package ru.dobrianskysp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cliient_GUI extends JFrame implements ActionListener {

    private final String nick;
    private final String title = "Чат. логин: ";
    private final int width = 800;
    private final int heigth = 600;
    private final JTextArea log = new JTextArea();
    private final JTextField txMessage = new JTextField("");
    private final JButton btnSend = new JButton("Отправить");
    private final JButton btnLogOut = new JButton("Logout");
    private final JPanel top =new JPanel(new GridLayout(1,2));
    private final JPanel bottom = new JPanel(new GridLayout(1,2));
    private final JPanel bottomBtn = new JPanel(new GridLayout(1,2));
    private final JList<String> userList = new JList<>();
    private final String[] users = new String[1];


    public Cliient_GUI(String nick){
        users[0] = nick;
        this.nick = nick;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(title + nick);
        setBounds(200,100,width,heigth);
        JScrollPane scrollLog = new JScrollPane(log);
        log.setEditable(false);
        scrollLog.setPreferredSize(new Dimension(695,0));
        add(scrollLog, BorderLayout.WEST);
        JScrollPane userLog = new JScrollPane(userList);
        userList.setListData(users);
        userLog.setPreferredSize(new Dimension(100,0));
        add(userLog, BorderLayout.EAST);
        bottom.add(txMessage);
        bottomBtn.add(btnSend);
        bottomBtn.add(btnLogOut);
        bottom.add(bottomBtn);
        add(bottom,BorderLayout.SOUTH);
        setResizable(false);
        btnLogOut.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnLogOut){
            new Aut_GUI();
            dispose();
        }
    }
}
