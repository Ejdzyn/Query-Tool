package com.Ejdzyn.Connection;

import java.sql.*;

@SuppressWarnings("ALL")
public class Interface {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://195.150.230.210:5434/2020_wstepnik_adrian";
    static final String USER = "2020_wstepnik_adrian";
    static final String PASS = "32354";

    private Connection con = null;
    private Statement state = null;

    public Connection getCon() {
        return con;
    }

    public Statement getState() {
        return state;
    }

    public void setState(Statement state) {
        this.state = state;
    }

    public Statement Connect() throws ClassNotFoundException, SQLException {

            // łączenie z driverem postgres
            Class jdbc = Class.forName(JDBC_DRIVER);
            Driver driver = DriverManager.getDriver(DB_URL);
            String information = "Class: " + jdbc.getCanonicalName() + " / JDBC version: " + driver.getMajorVersion() + "." + driver.getMinorVersion() + " / Database: " + DB_URL;
            System.out.println(information);

            System.out.println("Próba połączenia z bazą");
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement state = con.createStatement();
            System.out.println("Połączono");


        return state;
    }
}

