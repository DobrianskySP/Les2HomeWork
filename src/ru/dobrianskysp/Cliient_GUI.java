package ru.dobrianskysp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

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

    private DataInputStream in;


    public Cliient_GUI(String nick) throws IOException {
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
        btnSend.addActionListener(this);
        txMessage.addActionListener(this);
        loadLog();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnLogOut){
            new Aut_GUI();
            dispose();
        } else if (src == btnSend || src == txMessage){
            String username = nick;
            String msg = username + ": " + txMessage.getText();
            if ("".equals(msg)) return;
            else {
                log.append(msg + "\n");
                wrtToLog(msg,nick);
                txMessage.setText(null);
                txMessage.requestFocusInWindow();
            }
        }
    }

    private void wrtToLog(String msg, String user) {
        try (FileWriter out = new FileWriter("log.txt", true)) {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadLog(){
        ArrayList<String> inLog = new ArrayList<>();
        String[] loged = new String[101];
        try {
            in = new DataInputStream(new FileInputStream("log.txt"));
            int x = 0;
            int end = 0;
            while ((x = in.read()) != -1 ) {
                inLog.add((char)x + in.readLine());
            }
            int maxCount = inLog.size();
            while (end < 100){
                end++;
                if ((inLog.size() - end) < 0 ){break;}
                loged[end] = inLog.get(maxCount - end);
                System.out.println(inLog.get(maxCount - end));
            }
            end--;
            while (end >= 1){
                log.append(loged[end] + "\n");
                end--;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
