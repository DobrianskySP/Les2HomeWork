package ru.dobrianskysp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Aut_GUI extends JFrame implements ActionListener {
    private final String title = "Авторизация";
    private final int width = 400;
    private final int heigth = 200;
    private final JTextField login = new JTextField("Login");
    private final JPasswordField pass = new JPasswordField("Password");
    private final JButton btnLogin = new JButton("Login");
    private final JButton btnReg = new JButton("Registration");
    private final JButton btnExit = new JButton("Exit");
    private final JPanel top =new JPanel(new GridLayout(2,2));
    private final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private Connection conn = null;
    private Statement state = null;
    private PreparedStatement ps = null;

    private final JFrame registration = new JFrame("Регистрация");
    private final JPanel regPanel = new JPanel(new GridLayout(5,2));
    private final JPanel bottomReg = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private final JTextField login2 = new JTextField("Login");
    private final JPasswordField pass2 = new JPasswordField("123");
    private final JButton reg2 = new JButton("Зарегистрировать");
    private final JButton exit2 = new JButton("Выход");
    private final JTextField nick = new JTextField("nickname");
    private final JPasswordField confirmPass = new JPasswordField("321");

    public Aut_GUI(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(title);
        setBounds(300,200,width,heigth);
        top.add(login);
        top.add(pass);
        bottom.add(btnLogin);
        bottom.add(btnReg);
        bottom.add(btnExit);
        add(top);
        add(bottom,BorderLayout.SOUTH);
        setResizable(false);
        btnLogin.addActionListener(this);
        btnReg.addActionListener(this);
        btnExit.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnExit){
            System.exit(0);
        } else if (src == btnLogin){
            logined(login.getText(), pass.getText());
        } else if (src == btnReg){
            registration.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            registration.setBounds(300,200,width,heigth);
            registration.setResizable(false);
            registration.setTitle("Регистрицая пользователя");
            regPanel.add(login2);
            regPanel.add(nick);
            regPanel.add(pass2);
            regPanel.add(confirmPass);
            bottomReg.add(reg2);
            bottomReg.add(exit2);
            reg2.addActionListener(this);
            exit2.addActionListener(this);
            regPanel.add(bottomReg);
            registration.add(regPanel);
            registration.setVisible(true);
        } else if (src == exit2){
            registration.dispose();
        } else if (src == reg2) {
            if (pass2.getText().equals(confirmPass.getText())){
                registrationUser(login2.getText(), nick.getText(), pass2.getText());}
            else {
                JOptionPane.showMessageDialog(this,"пароли не совпадают");
            }
        }
    }
    public void logined(String usr, String pass){
        System.out.println(usr);
        String sqlResult = "SELECT * FROM User WHERE userName = " + usr;
        System.out.println(sqlResult);
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            state = conn.createStatement();
            ResultSet rs = state.executeQuery(sqlResult);
            while (rs.next()){
                System.out.println(rs.getString("userName") + ": " + rs.getString("nick"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void registrationUser(String usr, String nick, String pass) {
        System.out.println(usr);
        String sqlexecute = "INSERT INTO User ("+usr+", "+nick+", " +pass + ")";
        System.out.println(sqlexecute);
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            state = conn.createStatement();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("INSERT INTO User (userName, nick, password) VALUES (?,?,?)");
            ps.setString(1, usr);
            ps.setString(2, nick);
            ps.setString(3, pass);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}