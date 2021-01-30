package com.Ejdzyn.Service;

import java.sql.SQLException;
import java.util.List;

interface QuerySerivce {
    List<String> getColumns(String tabela) throws SQLException;
    List<String> getTables() throws SQLException;
}
