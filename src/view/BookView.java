package view;

import java.util.Scanner;
import java.util.ArrayList;
import Controller.BookController;
import model.Book;

public class BookView {
    private final BookController controller = new BookController();
    private final Scanner scanner = new Scanner(System.in);

    public void showBookMenu() {
        while (true) {
            System.out.println("==== Quản lý sách ====");
            System.out.println("1. Xem thông tin sách");
            System.out.println("2. Thêm thông tin sách");
            System.out.println("3. Sửa thông tin sách");
            System.out.println("4. Xoá thông tin sách");
            System.out.println("5. Tìm kiếm sách");
            System.out.println("0. Thoát");
            System.out.println("======================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        listBooks();
                        break;
                    case 2:
                        addBook();
                        break;
                    case 3:
                        updateBook();
                        break;
                    case 4:
                        deleteBook();
                        break;
                    case 5:
                        findBook();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Đã xảy ra lỗi!");
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

    private void addBook() {
        Book book = new Book();
        System.out.println("Nhập thông tin sách muốn thêm:");
        book.setTitle(checkStrInput("Tiêu đề sách: "));
        book.setAuthor(checkStrInput("Tác giả: "));
        book.setPublisher(checkStrInput("Nhà xuất bản: "));
        book.setYear_published(checkIntInput("Năm xuất bản: "));
        System.out.print("Thể loại: ");
        book.setCategory(scanner.nextLine().trim());
        System.out.print("Vị trí: ");
        book.setLocation(scanner.nextLine().trim());
        System.out.print("Ngôn ngữ: ");
        book.setLanguage(scanner.nextLine().trim());
        book.setQuantity(checkIntInput("Số lượng sách:"));
        book.setAvailable(checkIntInput("Số lượng sách có sẵn: "));
        System.out.print("Mức phạt: ");
        book.setPenalty_rate(Integer.parseInt(scanner.nextLine().trim()));
        boolean success = controller.addBook(book);
        if (success) {
            System.out.println("Thêm sách thành công!");
        } else {
            System.out.println("Thêm sách thất bại!");
        }
    }

    private void listBooks() {
        ArrayList<Book> list = controller.listBook();
        System.out.println("==== Danh sách sách ====");
        System.out.printf("%-5s | %-30s | %-20s | %-15s | %-4s | %-30s | %-10s | %-10s | %-8s | %-8s | %-12s\n",
                "ID", "Title", "Author", "Publisher", "Year", "Category", "Location", "Language", "Qty", "Avail",
                "Penalty");
        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Book book : list) {
            System.out.printf("%-5d | %-30s | %-20s | %-15s | %-4d | %-30s | %-10s | %-10s | %-8d | %-8d | %-12s\n",
                    book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getPublisher(),
                    book.getYear_published(), book.getCategory(), book.getLocation(),
                    book.getLanguage(), book.getQuantity(), book.getAvailable(), String.format("%d VND", book.getPenalty_rate()));
        }
    }

    private void updateBook() {
        int id = checkIntInput("Nhập ID sách cần sửa (Nếu muốn thoát vui lòng chọn số 0): ");
        Book book = controller.findBookByID(id);
        if (book == null) {
            System.out.println("Không thể tìm thấy sách!");
            return;
        }
        System.out.println("Nhấn Enter nếu không muốn thay đổi một thông tin nào đó.");
        System.out.print("Tiêu đề (" + book.getTitle() + "): ");
        String title = scanner.nextLine().trim();
        if (!title.isEmpty()) {
            book.setTitle(title);
        }
        
        System.out.print("Tác giả (" + book.getAuthor() + "): ");
        String author = scanner.nextLine().trim();
        if (!author.isEmpty()) {
            book.setAuthor(author);
        }

        System.out.print("Nhà xuất bản (" + book.getPublisher() + "): ");
        String publisher = scanner.nextLine().trim();
        if (!publisher.isEmpty()) {
            book.setPublisher(publisher);
        }

        System.out.print("Năm xuất bản (" + book.getYear_published() + "): ");
        String year = scanner.nextLine().trim();
        if (!year.isEmpty()) {
            try {
                book.setYear_published(Integer.parseInt(year));
            } catch (Exception e) {
                System.out.println("Năm không hợp lệ. Bỏ qua.");
            }
        }

        System.out.print("Thể loại (" + book.getCategory() + "): ");
        String category = scanner.nextLine().trim();
        if (!category.isEmpty()) {
            book.setCategory(category);
        }

        System.out.print("Vị trí (" + book.getLocation() + "): ");
        String location = scanner.nextLine().trim();
        if (!location.isEmpty()) {
            book.setLocation(location);
        }

        System.out.print("Ngôn ngữ (" + book.getLanguage() + "): ");
        String language = scanner.nextLine().trim();
        if (!language.isEmpty()) {
            book.setLanguage(language);
        }

        System.out.print("Số lượng (" + book.getQuantity() + "): ");
        String quantity = scanner.nextLine().trim();
        if (!quantity.isEmpty()) {
            try {
                book.setQuantity(Integer.parseInt(quantity));
            } catch (Exception e) {
                System.out.println("Số lượng không hợp lệ. Bỏ qua.");
            }
        }

        System.out.print("Số lượng có sẵn (" + book.getAvailable() + "): ");
        String available = scanner.nextLine().trim();
        if (!available.isEmpty()) {
            try {
                book.setAvailable(Integer.parseInt(available));
            } catch (Exception e) {
                System.out.println("Số lượng có sẵn không hợp lệ. Bỏ qua.");
            }
        }

        System.out.print("Mức phạt (" + book.getPenalty_rate() + "): ");
        String penalty = scanner.nextLine().trim();
        if (!penalty.isEmpty()) {
            try {
                book.setPenalty_rate(Integer.parseInt(penalty));
            } catch (Exception e) {
                System.out.println("Mức phạt không hợp lệ. Bỏ qua.");
            }
        }

        boolean success = controller.updateBook(book);
        if (success) {
            System.out.println("Cập nhật sách thành công!");
        } else {
            System.out.println("Cập nhật sách thất bại.");
        }
    }

    private void deleteBook() {
        int id = checkIntInput("Nhập ID sách cần xoá (Nếu muốn thoát vui lòng chọn số 0): ");
        boolean success = controller.deleteBook(id);
        if (success) {
            System.out.println("Xoá sách thành công!");
        } else {
            System.out.println("Không tìm thấy sách.");
        }
    }

    private void findBook() {
        String keyword = checkStrInput("Nhập ID sách hoặc tên sách để tìm (Muốn thoát vui lòng chon số 0): ");
        if (keyword.matches("\\d+")) {
            int book_id = Integer.parseInt(keyword);
            Book book = controller.findBookByID(book_id);
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
            System.out.println("Mức phạt:         " + book.getPenalty_rate() + " VND");
        }
        else {
            ArrayList<Book> list = controller.findBookByName(keyword);
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
                    System.out.printf("%-5d | %-30s | %-20s | %-15s | %-4d | %-30s | %-10s | %-10s | %-8d | %-8d | %-12s\n",
                            book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getPublisher(),
                            book.getYear_published(), book.getCategory(), book.getLocation(),
                            book.getLanguage(), book.getQuantity(), book.getAvailable(), String.format("%d VND", book.getPenalty_rate()));
                }
            }
        }
    }
}
