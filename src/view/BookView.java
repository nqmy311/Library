package view;

import java.util.Scanner;
import java.util.ArrayList;

import Controller.BookController;
import model.Book;
import model.TablePrinter;

public class BookView {
    private final BookController controller = new BookController();
    private final Scanner scanner = new Scanner(System.in);

    public void showBookMenu() {
        while (true) {
            System.out.println("==== QUẢN LÝ SÁCH ====");
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
                // e.printStackTrace();
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
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

    private String checkNameInput() {
        while (true) {
            System.out.print("Tác giả: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            boolean valid = true;
            for (char c : input.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Tên chỉ được chứa chữ cái và khoảng trắng!");
                continue;
            }
            return input;
        }
    }

    private String checkLanguageInput() {
        while (true) {
            System.out.print("Ngôn ngữ: ");
            String input = scanner.nextLine().trim();
            boolean valid = true;
            for (char c : input.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Ngôn ngữ chỉ được chứa chữ cái và khoảng trắng!");
                continue;
            }
            return input;
        }
    }

    private String checkPublisherInput() {
        while (true) {
            System.out.print("Nhà xuất bản: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            if (input.matches("[\\p{L} .\\-]+")) {
                return input;
            } else {
                System.out.println("Tên nhà xuất bản không hợp lệ! Chỉ cho phép chữ cái, khoảng trắng, dấu chấm và gạch nối.");
            }
        }
    }

    private String checkCategoryInput() {
        while (true) {
            System.out.print("Thể loại: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            if (input.matches("[0-9]+")) {
                System.out.println("Thể loại không hợp lệ! Không thể chỉ chứa số.");
                continue;
            }
            if (input.matches("[\\p{L}0-9 .\\-]+")) {
                if (Character.isDigit(input.charAt(0))) {
                    System.out.println("Thể loại không hợp lệ! Không được bắt đầu bằng số.");
                    continue;
                }
                return input;
            } else {
                System.out.println("Thể loại không hợp lệ! Chỉ cho phép chữ cái, số, khoảng trắng, dấu chấm và gạch nối.");
            }
        }
    }

    private void addBook() {
        Book book = new Book();
        System.out.println("Nhập thông tin sách muốn thêm:");
        book.setTitle(checkStrInput("Tiêu đề sách: "));
        book.setAuthor(checkNameInput());
        book.setPublisher(checkPublisherInput());
        int year_published = checkIntInput("Năm xuất bản: ");
        book.setYear_published(year_published);
        book.setCategory(checkCategoryInput());
        System.out.print("Vị trí: ");
        book.setLocation(scanner.nextLine().trim());
        book.setLanguage(checkLanguageInput());
        int quantity = checkIntInput("Số lượng sách: ");
        int available;
        do {
            available = checkIntInput("Số lượng sách có sẵn: ");
            if (available > quantity) {
                System.out.println("Số lượng sách có sẵn không thể lớn hơn số lượng sách. Vui lòng nhập lại!");
            }
        } while (available > quantity);
        book.setQuantity(quantity);
        book.setAvailable(available);
        book.setPenalty_rate(checkIntInput("Mức phạt: "));
        boolean success = controller.addBook(book);
        if (success) {
            System.out.println("Thêm sách thành công!");
        } else {
            System.out.println("Thêm sách thất bại!");
        }
    }

    private void listBooks() {
        ArrayList<Book> list = controller.listBook();
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

    private void updateBook() {
        int id = checkIntInput("Nhập ID sách cần sửa (Nếu muốn thoát vui lòng nhập số 0): ");
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

        do {
            System.out.print("Tác giả (" + book.getAuthor() + "): ");
            String author = scanner.nextLine().trim();
            if (author.isEmpty()) {
                break;
            }
            boolean valid = true;
            for (char c : author.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Tên chỉ được chứa chữ cái và khoảng trắng!");
            } else {
                book.setAuthor(author);
                break;
            }
        } while (true);

        do {
            System.out.print("Nhà xuất bản (" + book.getPublisher() + "): ");
            String publisher = scanner.nextLine().trim();
            if (publisher.isEmpty()) {
                break;
            }
            if (publisher.matches("[\\p{L} .\\-]+")) {
                book.setPublisher(publisher);
                break;
            } else {
                System.out.println("Tên nhà xuất bản không hợp lệ! Chỉ cho phép chữ cái, khoảng trắng, dấu chấm và gạch nối.");
            }
        } while (true);

        do {
            System.out.print("Năm xuất bản (" + book.getYear_published() + "): ");
            String year = scanner.nextLine().trim();
            if (year.isEmpty()) {
                break;
            }
            try {
                int parsedYear = Integer.parseInt(year);
                if (parsedYear <= 0) {
                    System.out.println("Năm xuất bản phải lớn hơn 0. Nhập lại!");
                    continue;
                }
                book.setYear_published(parsedYear);
                break;
            } catch (Exception e) {
                System.out.println("Năm không hợp lệ. Vui lòng nhập lại!");
            }
        } while (true);

        do {
            System.out.print("Thể loại (" + book.getCategory() + "): ");
            String category = scanner.nextLine().trim();
            if (category.isEmpty()) {
                break;
            }
            if (category.matches("[0-9]+")) {
                System.out.println("Thể loại không hợp lệ! Không thể chỉ chứa số.");
                continue;
            }
            if (category.matches("[\\p{L}0-9 .\\-]+")) {
                if (Character.isDigit(category.charAt(0))) {
                    System.out.println("Thể loại không hợp lệ! Không được bắt đầu bằng số.");
                    continue;
                }
                book.setCategory(category);
            } else {
                System.out.println("Thể loại không hợp lệ! Chỉ cho phép chữ cái, số, khoảng trắng, dấu chấm và gạch nối.");
            }
        } while (true);

        System.out.print("Vị trí (" + book.getLocation() + "): ");
        String location = scanner.nextLine().trim();
        if (!location.isEmpty()) {
            book.setLocation(location);
        }

        do {
            System.out.print("Ngôn ngữ (" + book.getLanguage() + "): ");
            String language = scanner.nextLine().trim();
            if (language.isEmpty()) {
                break;
            }
            boolean valid = true;
            for (char c : language.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println("Ngôn ngữ chỉ được chứa chữ cái và khoảng trắng!");
            } else {
                book.setLanguage(language);
                break;
            }
        } while (true);

        int quantity;
        do {
            System.out.print("Số lượng sách (" + book.getQuantity() + "): ");
            String quantityInput = scanner.nextLine().trim();
            if (!quantityInput.isEmpty()) {
                try {
                    quantity = Integer.parseInt(quantityInput);
                    if (quantity <= 0) {
                        System.out.println("Số lượng sách không được nhỏ hơn 0.");
                        continue;
                    }
                    book.setQuantity(quantity);
                    break;
                } catch (Exception e) {
                    System.out.println("Số lượng sách không hợp lệ.");
                }
            } else {
                quantity = book.getQuantity();
                break;
            }
        } while (true);

        int available;
        do {
            System.out.print("Số lượng sách có sẵn (" + book.getAvailable() + "): ");
            String availableInput = scanner.nextLine().trim();
            if (!availableInput.isEmpty()) {
                try {
                    available = Integer.parseInt(availableInput);
                    if (available < 0) {
                        System.out.println("Số lượng sách có sẵn không được nhỏ hơn 0.");
                        continue;
                    }
                    if (available > quantity) {
                        System.out.println("Số lượng sách có sẵn không thể lớn hơn số lượng tổng.");
                        continue;
                    }
                    book.setAvailable(available);
                    break;
                } catch (Exception e) {
                    System.out.println("Số lượng sách có sẵn không hợp lệ.");
                }
            } else {
                break;
            }
        } while (true);

        do {
            System.out.print("Mức phạt (" + book.getPenalty_rate() + "): ");
            String penalty = scanner.nextLine().trim();
            if (penalty.isEmpty()) {
                break;
            }
            try {
                int parsedPenalty = Integer.parseInt(penalty);
                if (parsedPenalty < 0) {
                    System.out.println("Mức phạt không được nhỏ hơn 0. Vui lòng nhập lại!");
                    continue;
                }
                book.setPenalty_rate(parsedPenalty);
                break;
            } catch (Exception e) {
                System.out.println("Mức phạt không hợp lệ. Vui lòng nhập lại!");
            }
        } while (true);

        boolean success = controller.updateBook(book);
        if (success) {
            System.out.println("Cập nhật sách thành công!");
        } else {
            System.out.println("Cập nhật sách thất bại.");
        }
    }

    private void deleteBook() {
        int id = checkIntInput("Nhập ID sách cần xoá (Nếu muốn thoát vui lòng nhập số 0): ");
        boolean success = controller.deleteBook(id);
        if (success) {
            System.out.println("Xoá sách thành công!");
        } else {
            System.out.println("Không tìm thấy sách.");
        }
    }

    private void findBook() {
        String keyword = checkStrInput("Nhập ID sách hoặc tên sách để tìm (Muốn thoát vui lòng nhập số 0): ");
        if (keyword.matches("\\d+")) {
            int book_id = Integer.parseInt(keyword);
            Book book = controller.findBookByID(book_id);
            if (book == null) {
                System.out.println("Không thể tìm thấy sách nào phù hợp!");
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
        } else {
            ArrayList<Book> list = controller.findBookByName(keyword);
            if (list.isEmpty()) {
                System.out.println("Không thể tìm thấy sách nào phù hợp!");
            } else {
                System.out.printf("%75s\n", " DANH SÁCH SÁCH TÌM KIẾM ");
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
}
