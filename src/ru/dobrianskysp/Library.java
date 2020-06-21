package ru.dobrianskysp;

import java.sql.*;

public class Library {
    private static String nick;
    private static Connection conn = null;
    private static Statement state = null;
    private static PreparedStatement ps = null;

    public static void connection (){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            state = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getNick(){
        return this.nick;
    }

    public boolean logined(String usr, String pass){
        System.out.println(usr);
        String sqlResult = String.format("select * from User where login='%s'", usr);
        System.out.println(sqlResult);
        try {
            ResultSet rs = state.executeQuery(sqlResult);
            while (rs.next()){
                if (rs.getString("password").equals(pass)) {
                    System.out.println(rs.getString("login") + ": " + rs.getString("nick"));
                    this.nick = rs.getString("nick");
                    return true;
                } else {
                    System.out.println("Пароль введен не верно");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return false;
    }

    public void registrationUser(String usr, String nick, String pass) {
        System.out.println(usr);
        String sqlexecute = "INSERT INTO User ("+usr+", "+nick+", " +pass + ")";
        System.out.println(sqlexecute);
        try {
            state = conn.createStatement();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("INSERT INTO User (login, nick, password) VALUES (?,?,?)");
            ps.setString(1, usr);
            ps.setString(2, nick);
            ps.setString(3, pass);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
