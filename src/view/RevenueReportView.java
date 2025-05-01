package view;

import Controller.RevenueReportController;
import model.RevenueReport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

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
        System.out.print("Nhập ngày báo cáo (yyyy-MM-dd): ");
        String reportDateStr = scanner.nextLine().trim();
        LocalDate reportDate = null;
        try {
            reportDate = LocalDate.parse(reportDateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày không hợp lệ. Vui lòng sử dụng yyyy-MM-dd.");
            return;
        }

        System.out.print("Nhập ghi chú (tùy chọn): ");
        String notes = scanner.nextLine().trim();

        revenueReportController.generateRevenueReport(reportDate, notes);
    }

    private void getReportByDateRange() {
        System.out.println("==== XEM BÁO CÁO DOANH THU THEO KHOẢNG THỜI GIAN ====");
        System.out.print("Nhập ngày bắt đầu (yyyy-MM-dd): ");
        String startDateStr = scanner.nextLine().trim();
        LocalDate startDate = null;
        try {
            startDate = LocalDate.parse(startDateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày bắt đầu không hợp lệ. Vui lòng sử dụng yyyy-MM-dd.");
            return;
        }

        System.out.print("Nhập ngày kết thúc (yyyy-MM-dd): ");
        String endDateStr = scanner.nextLine().trim();
        LocalDate endDate = null;
        try {
            endDate = LocalDate.parse(endDateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày kết thúc không hợp lệ. Vui lòng sử dụng yyyy-MM-dd.");
            return;
        }

        revenueReportController.viewRevenueReports(startDate, endDate);
    }

    public void displayReportGeneratedSuccessfully(RevenueReport report) {
        System.out.println("Báo cáo doanh thu đã được tạo thành công:");
        System.out.println(report);
    }

    public void displayReportGeneratedFailed() {
        System.out.println("Không thể tạo báo cáo doanh thu. Vui lòng thử lại!");
    }

    public void displayRevenueReports(List<RevenueReport> reports) {
        System.out.println("--- Danh sách báo cáo doanh thu ---");
        if (reports.isEmpty()) {
            System.out.println("Không có báo cáo nào trong khoảng thời gian này!");
        } else {
            for (RevenueReport report : reports) {
                System.out.println("ID: " + report.getReportId());
                System.out.println("Ngày báo cáo: " + report.getReportDate());
                System.out.println("Tổng tiền phạt thu được: " + report.getTotalFinesCollected());
                if (report.getNotes() != null && !report.getNotes().isEmpty()) {
                    System.out.println("Ghi chú: " + report.getNotes());
                }
                System.out.println("-----------------------------------");
            }
        }
    }

    public void displayNoReportsFound() {
        System.out.println("Không tìm thấy báo cáo doanh thu nào trong khoảng thời gian này!");
    }
}