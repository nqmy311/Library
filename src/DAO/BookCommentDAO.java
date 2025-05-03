package DAO;

import java.sql.*;
import java.util.*;
import model.BookComment;

public class BookCommentDAO {
    private final Connection connection;

    public BookCommentDAO (Connection connection)
    {
        this.connection = connection;
    }

    public boolean addComment(BookComment comment)
    {
        String sql = "INSERT INTO book_comments (user_id, book_id, comment) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, comment.getUserId());
            preparedStatement.setInt(2, comment.getBookId());
            preparedStatement.setString(3, comment.getComment());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    /*lấy danh sách comment theo sách*/
    public List<BookComment> getCommentsByBookId(int bookId)
    {
        List<BookComment> comments = new ArrayList<>();
        String sql = "SELECT bc.comment_id, bc.user_id, u.name AS user_name, bc.book_id, b.title AS book_title, bc.comment FROM book_comments bc JOIN users u ON bc.user_id = u.user_id JOIN books b ON bc.book_id = b.book_id WHERE bc.book_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookComment comment = new BookComment();
                comment.setCommentId(resultSet.getInt("comment_id"));
                comment.setUserId(resultSet.getInt("user_id"));
                comment.setUserName(resultSet.getString("user_name")); // Lấy tên người dùng
                comment.setBookId(resultSet.getInt("book_id"));
                comment.setBookTitle(resultSet.getString("book_title")); // Lấy tên sách
                comment.setComment(resultSet.getString("comment"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return comments;
    }
}
