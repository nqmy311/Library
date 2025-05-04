package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import Controller.BookCommentController;
import Controller.ViolationController;
import model.BorrowRequest;
import model.TablePrinter;
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
    private final java.sql.Connection dbConnection = DBConnection.getConnection();
    private final BookCommentView bookCommentView = new BookCommentView(new BookCommentController(dbConnection, new BookCommentView(null)));

    public void showMenu(User user) {
        while (true) {
            System.out.println("======== GIAO DIỆN NGƯỜI DÙNG THƯ VIỆN ========");
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
            System.out.println("===============================================");
            System.out.print("Chọn chức năng: ");
            try {
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
                        System.out.println("LƯU Ý: Trả muộn sách sẽ bị phạt 10.000đ/ngày. Sách bị hỏng hoặc mất sẽ bị xử phạt theo giá trị quy định đối với từng loại sách.");
                        requestBorrowBook(user.getUser_Id());
                        break;
                    case 6:
                        borrowController.viewBorrowRequests(user.getUser_Id());
                        break;
                    case 7:
                        System.out.println("======== GỬI YÊU CẦU TRẢ SÁCH ========");
                        int recordIdReturn = checkIntInput("Nhập phiếu mượn muốn trả (Muốn thoát vui lòng nhập số 0): ");
                        if(recordIdReturn == 0){
                            System.out.println("Huỷ thao tác yêu cầu trả sách.");
                            break;
                        }
                        borrowController.requestReturnBook(recordIdReturn, user.getUser_Id());
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
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    private void showProfile(User user) {
        System.out.println("======== THÔNG TIN TÀI KHOẢN ========");
        System.out.println("ID tài khoản:           " + user.getUser_Id());
        System.out.println("Tên đăng nhập:          " + user.getUsername());
        System.out.println("Tên chủ tài khoản:      " + user.getName());
        System.out.println("Email:                  " + user.getEmail());
        System.out.println("Số điện thoại:          " + user.getPhone());
        System.out.println("Địa chỉ:                " + user.getAddress());
        System.out.println("=====================================");
    }

    private void listBooks() {
        ArrayList<Book> list = bookController.listBook();
        System.out.printf("%100s\n", " DANH SÁCH SÁCH TRONG THƯ VIỆN ");
        String[] headers = {
                "ID sách", "Tiêu đề", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "Thể loại", "Vị trí", "Ngôn ngữ",
                "Số lượng",
                "Số lượng có sẵn", "Mức phạt"
        };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Book book : list) {
            rows.add(new Object[]{
                    book.getBook_Id(), book.getTitle(), book.getAuthor(),
                    book.getPublisher(), book.getYear_published(), book.getCategory(),
                    book.getLocation(), book.getLanguage(),
                    book.getQuantity(), book.getAvailable(), book.getPenalty_rate()
            });
        }
        TablePrinter.printTable(headers, rows);
    }

    private void findBook() {
        String keyword = checkStrInput("Nhập ID sách hoặc tên sách để tìm (Muốn thoát vui lòng nhập số 0): ");
        if (keyword.matches("\\d+")) {
            int book_id = Integer.parseInt(keyword);
            Book book = bookController.findBookByID(book_id);
            if (book == null) {
                System.out.println("Không thể tìm thấy sách!");
                return;
            }
            System.out.println("======== THÔNG TIN SÁCH TÌM KIẾM ========");
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
            System.out.println("Mức phạt:         " + book.getPenalty_rate() + " VND");
            System.out.println("=========================================");
        } else {
            ArrayList<Book> list = bookController.findBookByName(keyword);
            if (list.isEmpty()) {
                System.out.println("Không tìm thấy sách nào phù hợp!");
            } else {
                System.out.printf("%75s\n", "DANH SÁCH SÁCH TÌM KIẾM");
                String[] headers = {"ID sách", "Tiêu đề", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "Thể loại",
                        "Vị trí", "Ngôn ngữ", "Số lượng",
                        "Số lượng có sẵn", "Mức phạt"
                };
                ArrayList<Object[]> rows = new ArrayList<>();
                for (Book book : list) {
                    rows.add(new Object[]{
                            book.getBook_Id(), book.getTitle(), book.getAuthor(),
                            book.getPublisher(), book.getYear_published(), book.getCategory(),
                            book.getLocation(), book.getLanguage(),
                            book.getQuantity(), book.getAvailable(), book.getPenalty_rate()
                    });
                }
                TablePrinter.printTable(headers, rows);
            }
        }
    }

    private void requestBorrowBook(int userId) {
        System.out.println("======== GỬI YÊU CẦU MƯỢN SÁCH ========");
        int bookId = Integer.parseInt(checkStrInput("Nhập ID sách muốn mượn (Muốn thoát vui lòng nhập số 0): "));
        if (bookId == 0) {
            System.out.println("Hủy yêu cầu mượn sách.");
            return;
        }
        Book book = bookController.findBookByID(bookId);
        if (book == null) {
            System.out.println("Không tìm thấy sách có ID " + bookId + ".");
            return;
        }
        Date requestDate = null;
        while (requestDate == null) {
            String dateString = checkStrInput("Nhập ngày gửi yêu cầu mượn (yyyy-MM-dd) (Nhập 0 để thoát): ");
            if (dateString.equals("0")) {
                System.out.println("Hủy yêu cầu mượn sách.");
                return;
            }
            try {
                LocalDate localDate = LocalDate.parse(dateString);
                requestDate = java.sql.Date.valueOf(localDate);
            } catch (Exception e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
            }
        }
        BorrowRequest borrowRequest = new BorrowRequest(userId, bookId, requestDate);
        borrowController.requestBorrowBook(borrowRequest);
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
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Giá trị không được nhỏ hơn 0!");
                    continue;
                }
                return value;
            } catch (Exception e) {
                System.out.println("Yêu cầu nhập một số nguyên hợp lệ!");
            }
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
}
