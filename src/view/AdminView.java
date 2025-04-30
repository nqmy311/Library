package view;

import java.util.*;

import Controller.BorrowController;
import Controller.ViolationController;
import model.User;
import util.DBConnection;

public class AdminView {
    private final BookView bookView = new BookView();
    private final AccountView accountView = new AccountView();
    private final Scanner scanner = new Scanner(System.in);
    private final BorrowController borrowcontroller = new BorrowController();
    private final BorrowView borrowView = new BorrowView(borrowcontroller);
    private final ViolationController violationcontroller = new ViolationController();
    private final ViolationView violationView = new ViolationView(violationcontroller);
    private final java.sql.Connection dbConnection = DBConnection.getConnection();
    private final RevenueReportView revenueReportView = new RevenueReportView(new Controller.RevenueReportController(dbConnection, new RevenueReportView(null)));
    private final StatisticsView statisticsView = new StatisticsView(new Controller.StatisticController(dbConnection, new StatisticsView(null)));

    public void showMenu(User user) {
        while (true) {
            System.out.println("==== QUẢN LÝ THƯ VIỆN ====");
            System.out.println("1. Quản lý sách");
            System.out.println("2. Quản lý tài khoản");
            System.out.println("3. Quản lý mượn");
            System.out.println("4. Quản lý trả");
            System.out.println("5. Xem thông tin tất cả phiếu mượn / trả");
            System.out.println("6. Quản lý vi phạm");
            System.out.println("7. Báo cáo doanh thu");
            System.out.println("8. Thống kê");
            System.out.println("0. Đăng xuất");
            System.out.println("==========================");
            System.out.print("Chọn chức năng: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        bookView.showBookMenu();
                        break;
                    case 2:
                        accountView.showAccountMenu();
                        break;
                    case 3:
                        borrowView.showBorrowingManagementMenu();
                        break;
                    case 4:
                        borrowView.showReturningManagementMenu();
                        break;
                    case 5:
                        borrowcontroller.viewAllBorrowRecords();
                        break;
                    case 6:
                        violationView.showViolationMenu();
                        break;
                    case 7:
                        revenueReportView.showMenu();
                        break;
                    case 8:
                        statisticsView.showStatisticsMenu();
                        break;
                    case 0:
                        System.out.println("Đăng xuất ...");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                }
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }
}
