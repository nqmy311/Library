package DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import model.RevenueReport;


public class RevenueReportDAO {
    private final Connection connection; // Thêm biến Connection

    public RevenueReportDAO(Connection connection) {
        this.connection = connection;
    }

    /*Thêm báo cáo doanh thu */
    public boolean addReport(RevenueReport report) {
        String sql = "INSERT INTO revenue_reports (report_date, total_fines_collected, notes) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, report.getReportDate());
            preparedStatement.setBigDecimal(2, report.getTotalFinesCollected());
            preparedStatement.setString(3, report.getNotes());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return false;
    }

    /*lấy report theo khoảng ngày*/
    public List<RevenueReport> getReportByDateRange(LocalDate startDate, LocalDate endDate) {
        List<RevenueReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM revenue_reports WHERE report_date BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, startDate);
            preparedStatement.setObject(2, endDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RevenueReport report = new RevenueReport();
                report.setReportId(resultSet.getInt("report_id"));
                report.setReportDate(resultSet.getObject("report_date", LocalDate.class));
                report.setTotalFinesCollected(resultSet.getBigDecimal("total_fines_collected"));
                report.setNotes(resultSet.getString("notes"));
                reports.add(report);     //thêm báo cáo vào danh sách reports
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        return reports;
    }
}