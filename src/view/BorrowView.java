package view;

import Controller.BorrowController;
import model.BorrowRecord;
import model.BorrowRequest;
import java.util.ArrayList;
import java.util.Scanner;


public class BorrowView {
    private final BorrowController controller = new BorrowController();
    private final Scanner scanner = new Scanner(System.in);

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
                        controller.BorrowRequests();
                        break;
                    case 2:
                        int requestIdApprove = getRequestIdInput();
                        controller.approveBorrowRequest(requestIdApprove);
                        break;
                    case 3:
                        int requestIdReject = getRequestIdInput();
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


}
