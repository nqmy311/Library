package Controller;

import DAO.RevenueReportDAO;
import DAO.ViolationDAO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import model.RevenueReport;
import view.RevenueReportView;

public class RevenueReportController {
    private final RevenueReportDAO revenueReportDAO;
    private final RevenueReportView revenueReportView;
    private final ViolationDAO violationDAO;

    public RevenueReportController(java.sql.Connection connection, RevenueReportView view) {
        this.revenueReportDAO = new RevenueReportDAO(connection);
        this.violationDAO = new ViolationDAO();
        this.revenueReportView = view;
    }

    public void generateRevenueReport(LocalDate reportDate, String notes) {
        BigDecimal totalFines = violationDAO.getTotalPaidFinesByDate(reportDate);
        RevenueReport report = new RevenueReport(reportDate, totalFines, notes);
        if (revenueReportDAO.addReport(report)) {
            revenueReportView.displayReportGeneratedSuccessfully(report);
        } else {
            revenueReportView.displayReportGeneratedFailed();
        }
    }

    public void viewRevenueReports(LocalDate startDate, LocalDate endDate) {
        List<RevenueReport> reports = revenueReportDAO.getReportByDateRange(startDate, endDate);
        if (reports.isEmpty()) {
            revenueReportView.displayNoReportsFound();
        } else {
            revenueReportView.displayRevenueReports(reports);
        }
    }
}
