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
        String sql = "SELECT * FROM book_comments WHERE book_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BookComment comment = new BookComment();
                comment.setCommentId(resultSet.getInt("comment_id"));
                comment.setUserId(resultSet.getInt("user_id"));
                comment.setBookId(resultSet.getInt("book_id"));
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
