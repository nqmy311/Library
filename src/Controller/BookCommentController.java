package Controller;

import DAO.BookCommentDAO;
import java.sql.Connection;
import java.util.List;
import model.BookComment;
import view.BookCommentView;

public class BookCommentController {
    private final BookCommentDAO bookCommentDAO;
    private final BookCommentView bookCommentView;

    public BookCommentController(Connection connection, BookCommentView view)
    {
        this.bookCommentDAO = new BookCommentDAO(connection);
        this.bookCommentView = view;
    }

    public void addComment(int userId, int bookId, String comment)
    {
        BookComment newComment = new BookComment(userId, bookId, comment);
        if (bookCommentDAO.addComment(newComment))
        {
            bookCommentView.displayCommentAddedSuccesfully();
        }
        else
        {
            bookCommentView.displayCommentAddedFailed();
        }
    }

    public void viewCommentsByBook(int bookId)
    {
        List<BookComment> comments = bookCommentDAO.getCommentsByBookId(bookId);
        if (comments.isEmpty()) {
            bookCommentView.displayNoCommentsFoundForBook(bookId);
        } else {
            bookCommentView.displayCommentsForBook(comments);
        }
    }
}
