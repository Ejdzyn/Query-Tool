package com.Ejdzyn.Service;

import com.Ejdzyn.Connection.Interface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class QueryTool implements QuerySerivce{

    private Statement state;
    private Interface anInterface;

    public QueryTool() throws SQLException, ClassNotFoundException {

        this.anInterface = new Interface();
        this.state = this.anInterface.Connect();

    }

    /*ResultSet result = state.executeQuery(selectQuery);
            System.out.println("Połączono");

            while(result.next()){
        System.out.println(result.getString("column_name") +" : "+ result.getString("data_type"));
    }*/

    @Override
    public void getTables(String schema) throws SQLException {
        String selectTable =
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema = '"+schema+"' " +
                        "and table_type = 'BASE TABLE'";

        ResultSet result = state.executeQuery(selectTable);
        List<String> tables = new ArrayList<String>(Integer.parseInt(result.toString()));
        System.out.println(tables);
    }

    @Override
    public void getColumns(String tabela) throws SQLException {

        String selectQuery =
                "SELECT column_name,data_type" +
                        "  FROM information_schema.columns" +
                        " WHERE table_schema = 'public'" +
                        "   AND table_name   = '"+tabela+"'" +
                        "     ;";

        ResultSet result = state.executeQuery(selectQuery);
    }

}
