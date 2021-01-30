package com.Ejdzyn.GUI;

import com.Ejdzyn.Service.QueryTool;
import com.Ejdzyn.UI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.SQLException;

public class DatabaseInterfaceFrame extends UI {

    JFrame frame;
    QueryTool queryTool;

    public DatabaseInterfaceFrame(){

        try {
            this.queryTool = new QueryTool();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return;
        }

        this.frame = new JFrame();
        frame.setContentPane(start);

        JPanel panel = new JPanel(new GridBagLayout());

        frame.add(panel);
        frame.setVisible(true);


        try {
            addTables();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        frame.pack();
        frame.revalidate();
        frame.repaint();
        frame.setSize(new Dimension(960,640));
    }

    private void addTables() throws SQLException {


        JTabbedPane tabbedPane = new JTabbedPane();

        for(int i = 0 ; i < queryTool.getTables().size();i++){
            JPanel jPanel = new JPanel();
            tabbedPane.add(queryTool.getTables().get(i),jPanel);

        }
        JScrollPane jScrollPane = new JScrollPane(tabbedPane);
        this.frame.add(jScrollPane);


    }
}
