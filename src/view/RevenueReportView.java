package view;

import Controller.RevenueReportController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.RevenueReport;
import model.TablePrinter;

public class RevenueReportView {
    private final RevenueReportController revenueReportController;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public RevenueReportView(RevenueReportController controller) {
        this.revenueReportController = controller;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("==== QUẢN LÝ BÁO CÁO DOANH THU ====");
            System.out.println("1. Thêm báo cáo doanh thu");
            System.out.println("2. Xem báo cáo doanh thu theo khoảng thời gian");
            System.out.println("0. Quay lại");
            System.out.println("===================================");
            System.out.print("Chọn chức năng: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d+")) {
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        addReport();
                        break;
                    case 2:
                        getReportByDateRange();
                        break;
                    case 0:
                        System.out.println("Quay lại...");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
                }
            } else {
                System.out.println("Vui lòng nhập số!");
                choice = -1;
            }
        } while (choice != 0);
    }

    private void addReport() {
        System.out.println("==== THÊM BÁO CÁO DOANH THU ====");
        LocalDate reportDate = getDateInput("Nhập ngày báo cáo (yyyy-MM-dd): ");
        if (reportDate == null) return;

        System.out.print("Nhập ghi chú (tùy chọn): ");
        String notes = scanner.nextLine().trim();

        revenueReportController.generateRevenueReport(reportDate, notes);
    }

    private void getReportByDateRange() {
        System.out.println("==== XEM BÁO CÁO DOANH THU THEO KHOẢNG THỜI GIAN ====");
        LocalDate startDate = getDateInput("Nhập ngày bắt đầu (yyyy-MM-dd): ");
        if (startDate == null) return;

        LocalDate endDate = getDateInput("Nhập ngày kết thúc (yyyy-MM-dd): ");
        if (endDate == null) return;

        if (startDate.isAfter(endDate)) {
            System.out.println("Ngày bắt đầu phải trước ngày kết thúc!");
            return;
        }

        revenueReportController.viewRevenueReports(startDate, endDate);
    }

    private LocalDate getDateInput(String prompt) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine().trim();
            if (dateStr.equals("0"))
            {
                System.out.println("Quay lại...");
                return null;
            }
            try {
                date = LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng sử dụng yyyy-MM-dd.");
            }
        }
        return date;
    }


    public void displayReportGeneratedSuccessfully(RevenueReport report) {
        System.out.println("Báo cáo doanh thu đã được tạo thành công:");
        System.out.println(report);
    }

    public void displayReportGeneratedFailed() {
        System.out.println("Không thể tạo báo cáo doanh thu. Vui lòng thử lại!");
    }

    public void displayRevenueReports(List<RevenueReport> reports) {
        if (reports.isEmpty()) {
            System.out.println("Không có báo cáo nào trong khoảng thời gian này!");
        } else {
            String[] headers = { "ID báo cáo", "Ngày báo cáo", "Tổng tiền phạt", "Ghi chú" };
            ArrayList<Object[]> rows = new ArrayList<>();
            for (RevenueReport report : reports) {
                rows.add(new Object[] {
                        report.getReportId(),
                        report.getReportDate(),
                        report.getTotalFinesCollected(),
                        report.getNotes()
                });
            }
            TablePrinter.printTable(headers, rows);
        }
    }

    public void displayNoReportsFound() {
        System.out.println("Không tìm thấy báo cáo doanh thu nào trong khoảng thời gian này!");
    }
}