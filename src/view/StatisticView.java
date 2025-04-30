package view;

import Controller.StatisticsController;
import java.util.*;

public class StatisticsView {
    private final Scanner scanner = new Scanner(System.in);
    private final StatisticsController statisticsController = new StatisticsController();

    public void showStatisticsMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("==== THỐNG KÊ ====");
            System.out.println("1. Thống kê sách được mượn nhiều nhất");
            System.out.println("2. Thống kê người mượn sách nhiều nhất");
            System.out.println("0. Quay lại menu quản lý");
            System.out.println("===================");
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

    public void displayMostBorrowedBooks(List<Map.Entry<Integer, Integer>> sortedBooks, Map<Integer, String> bookTitles)
    {
        System.out.println("--- Thống kê sách được mượn nhiều nhất ---");
        if (sortedBooks.isEmpty())
        {
            System.out.println("Không có thông tin mượn sách!");
        }
        else
        {
            int count = 1;
            for (Map.Entry<Integer, Integer> entry : sortedBooks)
            {
                int bookId = entry.getKey();
                int borrowCount = entry.getValue();
                String bookTitle = bookTitles.get(bookId);
                System.out.println(count + ". Sách: \"" + bookTitle + "\" (ID: " + bookId + ") - Số lần mượn: " + borrowCount);
                count++;
            }
        }
        System.out.println("-------------------------------------");
    }

    public void displayMostBorrowingUsers(List<Map.Entry<Integer, Integer>> sortedUsers, Map<Integer, String> userNames)
    {
        System.out.println("--- Thống kê người mượn sách nhiều nhất ---");
        if(sortedUsers.isEmpty())
        {
            System.out.println("Không có thông tin mượn sách");
        }
        else
        {
            int count = 1;
            for (Map.Entry<Integer, Integer> entry : sortedUsers)
            {
                int userId = entry.getKey();
                int borrowCount = entry.getValue();
                String userName = userNames.get(userId);
                System.out.println(count + ". Người dùng: " + userName + " (ID: " + userId + ") - Số sách đã mượn: " + borrowCount);
                count++;
            }
        }
        System.out.println("-------------------------------------");
    }
}
