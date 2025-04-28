package Controller;

import java.util.ArrayList;
import model.Book;
import DAO.BookDAO;

public class BookController {
    private final BookDAO bookDAO = new BookDAO();

    public ArrayList<Book> listBook() {
        return bookDAO.listBook();
    }

    public boolean addBook(Book book) {
        return bookDAO.addBook(book);
    }

    public boolean deleteBook(int book_id) {
        return bookDAO.deleteBook(book_id);
    }

    public boolean updateBook(Book book) {
        return bookDAO.updateBook(book);
    }

    public Book findBookByID(int book_id) {
        return bookDAO.findBookByID(book_id);
    }

    public ArrayList<Book> findBookByName(String name) { return bookDAO.findBookByName(name); }
}
