package com.Ejdzyn.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

interface QuerySerivce {
    List<String> getColumns(String tabela) throws SQLException;
    List<String> getTables() throws SQLException;
    Map<String,List<String>> getRows(String tabela) throws SQLException;
    boolean performQuery(String query) throws SQLException;
}
