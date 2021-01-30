package com.Ejdzyn.GUI;

import com.Ejdzyn.Service.QueryTool;

import javax.swing.*;
import java.sql.SQLException;

public class DatabaseInterfaceFrame extends JFrame {

    JFrame jFrame;
    QueryTool queryTool;

    public DatabaseInterfaceFrame(){

        try {
            this.queryTool = new QueryTool();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return;
        }

        this.jFrame = new JFrame("Police DataBase");
        //jFrame.setSize(800,900);
        jFrame.setLayout(null);
        jFrame.setVisible(true);

    }
}
