package com.Ejdzyn.Service;

import com.Ejdzyn.Connection.Interface;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@SuppressWarnings("ALL")
public class QueryTool implements QuerySerivce{

    private Statement state;
    private Interface anInterface;

    public QueryTool() throws SQLException, ClassNotFoundException {

        this.anInterface = new Interface();
        this.state = this.anInterface.Connect();

    }

    public void close() throws SQLException {
        anInterface.getCon().close();
    }

    @Override
    public List<String> getTables() throws SQLException {

        String selectTable =
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema = 'public' " +
                        "and table_type = 'BASE TABLE'";

        ResultSet result = state.executeQuery(selectTable);

        try {

            List<String> tables = new ArrayList<String>();
            while (result.next()) {
                tables.add(result.getString("table_name"));
            }
            result.close();
            return tables;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        result.close();
        return null;
    }

    @Override
    public List<String> getColumns(String tabela) throws SQLException {

        String selectQuery =
                "SELECT column_name,data_type" +
                        "  FROM information_schema.columns" +
                        " WHERE table_schema = 'public'" +
                        "   AND table_name   = '" + tabela + "'" +
                        "     ;";

        ResultSet result = state.executeQuery(selectQuery);

        try {
            anInterface.getCon().commit();

            List<String> columns = new ArrayList<String>();
            while (result.next()) {
                columns.add(result.getString("column_name"));
            }
            result.close();
            return columns;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        result.close();
        return null;
    }

    @Override
    public Map<String,List<String>> getRows(String tabela) throws SQLException {

        String selectQuery =
                "SELECT * FROM " + tabela + ";";

        ResultSet result = state.executeQuery(selectQuery);
        try {

            anInterface.getCon().commit();

            Map<String, List<String>> products = new HashMap<>();
            List<String> columns = getColumns(tabela);

            for (String c : columns) {
                products.put(c, new ArrayList<>());
            }

            int i = 0;
            while (result.next()) {
                for (String c : columns) {
                    products.get(c).add(result.getString(c));
                }
            }
            result.close();
            return products;
        } catch (SQLException throwables) {
            anInterface.getCon().rollback();
        }
        result.close();
        return null;
    }

    @Override
    public boolean performQuery(String query) throws SQLException {

        PreparedStatement st = anInterface.getCon().prepareStatement(query);
        try {
            anInterface.getCon().commit();
            if (st.execute()) {
                while (st.getResultSet().next()) {
                    String row = "";
                    for (int i = 1; i <= st.getMetaData().getColumnCount(); i++) {
                        row += st.getResultSet().getString(i) + ", ";
                    }
                    System.out.println(row);
                }
            }
            st.close();
            return true;
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, throwables.getMessage());
            anInterface.getCon().rollback();
            throwables.printStackTrace();
        }
        st.close();
        return false;
    }
}
