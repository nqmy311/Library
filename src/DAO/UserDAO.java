package DAO;

import model.User;
import util.DBConnection;
import java.sql.*;

public class UserDAO {
    public boolean register(User user) {
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
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi!");
        }
        return false;
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUser_Id(rs.getInt("user_id"));
                user.setUsername(username);
                // user.setPassword(password);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi!");
        }
        return null;
    }
}
