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

    public static void insert(Stock stock) throws Exception
    {
        ResultSet keys = null;

        String sqlQuery = "INSERT into stocksymbols (symbol, price, volume, date) " +
                "VALUES (?, ?, ?, ?)";


        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1, stock.getSymbol());
            statement.setDouble(2, stock.getPrice());
            statement.setInt(3, stock.getVolume());
            statement.setDate(4, stock.getDate());

            statement.executeUpdate();

            int affected = statement.executeUpdate();

            if (affected == 1)
            {
                keys = statement.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                stock.setStockId(newKey);
            }
            else
            {
                System.err.println("No rows affected");
            }
        }
        catch (SQLException e)
        {
            System.err.println(e);
        }
        finally
        {
            if (keys != null)
            {
                keys.close();
            }
        }

    }

    public static double getMaxPrice(Date date, String stockSymbol) throws SQLException
    {
        String sqlQuery = "SELECT max(price) AS maxPrice FROM stocksymbols WHERE date = ? AND symbol = ?";

        ResultSet resultSet = null;

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, stockSymbol);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                double maxPrice = resultSet.getDouble(1);
                //stock.setPrice(resultSet.getDouble(1));
                return maxPrice;
            }
            else
            {
                System.err.println("No rows were found");
                return -1;
            }

        }
        catch (SQLException e)
        {
            System.err.println(e);
            return -1;
        }

    }


    public static double getMinPrice(Date date, String stockSymbol) throws SQLException
    {
        String sqlQuery = "SELECT MIN(price) AS minPrice, date FROM stocksymbols WHERE date = ? AND symbol = ?";

        ResultSet resultSet = null;

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, stockSymbol);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                double minPrice = resultSet.getDouble(1);
                return minPrice;
            }
            else
            {
                System.err.println("No rows were found");
                return -1;
            }
        }
    }

    public static int getTotalVolume(Date date, String stockSymbol) throws SQLException
    {
        String sqlQuery = "SELECT SUM(volume) AS totalVolume FROM stocksymbols WHERE date = ? AND symbol = ?";

        ResultSet resultSet = null;

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, stockSymbol);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                int totalVolume = resultSet.getInt(1);
                return totalVolume;
            }
            else
            {
                System.err.println("No rows were found");
                return -1;
            }
        }
    }
}
