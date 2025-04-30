package view;

import Controller.BorrowController;
import model.BorrowRecord;
import model.BorrowRequest;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BorrowView {
    private final BorrowController controller;
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

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
            } catch (Exception e){
                //e.printStackTrace();
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        }
    }

    public void showBorrowingManagementMenu() {
        while (true) {
            System.out.println("==== Quản lý mượn sách ====");
            System.out.println("1. Xem tất cả yêu cầu mượn");
            System.out.println("2. Phê duyệt yêu cầu mượn");
            System.out.println("3. Từ chối yêu cầu mượn");
            System.out.println("0. Thoát");
            System.out.println("===========================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        controller.viewAllBorrowRequests();
                        break;
                    case 2:
                        int requestIdApprove = checkIntInput("Nhập ID của yêu cầu mượn cần phê duyệt: ");
                        Date borrowDate = checkDateInput("Nhập ngày mượn");
                        controller.approveBorrowRequest(requestIdApprove, borrowDate);
                        break;
                    case 3:
                        int requestIdReject = checkIntInput("Nhập ID của yêu cầu mượn cần từ chối: ");
                        controller.rejectBorrowRequest(requestIdReject);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } catch (Exception e){
                //e.printStackTrace();
                System.out.println("Đã xảy ra lỗi!");
            }
        }
    }

    public void showReturningManagementMenu(){
        while (true) {
            System.out.println("==== Quản lý trả sách ====");
            System.out.println("1. Xem danh sách yêu cầu trả");
            System.out.println("2. Đánh dấu xác nhận trả sách");
            System.out.println("0. Thoát");
            System.out.println("===========================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        controller.viewAllReturnRequests();
                        break;
                    case 2:
                        int recordId = checkIntInput("Nhập ID phiếu mượn yêu cầu trả: ");
                        if(recordId == 0){
                            break;
                        }
                        Date returnDate = checkDateInput("Nhập ngày trả");
                        controller.markBookAsReturned(recordId, returnDate);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } catch (Exception e){
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

    public Date checkDateInput(String prompt) {
        while(true){
            System.out.print(prompt + " (yyyy-MM-dd): ");
            String dateString = scanner.nextLine().trim();
            if(dateString.isEmpty()){
                System.out.println("Không được để trống!");
                continue;
            }
            try {
                return dateFormatter.parse(dateString);
            } catch (Exception e){
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
            }
        }
    }

    public void displayReturnRequests(ArrayList<BorrowRecord> borrowRecords){
        System.out.println("==== Danh sách yêu cầu trả ====");
        if (borrowRecords.isEmpty()) {
            System.out.println("Không có bản ghi mượn trả nào.");
            return;
        }
        System.out.printf("%-10s | %-12s | %-15s | %-30s | %-10s | %-20s | %-15s | %-15s | %-15s | %-15s%n",
                "ID Phiếu", "ID Người Dùng", "Tên Người Dùng", "Tên Sách", "ID Sách", "Ngày mượn", "Ngày trả", "Ngày đến hạn", "Trạng thái", "Tình trạng sách");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        for (BorrowRecord record : borrowRecords) {
            System.out.printf("%-10d | %-12d | %-15s | %-30s | %-10d | %-20s | %-15s | %-15s | %-15s | %-15s%n",
                    record.getRecord_id(), record.getUserId(), record.getUserName(), record.getBookTitle(), record.getBookId(), record.getBorrowDate(),
                    record.getReturnDate() != null ? record.getReturnDate() : "Chưa trả", record.getDueDate(), record.getStatus(), record.getBook_condition());
        }
    }

    public void displayBorrowRequests(ArrayList<BorrowRequest> borrowRequests) {
        System.out.println("Danh sách các yêu cầu mượn sách:");
        if (borrowRequests.isEmpty()) {
            System.out.println("Không có yêu cầu mượn nào.");
            return;
        }
        System.out.printf("%-10s%-12s%-20s%-10s%-30s%-20s%-20s%n", "ID Yêu cầu", "ID Tài khoản", "Tên Người Dùng", "ID Sách", "Tên Sách", "Ngày yêu cầu mượn", "Trạng thái");
        for (BorrowRequest request : borrowRequests) {
            System.out.printf("%-10d%-12d%-20s%-10d%-30s%-20s%-20s%n",
                    request.getRequestId(), request.getUserId(), request.getUserName(), request.getBookId(), request.getBookTitle(), request.getRequestDate(), request.getStatus());
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void displayBorrowRecords(ArrayList<BorrowRecord> borrowRecords) {
        System.out.println("==== Lịch sử mượn trả ====");
        if (borrowRecords.isEmpty()) {
            System.out.println("Không có bản ghi mượn trả nào.");
            return;
        }
        System.out.printf("%-10s | %-12s | %-15s | %-30s | %-10s | %-20s | %-15s | %-15s | %-15s | %-15s%n",
                "ID Phiếu", "ID Người Dùng", "Tên Người Dùng", "Tên Sách", "ID Sách", "Ngày mượn", "Ngày trả", "Ngày đến hạn", "Trạng thái", "Tình trạng sách");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        for (BorrowRecord record : borrowRecords) {
            System.out.printf("%-10d | %-12d | %-15s | %-30s | %-10d | %-20s | %-15s | %-15s | %-15s | %-15s%n",
                    record.getRecord_id(), record.getUserId(), record.getUserName(), record.getBookTitle(), record.getBookId(), record.getBorrowDate(),
                    record.getReturnDate() != null ? record.getReturnDate() : "Chưa trả", record.getDueDate(), record.getStatus(), record.getBook_condition());
        }
    }

    public void displayAllBorrowRequests(ArrayList<BorrowRequest> borrowRequests) {
        System.out.println("Danh sách tất cả các yêu cầu mượn sách:");
        if (borrowRequests.isEmpty()) {
            System.out.println("Không có yêu cầu mượn nào.");
            return; // Thoát nếu danh sách rỗng
        }
        System.out.printf("%-10s%-12s%-20s%-10s%-30s%-20s%-20s%n", "ID Yêu cầu", "ID Tài khoản", "Tên Người Dùng", "ID Sách", "Tên Sách", "Ngày yêu cầu mượn", "Trạng thái");
        for (BorrowRequest request : borrowRequests) {
            System.out.printf("%-10d%-12d%-20s%-10d%-30s%-20s%-20s%n",
                    request.getRequestId(), request.getUserId(), request.getUserName(), request.getBookId(), request.getBookTitle(), request.getRequestDate(), request.getStatus());
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void displayAllBorrowRecords(ArrayList<BorrowRecord> borrowRecords) {
        System.out.println("==== Tất cả các phiếu mượn sách ====");
        if (borrowRecords.isEmpty()) {
            System.out.println("Không có phiếu mượn nào.");
            return;
        }
        System.out.printf("%-10s | %-12s | %-15s | %-30s | %-10s | %-20s | %-15s | %-15s | %-15s | %-15s%n",
                "ID Phiếu", "ID Người Dùng", "Tên Người Dùng", "Tên Sách", "ID Sách", "Ngày mượn", "Ngày trả", "Ngày đến hạn", "Trạng thái", "Tình trạng sách");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        for (BorrowRecord record : borrowRecords) {
            System.out.printf("%-10d | %-12d | %-15s | %-30s | %-10d | %-20s | %-15s | %-15s | %-15s | %-15s%n",
                    record.getRecord_id(), record.getUserId(), record.getUserName(), record.getBookTitle(), record.getBookId(), record.getBorrowDate(),
                    record.getReturnDate() != null ? record.getReturnDate() : "Chưa trả", record.getDueDate(), record.getStatus(), record.getBook_condition());
        }
    }
}
