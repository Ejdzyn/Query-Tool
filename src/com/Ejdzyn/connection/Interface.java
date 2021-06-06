package com.Ejdzyn.connection;

import java.sql.*;

public class Interface {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://195.150.230.210:5434/2020_wstepnik_adrian";
    static final String USER = "2020_wstepnik_adrian";
    static final String PASS = "32354";

    private Connection con = null;

    public Connection getCon() {
        return con;
    }

    public Statement Connect() throws ClassNotFoundException, SQLException {

        Class<?> jdbc = Class.forName(JDBC_DRIVER);
        Driver driver = DriverManager.getDriver(DB_URL);
        String info = "Class: " + jdbc.getCanonicalName() +
                        " / JDBC version: " + driver.getMajorVersion() + "." + driver.getMinorVersion() +
                        " / Database: " + DB_URL;

        System.out.println(info);

        System.out.println("Trying to connect..");
        con = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement state = con.createStatement();
        con.setAutoCommit(false);

        System.out.println("Success!");

        return state;
    }
}

