package DAO;

import model.Book;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DBConnection;

public class BookDAO {
    public ArrayList<Book> listBook() {
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBook_Id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setYear_published(rs.getInt("year_published"));
                book.setCategory(rs.getString("category"));
                book.setLocation(rs.getString("location"));
                book.setLanguage(rs.getString("language"));
                book.setQuantity(rs.getInt("quantity"));
                book.setAvailable(rs.getInt("available"));
                book.setPenalty_rate(rs.getInt("penalty_rate"));
                list.add(book);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return list;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, publisher, year_published, category, location, language, quantity, available, penalty_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setInt(4, book.getYear_published());
            stmt.setString(5, book.getCategory());
            stmt.setString(6, book.getLocation());
            stmt.setString(7, book.getLanguage());
            stmt.setInt(8, book.getQuantity());
            stmt.setInt(9, book.getAvailable());
            stmt.setInt(10, book.getPenalty_rate());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteBook(int book_id) {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, book_id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, year_published = ?, category = ?, location = ?, language = ?, quantity = ?, available = ?, penalty_rate = ? WHERE book_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setInt(4, book.getYear_published());
            stmt.setString(5, book.getCategory());
            stmt.setString(6, book.getLocation());
            stmt.setString(7, book.getLanguage());
            stmt.setInt(8, book.getQuantity());
            stmt.setInt(9, book.getAvailable());
            stmt.setInt(10, book.getPenalty_rate());
            stmt.setInt(11, book.getBook_Id());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    public Book findBookByID(int book_id) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, book_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setBook_Id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setYear_published(rs.getInt("year_published"));
                book.setCategory(rs.getString("category"));
                book.setLocation(rs.getString("location"));
                book.setLanguage(rs.getString("language"));
                book.setQuantity(rs.getInt("quantity"));
                book.setAvailable(rs.getInt("available"));
                book.setPenalty_rate(rs.getInt("penalty_rate"));
                return book;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Book> findBookByName(String name) {
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBook_Id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setYear_published(rs.getInt("year_published"));
                book.setCategory(rs.getString("category"));
                book.setLocation(rs.getString("location"));
                book.setLanguage(rs.getString("language"));
                book.setQuantity(rs.getInt("quantity"));
                book.setAvailable(rs.getInt("available"));
                book.setPenalty_rate(rs.getInt("penalty_rate"));
                list.add(book);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return list;
    }

    public void updateBookAvailable(int bookId, int available) {
        String sql = "UPDATE books SET available = ? WHERE book_id = ?";
        try{
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, available);
            statement.setInt(2, bookId);
            statement.executeUpdate();
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    public void decreaseBookAvailable(int bookId) {
        String sql = "UPDATE books SET available = available - 1 WHERE book_id = ? AND available > 0";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bookId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Không đủ sách để cho mượn hoặc sách không tồn tại.");
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    public void increaseBookAvailable(int bookId)  {
        String sql = "UPDATE books SET available = available + 1 WHERE book_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bookId);
            statement.executeUpdate();
        } catch(Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    public void decreaseBookQuantity(int bookId) {
        String sql = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ? AND quantity > 0";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, bookId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Không đủ sách để cho mượn hoặc sách không tồn tại.");
            }
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }
}
