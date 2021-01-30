package com.Ejdzyn.Repository;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowCollector extends AbstractTableModel {

    public RowCollector(Map<String, List<String>> products){
        this.products = products;

    }

    public Map<String, List<String>> getProducts() {
        return products;
    }

    public void setProducts(Map<String, List<String>> products) {
        this.products = products;
    }

    private Map<String, List<String>> products;

    @Override
    public int getRowCount() {
        if(!products.isEmpty()) {
            List<String> columns = new ArrayList<>(products.keySet());
            return products.get(columns.get(0)).size();
        } else return 0;
    }

    @Override
    public String getColumnName(int column) {

        List<String> columns = new ArrayList<>(products.keySet());
        return columns.get(column);
    }

    @Override
    public int getColumnCount() {
        return products.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(!products.isEmpty()) {
            List<String> columns = new ArrayList<>(products.keySet());
            return products.get(columns.get(columnIndex)).get(rowIndex);
        } else return null;
    }
}
