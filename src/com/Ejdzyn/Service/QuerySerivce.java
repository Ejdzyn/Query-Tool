package com.Ejdzyn.Service;

import java.sql.SQLException;

interface QuerySerivce {
    void getColumns(String tabela) throws SQLException;
    void getTables(String schema) throws SQLException;
}
