package com.Ejdzyn.GUI;

import com.Ejdzyn.Repository.RowCollector;
import com.Ejdzyn.Service.QueryTool;
import com.Ejdzyn.UI;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        this.frame = new JFrame("Police DataBase");
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

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    try {
                        queryTool.close();
                        System.out.println("Disconnected");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(null, throwables.getMessage());
                    }

                    System.exit(0);
                }
            }
        });

    }

    private void addTables() throws SQLException {


        JTabbedPane tabbedPane = new JTabbedPane();

        for(String t : queryTool.getTables()){
            //System.out.println(t);

            //???
            RowCollector rowCollector = new RowCollector(queryTool.getRows(t));
            JTable table = new JTable(rowCollector);
            JScrollPane jS = new JScrollPane(table);
            tabbedPane.add(t,jS);
        }
        JScrollPane jScrollPane = new JScrollPane(tabbedPane);
        this.frame.add(jScrollPane);


    }
}
