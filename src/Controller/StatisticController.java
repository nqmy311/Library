package Controller;

import DAO.BorrowRecordDAO;
import java.sql.Connection;
import java.util.*;
import model.BorrowRecord;
import view.StatisticsView;

public class StatisticController {
    private final BorrowRecordDAO borrowRecordDAO;
    private final StatisticsView statisticsView;

    public StatisticController (Connection connection, StatisticsView statisticsView)
    {
        this.borrowRecordDAO = new BorrowRecordDAO();
        this.statisticsView = statisticsView;
    }

    public void displayMostBorrowedBooks() {
        List<BorrowRecord> allRecords = borrowRecordDAO.getAllBorrowRecords();
        Map<Integer, Integer> bookBorrowCounts = new HashMap<>();
        Map<Integer, String> bookTitles = new HashMap<>();

        for (BorrowRecord record : allRecords) {
            int bookId = record.getBookId();
            String bookTitle = record.getBookTitle();
            if (bookTitle != null) {
                bookBorrowCounts.put(bookId, bookBorrowCounts.getOrDefault(bookId, 0) + 1);
                bookTitles.put(bookId, bookTitle);
            }
        }

        List<Map.Entry<Integer, Integer>> sortedBooks = new ArrayList<>(bookBorrowCounts.entrySet());
        sortedBooks.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        statisticsView.displayMostBorrowedBooks(sortedBooks, bookTitles);
    }

    public void displayMostBorrowingUsers() {
        List<BorrowRecord> allRecords = borrowRecordDAO.getAllBorrowRecords();
        Map<Integer, Integer> userBorrowCounts = new HashMap<>();
        Map<Integer, String> userNames = new HashMap<>();

        for (BorrowRecord record : allRecords) {
            int userId = record.getUserId();
            String userName = record.getUserName();
            if (userName != null) {
                userBorrowCounts.put(userId, userBorrowCounts.getOrDefault(userId, 0) + 1);
                userNames.put(userId, userName);
            }
        }

        List<Map.Entry<Integer, Integer>> sortedUsers = new ArrayList<>(userBorrowCounts.entrySet());
        sortedUsers.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        statisticsView.displayMostBorrowingUsers(sortedUsers, userNames);
    }
}