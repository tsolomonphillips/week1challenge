package com.solstice.javatraining;

import java.sql.*;

public class StockManager
{
    public static void displayAllRows() throws SQLException
    {
        String sqlQuery = "SELECT symbol, price, volume, date FROM stocksymbols";

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery))
        {
            System.out.println("Stock Table: ");
            while (resultSet.next())
            {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(resultSet.getString("symbol"));
                stringBuffer.append(resultSet.getDouble("price"));
                stringBuffer.append(resultSet.getInt("volume"));
                stringBuffer.append(resultSet.getDate("date"));
            }
        }
        catch (SQLException e)
        {
            DBUtil.processException(e);
        }
    }

    public static boolean insert(Stock stock) throws Exception
    {
        String sqlQuery = "INSERT into stocksymbols (symbol, price, volume) " +
                "VALUES (?, ?, ?,)";

        ResultSet keys = null;

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1, stock.getSymbol());
            statement.setDouble(2, stock.getPrice());
            statement.setInt(3, stock.getVolume());
            //statement.setDate(4, (java.sql.Date) stock.getDate());
            statement.executeUpdate();

            int affected = statement.executeUpdate();

            if (affected == 1)
            {
                keys = statement.getGeneratedKeys();
                keys.next();
                String newKey = keys.getString(1);
                stock.setSymbol(newKey);
            }
            else
            {
                System.err.println("No rows affected");
                return false;
            }
        }
        catch (SQLException e)
        {
            System.err.println(e);
            return false;
        }
        finally
        {
            if (keys != null)
            {
                keys.close();
            }
        }
        return true;

    }

    public static Stock getRow(String symbol) throws SQLException
    {
        String sqlQuery = "SELECT * FROM stocksymbols WHERE symbol = ?";
        ResultSet resultSet = null;

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
            preparedStatement.setString(1, symbol);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                Stock bean = new Stock();
                bean.setSymbol(symbol);
                bean.setPrice(resultSet.getDouble("price"));
                bean.setVolume(resultSet.getInt("volume"));
                bean.setDate(resultSet.getDate("date"));
                return bean;
            }
            else
            {
                System.err.println("No rows were found");
                return null;
            }
        }
    }
}
