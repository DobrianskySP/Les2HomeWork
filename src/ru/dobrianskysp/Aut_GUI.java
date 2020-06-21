package ru.dobrianskysp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

    private final JFrame registration = new JFrame("Регистрация");
    private final JPanel regPanel = new JPanel(new GridLayout(5,2));
    private final JPanel bottomReg = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private final JTextField login2 = new JTextField("Login");
    private final JPasswordField pass2 = new JPasswordField("123");
    private final JButton reg2 = new JButton("Зарегистрировать");
    private final JButton exit2 = new JButton("Выход");
    private final JTextField nick = new JTextField("nickname");
    private final JPasswordField confirmPass = new JPasswordField("321");

    private final Library library;

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
        library = new Library();
        library.connection();

        btnLogin.addActionListener(this);
        btnReg.addActionListener(this);
        btnExit.addActionListener(this);
        setVisible(true);
    }

    public void Registration(){
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnExit){
            library.close();
            System.exit(0);
        } else if (src == btnLogin){
            if (library.logined(login.getText(), pass.getText())){
                try {
                    new Cliient_GUI(library.getNick());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        } else if (src == btnReg){
            Registration();
        } else if (src == exit2){
            registration.dispose();
        } else if (src == reg2) {
            if (pass2.getText().equals(confirmPass.getText())){
                library.registrationUser(login2.getText(), nick.getText(), pass2.getText());}
            else {
                JOptionPane.showMessageDialog(this,"пароли не совпадают");
            }
        }
    }


}