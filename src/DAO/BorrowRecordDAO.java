package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.BorrowRecord;
import model.BorrowRequest;
import util.DBConnection;
import model.User;
import model.Book;

public class BorrowRecordDAO {
    public boolean createBorrowRecord(BorrowRecord record){
        String sql = "INSERT INTO borrow_records (user_id, book_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, record.getUserId());
            stmt.setInt(2, record.getBookId());
            stmt.setDate(3, new java.sql.Date(record.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(record.getDueDate().getTime()));
            stmt.setString(5, record.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    public BorrowRecord getBorrowRecordById(int recordId){
        String sql = "SELECT * FROM borrow_records WHERE record_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                BorrowRecord record = new BorrowRecord();
                record.setRecord_id(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBook_id(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date"));
                record.setDueDate(rs.getDate("due_date"));
                record.setReturnDate(rs.getDate("return_date"));
                record.setStatus(rs.getString("status"));
                record.setBook_condition(rs.getString("book_condition"));
                return record;
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<BorrowRecord> getBorrowRecordsByUserId(int userId) {
        ArrayList<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM borrow_records WHERE user_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BorrowRecord record = new BorrowRecord();
                record.setRecord_id(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBook_id(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date"));
                record.setDueDate(rs.getDate("due_date"));
                record.setReturnDate(rs.getDate("return_date"));
                record.setStatus(rs.getString("status"));
                record.setBook_condition(rs.getString("book_condition"));
                records.add(record);
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return records;
    }

    public ArrayList<BorrowRecord> getAllBorrowRecords(){
        ArrayList<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM borrow_records";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BorrowRecord record = new BorrowRecord();
                record.setRecord_id(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBook_id(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date"));
                record.setDueDate(rs.getDate("due_date"));
                record.setReturnDate(rs.getDate("return_date"));
                record.setStatus(rs.getString("status"));
                record.setBook_condition(rs.getString("book_condition"));
                records.add(record);
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return records;
    }

    public boolean updateBorrowRecordStatus(int recordId, String status){
        String sql = "UPDATE borrow_records SET status = ? WHERE record_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, recordId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }
}