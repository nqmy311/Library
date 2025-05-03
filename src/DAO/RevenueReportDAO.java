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

    /* Thêm báo cáo doanh thu */
    public boolean addReport(RevenueReport report) {
        String selectSQL = "SELECT report_id, total_fines_collected FROM revenue_reports WHERE report_date = ?";
        String updateSQL = "UPDATE revenue_reports SET total_fines_collected = ?, notes = ? WHERE report_date = ?";
        String insertSQL = "INSERT INTO revenue_reports (report_date, total_fines_collected, notes) VALUES (?, ?, ?)";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
             PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
             PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {

            selectStmt.setDate(1, java.sql.Date.valueOf(report.getReportDate()));
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Nếu báo cáo cho ngày này đã tồn tại, cập nhật tổng tiền phạt và ghi chú
                java.math.BigDecimal existingFines = rs.getBigDecimal("total_fines_collected");
                java.math.BigDecimal newFines = report.getTotalFinesCollected();
                //Cộng dồn tiền phạt hoặc thay thế hoàn toàn tùy theo yêu cầu
                java.math.BigDecimal totalFines = newFines; // Để thay thế hoàn toàn
                //BigDecimal totalFines = existingFines.add(newFines); // Để cộng dồn

                updateStmt.setBigDecimal(1, totalFines);
                updateStmt.setString(2, report.getNotes());
                updateStmt.setDate(3, java.sql.Date.valueOf(report.getReportDate()));
                return updateStmt.executeUpdate() > 0;
            } else {
                // Nếu không tồn tại, thêm mới báo cáo
                insertStmt.setDate(1, java.sql.Date.valueOf(report.getReportDate()));
                insertStmt.setBigDecimal(2, report.getTotalFinesCollected());
                insertStmt.setString(3, report.getNotes());
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            return false;
        }
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