package view;

import Controller.BookCommentController;
import Controller.BookController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Book;
import model.BookComment;
import model.TablePrinter;

public class BookCommentView {
    private final Scanner scanner = new Scanner(System.in);
    private final BookController bookController = new BookController();
    private final BookCommentController commentController;

    public BookCommentView(BookCommentController commentController) {
        this.commentController = commentController;
    }

    // Hiển thị danh sách sách để người dùng chọn
    private void displayBookList() {
        List<Book> books = bookController.listBook(); // Lấy danh sách tất cả các sách
        if (books.isEmpty()) {
            System.out.println("Không có sách nào trong thư viện.");
            return;
        }

        // In bảng danh sách sách
        String[] headers = { "ID sách", "Tiêu đề", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "Thể loại" };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Book book : books) {
            rows.add(new Object[] {
                    book.getBook_Id(), book.getTitle(), book.getAuthor(),
                    book.getPublisher(), book.getYear_published(), book.getCategory()
            });
        }
        TablePrinter.printTable(headers, rows);
    }

    public void showBookCommentMenu(int userId) {
        int choice;
        do {
            System.out.println("======== ĐÁNH GIÁ SÁCH ========");
            System.out.println("1. Đánh giá sách");
            System.out.println("2. Xem đánh giá sách");
            System.out.println("0. Quay lại menu người dùng");
            System.out.println("===============================");
            System.out.print("Chọn chức năng: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        addBookComment(userId);
                        break;
                    case 2:
                        viewBookComments();
                        break;
                    case 0:
                        System.out.println("Quay lại menu người dùng ...");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } else {
                System.out.println("Vui lòng nhập số!");
                choice = -1;
            }
        } while (choice != 0);
    }

    private void addBookComment(int userId) {
        // Hiển thị danh sách sách trước khi lấy ID sách
        displayBookList();
        while (true) {
            System.out.print("Nhập ID sách bạn muốn đánh giá: ");
            String bookIdInput = scanner.nextLine().trim();
            if (bookIdInput.equals("0")) {
                System.out.println("Quay lại...");
                return;
            }

            if (!bookIdInput.matches("\\d+")) {
                System.out.println("ID sách không hợp lệ!");
                continue;
            }
            int bookId = Integer.parseInt(bookIdInput);

            Book selectedBook = bookController.findBookByID(bookId);
            if (selectedBook == null) {
                System.out.println("Không tìm thấy sách có ID " + bookId);
                continue;
            }

            System.out.print("Nhập đánh giá của bạn về sách \"" + selectedBook.getTitle() + "\": ");
            String comment = scanner.nextLine().trim();
            if (comment.isEmpty()) {
                System.out.println("Đánh giá không được để trống!");
                continue;
            }

            commentController.addComment(userId, bookId, comment);
            return;
        }
    }

    public void viewBookComments() {
        // Hiển thị danh sách sách trước khi lấy ID sách
        displayBookList();
        while (true) {
            System.out.print("Nhập ID sách bạn muốn xem đánh giá: ");
            String bookIdInput = scanner.nextLine().trim();
            if (bookIdInput.equals("0")) {
                System.out.println("Quay lại...");
                return;
            }
            if (!bookIdInput.matches("\\d+")) {
                System.out.println("ID sách phải là một số nguyên!");
                continue;
            }
            int bookId = Integer.parseInt(bookIdInput);

            Book selectedBook = bookController.findBookByID(bookId);
            if (selectedBook != null) {
                System.out
                        .println("--- Đánh giá cho sách \"" + selectedBook.getTitle() + "\" (ID: " + bookId + ") ---");
                commentController.viewCommentsByBook(bookId);
                break;
            } else {
                System.out.println("Không tìm thấy sách có ID " + bookId);
            }
        }
    }

    public void displayCommentAddedSuccesfully() {
        System.out.println("Đánh giá đã được thêm thành công!");
    }

    public void displayCommentAddedFailed() {
        System.out.println("Không thể thêm đánh giá. Vui lòng thử lại!");
    }

    public void displayCommentsForBook(List<BookComment> comments) {
        if (comments.isEmpty()) {
            System.out.println("Không có đánh giá nào cho sách này.");
        } else {
            String[] headers = { "ID đánh giá", "ID người dùng", "Tên người dùng", "ID sách", "Tên sách", "Bình luận" };
            ArrayList<Object[]> rows = new ArrayList<>();
            for (BookComment comment : comments) {
                rows.add(new Object[] {
                        comment.getCommentId(),
                        comment.getUserId(),
                        comment.getUserName(),
                        comment.getBookId(),
                        comment.getBookTitle(),
                        comment.getComment()
                });
            }
            TablePrinter.printTable(headers, rows);
        }
    }

    public void displayNoCommentsFoundForBook(int bookId) {
        System.out.println("Không tìm thấy đánh giá nào cho sách có ID: " + bookId);
    }
}
