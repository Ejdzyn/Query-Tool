package com.Ejdzyn.Connection;

import java.sql.*;

@SuppressWarnings("ALL")
public class Interface {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://195.150.230.210:5434/2020_wstepnik_adrian";
    static final String USER = "2020_wstepnik_adrian";
    static final String PASS = "32354";

    private Connection con = null;
    private Statement state = null;

    public Connection getCon() {
        return con;
    }

    public Statement getState() {
        return state;
    }

    public void setState(Statement state) {
        this.state = state;
    }

    public Statement Connect() throws ClassNotFoundException, SQLException {

            // łączenie z driverem postgres

            Class jdbc = Class.forName(JDBC_DRIVER);
            Driver driver = DriverManager.getDriver(DB_URL);
            String information = "Class: " + jdbc.getCanonicalName() + " / JDBC version: " + driver.getMajorVersion() + "." + driver.getMinorVersion() + " / Database: " + DB_URL;
            System.out.println(information);

            // łączenie
            System.out.println("Próba połączenia z bazą");
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement state = con.createStatement();
            System.out.println("Połączono");




            // tworzenie tabeli
            /*List<Oferta> ofertas = new ArrayList<>();
            while(result.next()){
                ofertas.add(new Oferta(Integer.parseInt(result.getString("id_produktu")), result.getString("nazwa_produktu"), Integer.parseInt(result.getString("cena")), Integer.parseInt(result.getString("dostepne_sztuki"))));
            }

            try {
                con.setAutoCommit(false);

            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
                throwables.printStackTrace();
            }
            // tworzenie okna
            OfertaTable ofertaTable = new OfertaTable(ofertas);
                // tworzenie przyciskow
            JTable table = new JTable(ofertaTable);
            JButton addBtn = new JButton("Dodaj");
            JButton upBtn = new JButton("Zaktualizuj");
            JButton deleteBtn = new JButton("Usuń");
            JButton refreshBtn = new JButton("Odśwież");

            addBtn.setBounds(240,750,100,30);
            upBtn.setBounds(360, 750, 100, 30);
            deleteBtn.setBounds(480, 750, 100, 30);
            refreshBtn.setBounds(360, 800, 100, 30);

            JTextField id = new HintTextField("ID");
            JTextField nazwa = new HintTextField("NAZWA");
            JTextField cena = new HintTextField("CENA");
            JTextField ilosc = new HintTextField("ILOSC");
            id.setBounds(180, 710, 100, 30);
            nazwa.setBounds(300, 710, 100, 30);
            cena.setBounds(420, 710, 100, 30);
            ilosc.setBounds(540, 710, 100, 30);

            // Insert into do tabeli
            addBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //your actions
                    try{
                        System.out.println("(" +id.getText()+", " + nazwa.getText() +", " + cena.getText() +", " + ilosc.getText() +")");
                        PreparedStatement st = con.prepareStatement("INSERT INTO \"sklep_internetowy\".\"Oferta\" (id_produktu, nazwa_produktu, cena, dostepne_sztuki) VALUES ("
                                +id.getText()+", '" + nazwa.getText() +"', " + cena.getText() +", " + ilosc.getText() +");");
                        st.execute();
                        st.close();
                        con.commit();
                        JOptionPane.showMessageDialog(null, "Dodanie wiersza powiodło się!");
                    }catch (SQLException ignored){
                        try {
                            con.rollback();
                            JOptionPane.showMessageDialog(null, ignored.getMessage());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            JOptionPane.showMessageDialog(null, throwables.getMessage());
                        }
                    }


                }
            });
            // delete z tabeli
            deleteBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //your actions
                    try{
                        System.out.println("(" +id.getText()+", " + nazwa.getText() +", " + cena.getText() +", " + ilosc.getText() +")");
                        PreparedStatement st = con.prepareStatement("DELETE FROM \"sklep_internetowy\".\"Oferta\" WHERE id_produktu = "
                                +id.getText()+" and nazwa_produktu = '" + nazwa.getText() + "';");
                        st.execute();
                        st.close();
                        con.commit();
                        JOptionPane.showMessageDialog(null, "Usunieto dany produkt!");
                    }catch (SQLException ignored){
                        try {
                            con.rollback();
                            JOptionPane.showMessageDialog(null, ignored.getMessage());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            JOptionPane.showMessageDialog(null, throwables.getMessage());
                        }
                    }


                }
            });
            // update do tabeli
            upBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //your actions
                    try{
                        System.out.println("(" +id.getText()+", " + nazwa.getText() +", " + cena.getText() +", " + ilosc.getText() +")");
                        PreparedStatement st = con.prepareStatement("UPDATE \"sklep_internetowy\".\"Oferta\" SET "
                                + "id_produktu = " + id.getText()+", nazwa_produktu = '" + nazwa.getText() +"', cena = " + cena.getText() +", dostepne_sztuki = " + ilosc.getText()
                                        + "WHERE id_produktu = " + id.getText() + ";"
                                );
                        st.execute();
                        st.close();
                        con.commit();
                        JOptionPane.showMessageDialog(null, "Zaktualizowano wiersz!");
                    }catch (SQLException ignored){
                        try {
                            con.rollback();
                            JOptionPane.showMessageDialog(null, ignored.getMessage());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            JOptionPane.showMessageDialog(null, throwables.getMessage());
                        }
                    }


                }
            });

                // tworzenie widoku
            JFrame jFrame = new JFrame("Oferta sklepu internetowego");
            JScrollPane jScrollPane = new JScrollPane(table);

            // REFRESH WIDOKU TABELKI
            refreshBtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //your actions
                    try{
                        PreparedStatement st = con.prepareStatement("SELECT * FROM \"sklep_internetowy\".\"Oferta\";");
                        ResultSet result = st.executeQuery();
                        // tworzenie tabeli
                        List<Oferta> ofertas = new ArrayList<>();
                        while(result.next()){
                            ofertas.add(new Oferta(Integer.parseInt(result.getString("id_produktu")), result.getString("nazwa_produktu"), Integer.parseInt(result.getString("cena")), Integer.parseInt(result.getString("dostepne_sztuki"))));
                        }
                        OfertaTable ofertaTable = new OfertaTable(ofertas);
                        // tworzenie przyciskow
                        JTable table = new JTable(ofertaTable);

                        jScrollPane.getViewport().add(table);
                        jScrollPane.repaint();
                        System.out.println("Odświeżono dane");
                    }catch (SQLException ignored){
                        try {
                            con.rollback();
                            JOptionPane.showMessageDialog(null, ignored.getMessage());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            JOptionPane.showMessageDialog(null, throwables.getMessage());
                        }
                    }


                }
            });


            jScrollPane.setBounds(0,0, 780, 700);
            jFrame.add(jScrollPane);
            jFrame.add(addBtn);
            jFrame.add(upBtn);
            jFrame.add(deleteBtn);
            jFrame.add(refreshBtn);
            jFrame.add(id);
            jFrame.add(nazwa);
            jFrame.add(cena);
            jFrame.add(ilosc);
            jFrame.setSize(800,900);
            jFrame.setLayout(null);
            jFrame.setVisible(true);
            // Rozlaczenie polaczenia przy wylaczeniu okna.
            jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    if (JOptionPane.showConfirmDialog(jFrame,
                            "Are you sure you want to close this window?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        try {
                            con.close();
                            System.out.println("Rozlaczono");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            JOptionPane.showMessageDialog(null, throwables.getMessage());
                        }

                        System.exit(0);
                    }
                }
            });*/

        return state;
    }
}

