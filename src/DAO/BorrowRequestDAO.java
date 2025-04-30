package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.BorrowRequest;
import util.DBConnection;


public class BorrowRequestDAO {
    public boolean createBorrowRequest(BorrowRequest request) {
        String sql = "INSERT INTO borrow_requests (user_id, book_id, request_date, status) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, request.getUserId());
            stmt.setInt(2, request.getBookId());
            stmt.setDate(3, new java.sql.Date(request.getRequestDate().getTime()));
            stmt.setString(4, request.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<BorrowRequest> BorrowRequests(){
        ArrayList<BorrowRequest> list = new ArrayList<>();
        String sql = "SELECT br.request_id, br.user_id, u.name AS user_name, br.book_id, b.title AS book_title, br.request_date, br.status " +
                "FROM borrow_requests br " +
                "JOIN users u ON br.user_id = u.user_id " +
                "JOIN books b ON br.book_id = b.book_id " +
                "WHERE status = 'Đang chờ duyệt'";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                BorrowRequest requests = new BorrowRequest();
                requests.setRequestId(rs.getInt("request_id"));
                requests.setUserId(rs.getInt("user_id"));
                requests.setUserName(rs.getString("user_name"));
                requests.setBookId(rs.getInt("book_id"));
                requests.setBookTitle(rs.getString("book_title"));
                requests.setRequestDate(rs.getDate("request_date"));
                requests.setStatus(rs.getString("status"));
                list.add(requests);
            }
        } catch(Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return list;
    }

    public BorrowRequest getBorrowRequestById(int requestId) {
        String sql = "SELECT br.request_id, br.user_id, u.name AS user_name, br.book_id, b.title AS book_title, br.request_date, br.status " +
                "FROM borrow_requests br " +
                "JOIN users u ON br.user_id = u.user_id " +
                "JOIN books b ON br.book_id = b.book_id " +
                "WHERE br.request_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, requestId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BorrowRequest request = new BorrowRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setUserId(rs.getInt("user_id"));
                request.setUserName(rs.getString("user_name"));
                request.setBookId(rs.getInt("book_id"));
                request.setBookTitle(rs.getString("book_title"));
                request.setRequestDate(rs.getDate("request_date"));
                request.setStatus(rs.getString("status"));
                return request;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<BorrowRequest> getBorrowRequestByUserId(int userId) {
        ArrayList<BorrowRequest> requests = new ArrayList<>();
        String sql = "SELECT br.request_id, br.user_id, u.name AS user_name, br.book_id, b.title AS book_title, br.request_date, br.status " +
                "FROM borrow_requests br " +
                "JOIN users u ON br.user_id = u.user_id " +
                "JOIN books b ON br.book_id = b.book_id " +
                "WHERE br.user_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                BorrowRequest request = new BorrowRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setUserId(rs.getInt("user_id"));
                request.setUserName(rs.getString("user_name"));
                request.setBookId(rs.getInt("book_id"));
                request.setBookTitle(rs.getString("book_title"));
                request.setRequestDate(rs.getDate("request_date"));
                request.setStatus(rs.getString("status"));
                requests.add(request);
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return requests;
    }

    public boolean updateBorrowRequestStatus(int requestId, String status){
        String sql = "UPDATE borrow_requests SET status = ? WHERE request_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, requestId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }
}