package view;

import Controller.StatisticController;
import java.util.*;
import model.TablePrinter;

public class StatisticsView {
    private final Scanner scanner = new Scanner(System.in);
    private final StatisticController statisticsController;

    public StatisticsView (StatisticController statisticController)
    {
        this.statisticsController = statisticController;
    }

    public void showStatisticsMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("========== THỐNG KÊ ==========");
            System.out.println("1. Thống kê sách được mượn nhiều nhất");
            System.out.println("2. Thống kê người mượn sách nhiều nhất");
            System.out.println("0. Quay lại menu quản lý");
            System.out.println("==============================");
            System.out.print("Chọn chức năng: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        statisticsController.displayMostBorrowedBooks();
                        break;
                    case 2:
                        statisticsController.displayMostBorrowingUsers();
                        break;
                    case 0:
                        System.out.println("Quay lại menu quản lý ...");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Hãy chọn lại.");
                        break;
                }
            } else {
                System.out.println("Vui lòng nhập số!");
            }
        }
    }

    public void displayMostBorrowedBooks(List<Map.Entry<Integer, Integer>> sortedBooks, Map<Integer, String> bookTitles) {
        System.out.println("======== THỐNG KÊ SÁCH ĐƯỢC MƯỢN NHIỀU NHẤT ========");
        if (sortedBooks.isEmpty()) {
            System.out.println("Không có thông tin mượn sách!");
        } else {
            String[] headers = {"STT", "ID Sách", "Tên Sách", "Số lần mượn"};
            ArrayList<Object[]> rows = new ArrayList<>();
            int count = 1;
            for (Map.Entry<Integer, Integer> entry : sortedBooks) {
                int bookId = entry.getKey();
                int borrowCount = entry.getValue();
                String bookTitle = bookTitles.get(bookId);
                rows.add(new Object[]{count++, bookId, bookTitle, borrowCount});
            }
            TablePrinter.printTable(headers, rows);
        }
    }

    public void displayMostBorrowingUsers(List<Map.Entry<Integer, Integer>> sortedUsers, Map<Integer, String> userNames) {
        System.out.println("======== THỐNG KÊ NGƯỜI DÙNG MƯỢN SÁCH NHIỀU NHẤT ========");
        if (sortedUsers.isEmpty()) {
            System.out.println("Không có thông tin mượn sách");
        } else {
            String[] headers = {"STT", "ID Người dùng", "Tên người dùng", "Số sách đã mượn"};
            ArrayList<Object[]> rows = new ArrayList<>();
            int count = 1;
            for (Map.Entry<Integer, Integer> entry : sortedUsers) {
                int userId = entry.getKey();
                int borrowCount = entry.getValue();
                String userName = userNames.get(userId);
                rows.add(new Object[]{count++, userId, userName, borrowCount});
            }
            TablePrinter.printTable(headers, rows);
        }
    }
}
