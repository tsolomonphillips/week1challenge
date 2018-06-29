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

    public static Stock getMaxPrice(Date date, String stockSymbol) throws SQLException
    {
        String sqlQuery = "SELECT symbol, max(price) AS maxPrice, date FROM stocksymbols WHERE date = '?' AND symbol = '?'";

        ResultSet resultSet = null;

        Stock stock = new Stock();
        stock.setDate(date);
        stock.setSymbol(stockSymbol);

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, stockSymbol);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                stock.setDate(resultSet.getDate(1));
                stock.setSymbol(resultSet.getString(2));
                return stock;
            }
            else
            {
                System.err.println("No rows were found");
                return null;
            }

        }
        catch (SQLException e)
        {
            System.err.println(e);
            return null;
        }

    }

    public static Stock getMinPrice(Date date, String stockSymbol) throws SQLException
    {
        String sqlQuery = "SELECT symbol, MIN(price) AS maxPrice, date FROM stocksymbols WHERE date = '?' AND symbol = '?'";

        ResultSet resultSet = null;

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
//            preparedStatement.setDate(1, date);
//            preparedStatement.setString(2, stockSymbol);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                Stock stock = new Stock();

                stock.setDate(resultSet.getDate("date"));
                stock.setSymbol(resultSet.getString("symbol"));

                return stock;
            }
            else
            {
                System.err.println("No rows were found");
                return null;
            }
        }
    }

    public static Stock getTotalVolume(Date date, String stockSymbol) throws SQLException
    {
        String sqlQuery = "SELECT symbol, SUM(volume) AS totalVolume, date FROM stocksymbols WHERE date = '?' AND symbol = '?'";

        ResultSet resultSet = null;

        try (Connection connection = DBUtil.getConnection(DBType.MYSQL);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))
        {
//            preparedStatement.setDate(1, date);
//            preparedStatement.setString(2, stockSymbol);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                Stock stock = new Stock();

                stock.setDate(resultSet.getDate("date"));
                stock.setSymbol(resultSet.getString("symbol"));

                return stock;
            }
            else
            {
                System.err.println("No rows were found");
                return null;
            }
        }
    }
}
