package com.solstice.javatraining;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil
{
    private static final String USERNAME = "root";
    private static final String PASSWORD = "TombRaider2408$";
    private static final String CONN_STRING =  "jdbc:mysql://localhost/" +
            "codingchallenge?useSSL=false&useUnicode=true&useJDBCComp" +
        "liantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static Connection getConnection(DBType dbType) throws SQLException
    {
        switch (dbType)
        {
            case MYSQL:
                return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                default:
                    return null;

        }
    }

    public static void processException(SQLException e)
    {
        System.err.println("Error message: " + e.getMessage());
        System.err.println("Error code: " + e.getErrorCode());
        System.err.println("Error message: " + e.getSQLState());
    }
}
