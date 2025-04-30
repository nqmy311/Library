package view;

import Controller.ViolationController;
import model.Violation;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class ViolationView {
    private ViolationController controller;
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public ViolationView() {
    }

    public ViolationView(ViolationController controller) {
        this.controller = controller;
    }

    public void showViolationMenu(){
        while (true) {
            System.out.println("==== Quản lý mượn sách ====");
            System.out.println("1. Xem vi phạm người dùng");
            System.out.println("2. Xem tất cả vi phạm");
            System.out.println("3. Đánh dấu vi phạm đã thanh toán");
            System.out.println("0. Thoát");
            System.out.println("===========================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        int userId = checkIntInput("Nhập vào ID người dùng: ");
                        controller.viewViolationsByUserId(userId);
                        break;
                    case 2:
                        controller.viewAllViolations();
                        break;
                    case 3:
                        int violationId = checkIntInput("Nhập vào ID vi phạm: ");
                        controller.markViolationAsPaid(violationId);
                        break;
                    case 0:
                        return;
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

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayViolations(List<Violation> violations) {
        if (violations.isEmpty()) {
            System.out.println("Không có vi phạm nào.");
            return;
        }
        System.out.println("--- Danh sách vi phạm của người dùng ---");
        System.out.printf("%-10s %-20s %-10s %-10s %-15s %-20s %-15s %-20s%n", "ID vi phạm", "Tên Người Dùng", "ID Người Dùng", "ID Phiếu", "Ngày vi phạm", "Lý do", "Tiền phạt", "Tình Trạng Thanh Toán");
        for (Violation violation : violations) {
            System.out.printf("%-10d %-20s %-10d %-10d %-15s %-20s %-15.2f %-20s%n",
                    violation.getViolationId(), violation.getUserName(), violation.getUserId(), violation.getRecordId(), violation.getViolationDate(),
                    violation.getReason(), violation.getFineAmount(), violation.isPaid() ? "Đã thanh toán" : "Chưa thanh toán");
        }
        System.out.println("------------------------");
    }

    public void displayAllViolationsAdmin(List<Violation> violations) {
        if (violations.isEmpty()) {
            System.out.println("Không có vi phạm nào.");
            return;
        }
        System.out.println("--- Danh sách vi phạm ---");
        System.out.printf("%-10s %-20s %-10s %-10s %-15s %-20s %-15s %-20s%n", "ID vi phạm", "Tên Người Dùng", "ID Người Dùng", "ID Phiếu", "Ngày vi phạm", "Lý do", "Tiền phạt", "Tình Trạng Thanh Toán");
        for (Violation violation : violations) {
            System.out.printf("%-10d %-20s %-10d %-10d %-15s %-20s %-15.2f %-20s%n",
                    violation.getViolationId(), violation.getUserName(), violation.getUserId(), violation.getRecordId(), violation.getViolationDate(),
                    violation.getReason(), violation.getFineAmount(), violation.isPaid() ? "Đã thanh toán" : "Chưa thanh toán");
        }
        System.out.println("------------------------");
    }
}