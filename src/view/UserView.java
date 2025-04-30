package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import Controller.BookCommentController;
import Controller.ViolationController;
import model.BorrowRequest;
import model.User;
import Controller.BookController;
import model.Book;
import Controller.BorrowController;
import util.DBConnection;

public class UserView {
    private final Scanner scanner = new Scanner(System.in);
    private final BookController bookController = new BookController();
    private final BorrowController borrowController = new BorrowController();
    private final ViolationController violationcontroller = new ViolationController();
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private final java.sql.Connection dbConnection = DBConnection.getConnection();
    private final BookCommentView bookCommentView = new BookCommentView(new BookCommentController(dbConnection, new BookCommentView(null)));

    public void showMenu(User user) {
        while (true){
            System.out.println("==== GIAO DIỆN NGƯỜI DÙNG THƯ VIỆN====");
            System.out.println("1. Xem thông tin tài khoản cá nhân");
            System.out.println("2. Xem thông tin sách");
            System.out.println("3. Tìm kiếm sách");
            System.out.println("4. Xem thông tin phiếu mượn / trả sách của tôi");
            System.out.println("5. Gửi yêu cầu mượn sách");
            System.out.println("6. Xem các yêu cầu mượn của tôi");
            System.out.println("7. Gửi yêu cầu trả sách");
            System.out.println("8. Đánh giá sách");
            System.out.println("9. Xem vi phạm của tôi");
            System.out.println("0. Đăng xuất");
            System.out.println("======================================");
            System.out.print("Chọn chức năng: ");
            try{
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        showProfile(user);
                        break;
                    case 2:
                        listBooks();
                        break;
                    case 3:
                        findBook();
                        break;
                    case 4:
                        borrowController.viewBorrowRecords(user.getUser_Id());
                        break;
                    case 5:
                        requestBorrowBook(user.getUser_Id());
                        break;
                    case 6:
                        borrowController.viewBorrowRequests(user.getUser_Id());
                        break;
                    case 7:
                        int recordIdReturn = checkIntInput("Nhập phiếu mượn muôn trả: ");
                        borrowController.requestReturnBook(recordIdReturn);
                        break;
                    case 8:
                        bookCommentView.showBookCommentMenu(user.getUser_Id());
                        break;
                    case 9:
                        violationcontroller.viewViolationsByUserId(user.getUser_Id());
                        break;
                    case 0:
                        System.out.println("Đăng xuất ...");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } catch (Exception e){
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    private void showProfile(User user) {
        System.out.println("==== Thông tin tài khoản ====");
        System.out.println("ID tài khoản:           " + user.getUser_Id());
        System.out.println("Tên đăng nhập:          " + user.getUsername());
        System.out.println("Tên chủ tài khoản:      " + user.getName());
        System.out.println("Email:                  " + user.getEmail());
        System.out.println("Số điện thoại:          " + user.getPhone());
        System.out.println("Địa chỉ:                " + user.getAddress());
    }

    private void listBooks() {
        ArrayList<Book> list = bookController.listBook();
        System.out.println("==== Danh sách sách ====");
        System.out.printf("%-5s | %-30s | %-20s | %-15s | %-4s | %-30s | %-10s | %-10s | %-8s | %-8s | %-12s\n",
                "ID", "Title", "Author", "Publisher", "Year", "Category", "Location", "Language", "Qty", "Avail",
                "Penalty");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Book book : list) {
            System.out.printf("%-5d | %-30s | %-20s | %-15s | %-4d | %-30s | %-10s | %-10s | %-8d | %-8d | %-12d\n",
                    book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getPublisher(),
                    book.getYear_published(), book.getCategory(), book.getLocation(),
                    book.getLanguage(), book.getQuantity(), book.getAvailable(), book.getPenalty_rate());
        }
    }

    private String checkStrInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            return input;
        }
    }

    private void findBook() {
        String keyword = checkStrInput("Nhập ID sách hoặc tên sách để tìm (Muốn thoát vui lòng chon số 0): ");
        if (keyword.matches("\\d+")) {
            int book_id = Integer.parseInt(keyword);
            Book book = bookController.findBookByID(book_id);
            if (book == null) {
                System.out.println("Không thể tìm thấy sách!");
                return;
            }
            System.out.println("==== Thông tin sách ====");
            System.out.println("ID sách:          " + book.getBook_Id());
            System.out.println("Tiêu đề:          " + book.getTitle());
            System.out.println("Tác giả:          " + book.getAuthor());
            System.out.println("Nhà xuất bản:     " + book.getPublisher());
            System.out.println("Năm xuất bản:     " + book.getYear_published());
            System.out.println("Thể loại:         " + book.getCategory());
            System.out.println("Vị trí:           " + book.getLocation());
            System.out.println("Ngôn ngữ:         " + book.getLanguage());
            System.out.println("Số lượng:         " + book.getQuantity());
            System.out.println("Số lượng có sẵn:  " + book.getAvailable());
            System.out.println("Mức phạt:         " + book.getPenalty_rate() + " VND/day");
        }
        else {
            ArrayList<Book> list = bookController.findBookByName(keyword);
            if(list.isEmpty()){
                System.out.println("Không tìm thấy sách nào phù hợp!");
            }
            else{
                System.out.println("==== Danh sách sách tìm kiếm ====");
                System.out.printf("%-5s | %-30s | %-20s | %-15s | %-4s | %-30s | %-10s | %-10s | %-8s | %-8s | %-12s\n",
                        "ID", "Title", "Author", "Publisher", "Year", "Category", "Location", "Language", "Qty", "Avail",
                        "Penalty");
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                for (Book book : list) {
                    System.out.printf("%-5d | %-30s | %-20s | %-15s | %-4d | %-30s | %-10s | %-10s | %-8d | %-8d | %-12d\n",
                            book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getPublisher(),
                            book.getYear_published(), book.getCategory(), book.getLocation(),
                            book.getLanguage(), book.getQuantity(), book.getAvailable(), book.getPenalty_rate());
                }
            }
        }
    }

    private int checkIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Phải nhập một số nguyên hợp lệ!");
            }
        }
    }

    private void requestBorrowBook(int userId) {
        System.out.println("==== Gửi yêu cầu mượn sách ====");
        int bookId = checkIntInput("Nhập ID sách muốn mượn (Muốn thoát chọn số 0): ");
        Book book = bookController.findBookByID(bookId);
        if (book == null) {
            System.out.println("Không tìm thấy sách có ID " + bookId + ".");
            return;
        }
        Date requestDate = null;
        while (requestDate == null) {
            String dateString = checkStrInput("Nhập ngày mượn (yyyy-MM-dd): ");
            try {
                requestDate = dateFormatter.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
            }
        }
        BorrowRequest borrowRequest = new BorrowRequest(userId, bookId, requestDate);
        borrowController.requestBorrowBook(borrowRequest);
    }
}
