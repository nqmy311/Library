package DAO;

import model.User;
import java.sql.*;
import java.util.*;
import util.DBConnection;

public class AccountDAO {
    public ArrayList<User> listUser() {
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUser_Id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                list.add(user);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return list;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, name, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getAddress());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    public User findUser(int user_id){
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUser_Id(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                return user;
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return null;
    }
}
