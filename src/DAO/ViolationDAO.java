package DAO;

import model.Violation;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViolationDAO {
    public boolean createViolation(Violation violation){
        String sql = "INSERT INTO violations (user_id, record_id, violation_date, reason, fine_amount) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, violation.getUserId());
            stmt.setInt(2, violation.getRecordId());
            stmt.setDate(3, new java.sql.Date(violation.getViolationDate().getTime()));
            stmt.setString(4, violation.getReason());
            stmt.setDouble(5, violation.getFineAmount());
            return stmt.executeUpdate() > 0;
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    public Violation getViolationById(int violationId) {
        String sql = "SELECT v.*, u.name AS user_name " +
                "FROM violations v " +
                "JOIN users u ON v.user_id = u.user_id " +
                "WHERE v.violation_id = ?";
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, violationId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Violation violation = new Violation();
                violation.setViolationId(resultSet.getInt("violation_id"));
                violation.setUserId(resultSet.getInt("user_id"));
                violation.setRecordId(resultSet.getInt("record_id"));
                violation.setViolationDate(resultSet.getDate("violation_date"));
                violation.setReason(resultSet.getString("reason"));
                violation.setFineAmount(resultSet.getDouble("fine_amount"));
                violation.setPaid(resultSet.getBoolean("is_paid"));
                violation.setUserName(resultSet.getString("user_name"));
                return violation;
            }
        } catch(Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Violation> getViolationsByUserId(int userId) {
        ArrayList<Violation> violations = new ArrayList<>();
        String sql = "SELECT v.*, u.name AS user_name " +
                "FROM violations v " +
                "JOIN users u ON v.user_id = u.user_id " +
                "WHERE v.user_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Violation violation = new Violation();
                violation.setViolationId(resultSet.getInt("violation_id"));
                violation.setUserId(resultSet.getInt("user_id"));
                violation.setRecordId(resultSet.getInt("record_id"));
                violation.setViolationDate(resultSet.getDate("violation_date"));
                violation.setReason(resultSet.getString("reason"));
                violation.setFineAmount(resultSet.getDouble("fine_amount"));
                violation.setPaid(resultSet.getBoolean("is_paid"));
                violation.setUserName(resultSet.getString("user_name"));
                violations.add(violation);
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return violations;
    }

    public ArrayList<Violation> getViolationsByRecordId(int recordId) {
        ArrayList<Violation> violations = new ArrayList<>();
        String sql = "SELECT v.*, u.name AS user_name " +
                "FROM violations v " +
                "JOIN users u ON v.user_id = u.user_id " +
                "WHERE v.record_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, recordId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Violation violation = new Violation();
                violation.setViolationId(resultSet.getInt("violation_id"));
                violation.setUserId(resultSet.getInt("user_id"));
                violation.setRecordId(resultSet.getInt("record_id"));
                violation.setViolationDate(resultSet.getDate("violation_date"));
                violation.setReason(resultSet.getString("reason"));
                violation.setFineAmount(resultSet.getDouble("fine_amount"));
                violation.setPaid(resultSet.getBoolean("is_paid"));
                violation.setUserName(resultSet.getString("user_name"));
                violations.add(violation);
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return violations;
    }

    public ArrayList<Violation> getAllViolations() {
        ArrayList<Violation> violations = new ArrayList<>();
        String sql = "SELECT v.*, u.name AS user_name " +
                "FROM violations v " +
                "JOIN users u ON v.user_id = u.user_id ";
        try{
            Connection conn = DBConnection.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Violation violation = new Violation();
                violation.setViolationId(resultSet.getInt("violation_id"));
                violation.setUserId(resultSet.getInt("user_id"));
                violation.setRecordId(resultSet.getInt("record_id"));
                violation.setViolationDate(resultSet.getDate("violation_date"));
                violation.setReason(resultSet.getString("reason"));
                violation.setFineAmount(resultSet.getDouble("fine_amount"));
                violation.setPaid(resultSet.getBoolean("is_paid"));
                violation.setUserName(resultSet.getString("user_name"));
                violations.add(violation);
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return violations;
    }


    public boolean updateViolation(Violation violation) {
        String sql = "UPDATE violations SET user_id = ?, record_id = ?, violation_date = ?, reason = ?, fine_amount = ?, is_paid = ? WHERE violation_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, violation.getUserId());
            statement.setInt(2, violation.getRecordId());
            statement.setDate(3, new java.sql.Date(violation.getViolationDate().getTime()));
            statement.setString(4, violation.getReason());
            statement.setDouble(5, violation.getFineAmount());
            statement.setBoolean(6, violation.isPaid());
            statement.setInt(7, violation.getViolationId());
            return statement.executeUpdate() > 0;
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    public void markViolationAsPaid(int violationId) {
        String sql = "UPDATE violations SET is_paid = TRUE WHERE violation_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, violationId);
            statement.executeUpdate();
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    public BigDecimal getTotalPaidFinesByDate(LocalDate date) {
        BigDecimal totalFines = BigDecimal.ZERO;
        String sql = "SELECT SUM(fine_amount) FROM violations WHERE is_paid = TRUE AND DATE(violation_date) = ?";
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalFines = resultSet.getBigDecimal(1) != null ? resultSet.getBigDecimal(1) : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return totalFines;
    }
}