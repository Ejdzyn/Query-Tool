package com.Ejdzyn.GUI;

import com.Ejdzyn.GUI.Components.HintTextField;
import com.Ejdzyn.Repository.RowCollector;
import com.Ejdzyn.Service.QueryTool;
import com.Ejdzyn.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class DatabaseInterfaceFrame extends UI {

    JFrame frame;
    QueryTool queryTool;
    int last = 0;

    public DatabaseInterfaceFrame(){

        try {
            this.queryTool = new QueryTool();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return;
        }

        this.frame = new JFrame("Police DataBase");
        frame.setContentPane(start);

        frame.setVisible(true);

        refreshButton.addActionListener(e -> new Thread(() -> {
            try {
                addTables();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start());

        try {
            addTables();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        setButtonAction();

        frame.pack();
        frame.revalidate();
        frame.repaint();
        frame.setSize(new Dimension(960,640));

        new Thread(() -> {
            while(true){
                try {
                    frame.revalidate();
                    frame.repaint();
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void addTables() throws SQLException {

        this.frame.revalidate();
        this.frame.repaint();
        this.table.revalidate();

        JPanel buttonPanel = new JPanel(new GridLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        List<JTable> tables = new ArrayList<>();
        for(String t : queryTool.getTables()){
            RowCollector rowCollector = new RowCollector(queryTool.getRows(t),queryTool.getColumns(t));
            JTable table = new JTable(rowCollector);
            tables.add(table);

            JScrollPane jS = new JScrollPane(table);
            jS.setName(t);
            tabbedPane.add(t,jS);

        }

        JScrollPane jScrollPane = new JScrollPane(tabbedPane);
        buttonPanel.add(jScrollPane);

        addButtons(tabbedPane);
        tabbedPane.setSelectedIndex(last);

        while(this.table.getComponentCount()!=0){
            this.table.remove(0);
        }

        this.table.add(buttonPanel,0);

        this.frame.revalidate();
        this.frame.repaint();
        this.table.revalidate();

        tabbedPane.addChangeListener(e -> {
            last=tabbedPane.getSelectedIndex();
            addButtons(tabbedPane);
        });
    }

    private void addButtons(JTabbedPane tabbedPane){

        //System.out.println(tables.get(tabbedPane.getSelectedIndex()).getSelectedColumn());

        while(this.inputPanel.getComponentCount()!=0){
            this.inputPanel.remove(0);
        }

        JPanel jPanel = new JPanel(new FlowLayout(5,5,FlowLayout.LEFT));
        jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        try {
            List<String> columns = queryTool.getColumns(tabbedPane.getSelectedComponent().getName());

            List<HintTextField> inputs = new ArrayList<>();

            for(String c : columns){
                HintTextField jTextField = new HintTextField(c);
                jTextField.setName(c);
                jTextField.setMargin(new Insets(5,5,5,5));
                jPanel.add(jTextField);
                inputs.add(jTextField);
            }
            JButton insert = new JButton("INSERT");
            insert.setBackground(Color.ORANGE);
            JButton update = new JButton("UPDATE");
            update.setBackground(Color.PINK);
            JButton delete = new JButton("DELETE");
            delete.setBackground(Color.RED);

            JButton select = new JButton("SELECT");
            select.setBackground(Color.LIGHT_GRAY);

            jPanel.add(insert);
            jPanel.add(update);
            jPanel.add(delete);
            jPanel.add(select);
            inputPanel.add(jPanel,0);

            frame.revalidate();
            frame.repaint();

            for (HintTextField in : inputs){
                in.setMinimumSize(in.getSize());
                in.setPreferredSize(in.getSize());
            }

            delete.addActionListener(e1 -> {
                String query = "DELETE FROM "+tabbedPane.getSelectedComponent().getName()+" WHERE ";

                List<String> values = new ArrayList<>();
                for (HintTextField input : inputs) {
                    if (!input.getText().isEmpty()) {
                        values.add(input.getName() + " = '" + input.getText()+"'");
                    }
                }

                if(!values.isEmpty()){
                    for (int i = 0; i < values.size(); i++) {
                        query+=values.get(i);
                        if(i!= values.size()-1){
                            query+=" AND ";
                        }
                    }
                }

                textInput.setText(query);
            });

            insert.addActionListener(e1 -> {
                String query = "INSERT INTO "+tabbedPane.getSelectedComponent().getName()+" VALUES (";

                List<String> values = new ArrayList<>();
                for (HintTextField input : inputs) {
                    if (!input.getText().isEmpty()) {
                        values.add("'"+ input.getText()+"'");
                    }
                }

                if(!values.isEmpty()){
                    for (int i = 0; i < values.size(); i++) {
                        query+=values.get(i);
                        if(i!= values.size()-1){
                            query+=",";
                        }
                    }
                }

                textInput.setText(query+" );");
            });

            update.addActionListener(e1 -> {
                String query = "UPDATE "+tabbedPane.getSelectedComponent().getName()+" SET ";

                List<String> values = new ArrayList<>();
                for (HintTextField input : inputs) {
                    if (!input.getText().isEmpty()) {
                        values.add(input.getName() + " = '" + input.getText()+"'");
                    }
                }

                if(!values.isEmpty()){
                    for (int i = 0; i < values.size(); i++) {
                        query+=values.get(i);
                        if(i!= values.size()-1){
                            query+=", ";
                        }
                    }
                }

                textInput.setText(query);
            });

            select.addActionListener(e1 -> {
                String query = "SELECT * FROM "+tabbedPane.getSelectedComponent().getName();

                List<String> values = new ArrayList<>();
                for (HintTextField input : inputs) {
                    if (!input.getText().isEmpty()) {
                        values.add(input.getName() + " = '" + input.getText()+"'");
                    }
                }

                if(!values.isEmpty()){
                    query+=" WHERE ";
                    for (int i = 0; i < values.size(); i++) {
                        query+=values.get(i);
                        if(i!= values.size()-1){
                            query+=" AND ";
                        }
                    }
                }

                textInput.setText(query);
            });

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setButtonAction(){
        performButton.addActionListener(e -> new Thread(() -> {
            try {
                queryTool.performQuery(textInput.getText());
                textInput.setText("");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start());

        textInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    try {
                        queryTool.performQuery(textInput.getText());
                        textInput.setText("");
                    } catch (SQLException throwables) {
                        throwables.getMessage();
                    }
                }
            }
        });
    }
}
