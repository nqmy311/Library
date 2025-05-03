package view;

import Controller.ViolationController;
import model.TablePrinter;
import model.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViolationView {
    private final ViolationController controller;
    private final Scanner scanner = new Scanner(System.in);

    public ViolationView(ViolationController controller) {
        this.controller = controller;
    }

    public void showViolationMenu() {
        while (true) {
            System.out.println("======== QUẢN LÝ VI PHẠM ========");
            System.out.println("1. Xem vi phạm người dùng");
            System.out.println("2. Xem danh sách tất cả vi phạm");
            System.out.println("3. Đánh dấu vi phạm đã thanh toán");
            System.out.println("0. Thoát");
            System.out.println("=================================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        int userId = checkIntInput("Nhập vào ID người dùng (Muốn thoát vui lòng nhập số 0): ");
                        if(userId == 0){
                            System.out.println("Huỷ thao tác xem vi phạm của người dùng");
                            break;
                        }
                        controller.viewViolationsByUserId(userId);
                        break;
                    case 2:
                        controller.viewAllViolations();
                        break;
                    case 3:
                        int violationId = checkIntInput("Nhập vào ID vi phạm muốn đánh dấu (Muốn thoát vui lòng nhập số 0): ");
                        if(violationId == 0){
                            System.out.println("Huỷ thao tác đánh dấu đã thanh toán");
                            break;
                        }
                        controller.markViolationAsPaid(violationId);
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

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayViolations(List<Violation> violations) {
        System.out.printf("%75s\n", "DANH SÁCH VI PHẠM CỦA NGƯỜI DÙNG");
        if (violations.isEmpty()) {
            System.out.printf("%80s\n", "Không có vi phạm nào!");
            return;
        }
        String[] headers = {"ID vi phạm", "ID Người Dùng", "Tên Người Dùng", "ID Phiếu Mượn",
                "Ngày vi phạm", "Lý do", "Tiền phạt", "Tình Trạng Thanh Toán"
        };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Violation violation : violations) {
            rows.add(new Object[]{violation.getViolationId(), violation.getUserId(), violation.getUserName(),
                    violation.getRecordId(), violation.getViolationDate(), violation.getReason(),
                    String.format("%.2f", violation.getFineAmount()), violation.isPaid() ? "Đã thanh toán" : "Chưa thanh toán"
            });
        }
        TablePrinter.printTable(headers, rows);
    }

    public void displayAllViolationsAdmin(List<Violation> violations) {
        System.out.printf("%75s\n", "DANH SÁCH TẤT CẢ VI PHẠM");
        if (violations.isEmpty()) {
            System.out.printf("%75s\n", "Không có vi phạm nào!");
            return;
        }
        String[] headers = {"ID vi phạm", "ID Người Dùng", "Tên Người Dùng", "ID Phiếu",
                "Ngày vi phạm", "Lý do", "Tiền phạt", "Tình Trạng Thanh Toán"
        };
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Violation violation : violations) {
            rows.add(new Object[]{violation.getViolationId(), violation.getUserId(), violation.getUserName(),
                    violation.getRecordId(), violation.getViolationDate(), violation.getReason(),
                    String.format("%.2f", violation.getFineAmount()), violation.isPaid() ? "Đã thanh toán" : "Chưa thanh toán"
            });
        }
        TablePrinter.printTable(headers, rows);
    }
}