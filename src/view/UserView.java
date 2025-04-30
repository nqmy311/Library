package view;

import java.util.ArrayList;
import java.util.Scanner;

import Controller.ViolationController;
import model.User;
import Controller.BookController;
import model.Book;
import Controller.BorrowController;

public class UserView {
    private final Scanner scanner = new Scanner(System.in);
    private final BookController bookController = new BookController();
    private final BorrowController borrowController = new BorrowController();
    private final ViolationController violationcontroller = new ViolationController();
    private final ViolationView violationView = new ViolationView(violationcontroller);

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void showMenu(User user) {
        System.out.println("==== GIAO DIỆN NGƯỜI DÙNG THƯ VIỆN====");
        System.out.println("1. Xem thông tin tài khoản cá nhân");
        System.out.println("2. Xem thông tin sách");
        System.out.println("3. Tìm kiếm sách");
        System.out.println("4. Xem thông tin mượn / trả sách");
        System.out.println("5. Gửi yêu cầu mượn sách");
        System.out.println("6. Gửi yêu cầu trả sách");
        System.out.println("7. Đánh giá sách");
        System.out.println("8. Xem vi phạm của tài khoản");
        System.out.println("0. Đăng xuất");
        System.out.println("======================================");
        System.out.print("Chọn chức năng: ");
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
                borrowController.viewAllBorrowRecords();
                break;
            case 5:
                //BorrowView
                break;
            case 6:
                borrowController.viewBorrowRecords(user.getUser_Id());
                break;
            case 7:
                violationcontroller.viewViolationsByUserId(user.getUser_Id());
                break;
            case 8:

            case 0:
                System.out.println("Đăng xuất ...");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                break;
        }
        scanner.close();
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

}
