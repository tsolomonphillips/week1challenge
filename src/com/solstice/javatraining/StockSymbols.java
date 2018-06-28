package com.solstice.javatraining;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockSymbols
{
    private String symbol;
    private double price;
    private int volume;
    private Date date;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "TombRaider2408$";
    private static final String CONN_STRING =
            "jdbc:mysql://localhost/codingchallenge?useSSL=false&useUnicode=true&useJDBCComp" +
                    "liantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static boolean insert(StockSymbols bean) throws Exception
    {
        String sqlQuery = "INSERT into stocksymbols (symbol, price, volume, date) " +
                "VALUES (?, ?)";

        ResultSet keys = null;

        try
        {
            Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, bean.getSymbol());
            statement.setDouble(2, bean.getPrice());
            statement.setInt(3, bean.getVolume());
            statement.setDate(4, (java.sql.Date) bean.getDate());
            int affected = statement.executeUpdate();

            if (affected == 1)
            {
                keys = statement.getGeneratedKeys();
                keys.next();
            }
            else
            {

            }
        }
        catch (SQLException e)
        {
            System.err.println(e);
            return false;
        }
        finally
        {

        }
        return true;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getVolume()
    {
        return volume;
    }

    public void setVolume(int volume)
    {
        this.volume = volume;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
