package view;

import Controller.BookCommentController;
import Controller.BookController;

import java.util.List;
import java.util.Scanner;

import model.Book;
import model.BookComment;

public class BookCommentView {
    private final Scanner scanner = new Scanner(System.in);
    private final BookController bookController = new BookController();
    private final BookCommentController commentController;

    public BookCommentView(BookCommentController commentController) {
        this.commentController = commentController;
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

    public void addBookComment(int userId) {
        System.out.println("======== ĐÁNH GIÁ SÁCH ========");
        List<Book> allBooks = bookController.listBook();
        if (allBooks.isEmpty()) {
            System.out.println("Không có sách nào để đánh giá!");
            return;
        }
        System.out.println(" DANH SÁCH SÁCH ");
        for (Book book : allBooks) {
            System.out.println(book.getBook_Id() + " " + book.getTitle());
        }
        System.out.print("Chọn sách bạn muốn đánh giá: ");
        String bookIdInput = scanner.nextLine().trim();
        if (bookIdInput.matches("\\d+")) {
            int bookId = Integer.parseInt(bookIdInput);
            Book selectedBook = bookController.findBookByID(bookId);
            if (selectedBook != null) {
                System.out.println("Nhập đánh giá: ");
                String commentText = scanner.nextLine().trim();
                if (!commentText.isEmpty()) {
                    commentController.addComment(userId, bookId, commentText);
                } else {
                    System.out.println("Vui lòng điền nội dung đánh giá!");
                }
            } else {
                System.out.println("Không tìm thấy sách có ID " + bookId);
            }
        } else {
            System.out.println("ID sách không hợp lệ!");
        }
    }

    public void viewBookComments() {
        System.out.println("======== XEM ĐÁNH GIÁ SÁCH ========");
        List<Book> allBooks = bookController.listBook();
        if (allBooks.isEmpty()) {
            System.out.println("Không có sách nào để xem đánh giá!");
            return;
        }
        System.out.println("--- Danh sách sách ---");
        for (Book book : allBooks) {
            System.out.println(book.getBook_Id() + " " + book.getTitle());
        }
        System.out.print("Chọn sách bạn muốn xem đánh giá: ");
        String bookIdInput = scanner.nextLine().trim();
        if (bookIdInput.matches("\\d+")) {
            int bookId = Integer.parseInt(bookIdInput);
            Book selectedBook = bookController.findBookByID(bookId);
            if (selectedBook != null) {
                commentController.viewCommentsByBook(bookId);
            } else {
                System.out.println("Không tìm thấy sách có ID " + bookId);
            }
        } else {
            System.out.println("ID sách không hơp lệ!");
        }
    }


    public void displayCommentAddedSuccesfully() {
        System.out.println("Đánh giá đã được thêm thành công!");
    }

    public void displayCommentAddedFailed() {
        System.out.println("Không thể thêm đánh giá. Vui lòng thử lại!");
    }

    public void displayCommentsForBook(List<BookComment> comments) {
        System.out.println("--- Đánh giá cho sách ---");
        if (comments != null && !comments.isEmpty()) {
            for (BookComment comment : comments) {
                System.out.println("ID bình luận: " + comment.getCommentId());
                System.out.println("ID người bình luận: " + comment.getUserId());
                System.out.println("Bình luận: " + comment.getComment());
                System.out.println("-------------------------------------");
            }
        } else {
            System.out.println("Không có đánh giá nào cho cuốn sách này!");
        }
    }

    public void displayNoCommentsFoundForBook(int bookId) {
        System.out.println("Không tìm thấy đánh giá nào cho sách có ID: " + bookId);
    }
}
