package view;

import Controller.BorrowController;
import model.BorrowRecord;
import model.BorrowRequest;
import model.TablePrinter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;


public class BorrowView {
    private final BorrowController controller;
    private final Scanner scanner = new Scanner(System.in);

    public BorrowView(BorrowController controller) {
        this.controller = controller;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String getBookConditionInput() {
        while (true) {
            System.out.println("Tình trạng sách khi trả:");
            System.out.println("1. Bình thường");
            System.out.println("2. Bị hỏng");
            System.out.println("3. Bị mất");
            System.out.print("Chọn (1-3): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        return "Bình thường";
                    case 2:
                        return "Bị hỏng";
                    case 3:
                        return "Bị mất";
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        }
    }

    public void showBorrowingManagementMenu() {
        while (true) {
            System.out.println("======== QUẢN LÝ MƯỢN SÁCH ========");
            System.out.println("1. Xem tất cả yêu cầu mượn");
            System.out.println("2. Phê duyệt yêu cầu mượn");
            System.out.println("3. Từ chối yêu cầu mượn");
            System.out.println("0. Thoát");
            System.out.println("===================================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        controller.viewAllBorrowRequests();
                        break;
                    case 2:
                        int requestIdApprove = checkIntInput("Nhập ID của yêu cầu mượn cần phê duyệt (Muốn thoát vui lòng nhập số 0): ");
                        if (requestIdApprove == 0) {
                            System.out.println("Hủy thao tác phê duyệt sách.");
                            break;
                        }
                        Date borrowDate = checkDateInput("Nhập ngày mượn");
                        if (borrowDate == null) {
                            System.out.println("Hủy thao tác phê duyệt sách.");
                            break;
                        }
                        controller.approveBorrowRequest(requestIdApprove, borrowDate);
                        break;
                    case 3:
                        int requestIdReject = checkIntInput("Nhập ID của yêu cầu mượn cần từ chối (Muốn thoát vui lòng nhập số 0): ");
                        if (requestIdReject == 0) {
                            break;
                        }
                        controller.rejectBorrowRequest(requestIdReject);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        }
    }

    public void showReturningManagementMenu() {
        while (true) {
            System.out.println("======== QUẢN LÝ TRẢ SÁCH ========");
            System.out.println("1. Xem danh sách yêu cầu trả sách");
            System.out.println("2. Đánh dấu xác nhận trả sách");
            System.out.println("0. Thoát");
            System.out.println("==================================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        controller.viewAllReturnRequests();
                        break;
                    case 2:
                        int recordId = checkIntInput("Nhập ID phiếu mượn yêu cầu trả (Muốn thoát vui lòng nhập số 0): ");
                        if (recordId == 0) {
                            System.out.println("Hủy thao tác xác nhận trả sách.");
                            break;
                        }
                        Date returnDate = checkDateInput("Nhập ngày trả");
                        if (returnDate == null) {
                            System.out.println("Hủy thao tác xác nhận trả sách.");
                            break;
                        }
                        controller.markBookAsReturned(recordId, returnDate);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
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

    public Date checkDateInput(String prompt) {
        while (true) {
            System.out.print(prompt + " (yyyy-MM-dd, muốn thoát vui lòng nhập số 0): ");
            String dateString = scanner.nextLine().trim();
            if (dateString.isEmpty()) {
                System.out.println("Không được để trống!");
                continue;
            }
            if (dateString.equals("0")) {
                return null;
            }
            try {
                LocalDate localDate = LocalDate.parse(dateString);
                return java.sql.Date.valueOf(localDate);
            } catch (Exception e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
            }
        }
    }

    public void displayReturnRequests(ArrayList<BorrowRecord> borrowRecords) {
        System.out.printf("%60s\n", "DANH SÁCH YÊU CẦU TRẢ");
        if (borrowRecords.isEmpty()) {
            System.out.printf("%60s\n", "Không có yêu cầu trả nào!");
            return;
        }
        String[] headers = {"ID Phiếu", "ID Người Dùng", "Tên Người Dùng", "ID Sách", "Tên Sách", "Ngày mượn", "Ngày đến hạn", "Trạng thái"};
        ArrayList<Object[]> rows = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            rows.add(new Object[]{
                    record.getRecord_id(), record.getUserId(), record.getUserName(),
                    record.getBookId(), record.getBookTitle(),
                    record.getBorrowDate(), record.getDueDate(), record.getStatus()
            });
        }
        TablePrinter.printTable(headers, rows);
    }

    public void displayBorrowRequests(ArrayList<BorrowRequest> borrowRequests) {
        System.out.printf("%60s\n", " DANH SÁCH CÁC YÊU CẦU MƯỢN SÁCH ");
        if (borrowRequests.isEmpty()) {
            System.out.printf("%60s\n", " Không có yêu cầu mượn nào! ");
            return;
        }
        String[] headers = {"ID Yêu cầu", "ID Tài khoản", "Tên Người Dùng", "ID Sách", "Tên Sách", "Ngày yêu cầu mượn", "Trạng thái"};
        ArrayList<Object[]> rows = new ArrayList<>();
        for (BorrowRequest request : borrowRequests) {
            rows.add(new Object[]{
                    request.getRequestId(), request.getUserId(), request.getUserName(),
                    request.getBookId(), request.getBookTitle(),
                    request.getRequestDate(), request.getStatus()
            });
        }
        TablePrinter.printTable(headers, rows);
    }

    public void displayBorrowRecords(ArrayList<BorrowRecord> borrowRecords) {
        System.out.printf("%80s\n", "LỊCH SỬ MƯỢN TRẢ");
        if (borrowRecords.isEmpty()) {
            System.out.printf("%75s\n", "Không có bản ghi mượn trả nào!");
            return;
        }
        String[] headers = {"ID Phiếu", "ID Người Dùng", "Tên Người Dùng", "Tên Sách", "ID Sách",
                "Ngày mượn", "Ngày trả", "Ngày đến hạn", "Trạng thái", "Tình trạng sách"
        };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            rows.add(new Object[]{
                    record.getRecord_id(), record.getUserId(), record.getUserName(), record.getBookTitle(), record.getBookId(),
                    record.getBorrowDate(), record.getReturnDate() != null ? record.getReturnDate() : "Chưa trả",
                    record.getDueDate(), record.getStatus(), record.getBook_condition()
            });
        }
        TablePrinter.printTable(headers, rows);
    }

    public void displayAllBorrowRequests(ArrayList<BorrowRequest> borrowRequests) {
        System.out.printf("%80s\n", "DANH SÁCH TẤT CẢ YÊU CẦU MƯỢN SÁCH");
        if (borrowRequests.isEmpty()) {
            System.out.printf("%80s\n", "Không có yêu cầu mượn nào!");
            return;
        }
        String[] headers = {"ID Yêu cầu", "ID Tài khoản", "Tên Người Dùng", "ID Sách", "Tên Sách", "Ngày yêu cầu mượn", "Trạng thái"};
        ArrayList<Object[]> rows = new ArrayList<>();
        for (BorrowRequest request : borrowRequests) {
            rows.add(new Object[]{
                    request.getRequestId(), request.getUserId(), request.getUserName(),
                    request.getBookId(), request.getBookTitle(),
                    request.getRequestDate(), request.getStatus()
            });
        }
        TablePrinter.printTable(headers, rows);
    }

    public void displayAllBorrowRecords(ArrayList<BorrowRecord> borrowRecords) {
        System.out.printf("%80s\n", "DANH SÁCH TẤT CẢ PHIẾU MƯỢN SÁCH");
        if (borrowRecords.isEmpty()) {
            System.out.printf("80s\n", "Không có phiếu mượn nào!");
            return;
        }
        String[] headers = {"ID Phiếu", "ID Người Dùng", "Tên Người Dùng", "Tên Sách", "ID Sách",
                "Ngày mượn", "Ngày trả", "Ngày đến hạn", "Trạng thái", "Tình trạng sách"
        };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (BorrowRecord record : borrowRecords) {
            rows.add(new Object[]{
                    record.getRecord_id(), record.getUserId(), record.getUserName(), record.getBookTitle(),
                    record.getBookId(), record.getBorrowDate(), record.getReturnDate() != null ? record.getReturnDate() : "Chưa trả",
                    record.getDueDate(), record.getStatus(), record.getBook_condition()
            });
        }
        TablePrinter.printTable(headers, rows);

    }
}
