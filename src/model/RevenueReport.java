package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueReport {
    private int reportId;
    private LocalDate reportDate;
    private BigDecimal totalFinesCollected; //tổng tiền phạt đã thu
    private String notes;

    public RevenueReport() {
    }

    public RevenueReport(LocalDate reportDate, BigDecimal totalFinesCollected, String notes)
    {
        this.reportDate = reportDate;
        this.totalFinesCollected = totalFinesCollected;
        this.notes = notes;
    }

    public int getReportId()
    {
        return reportId;
    }

    public void setReportId(int reportId)
    {
        this.reportId = reportId;
    }

    public LocalDate getReportDate()
    {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate)
    {
        this.reportDate = reportDate;
    }

    public BigDecimal getTotalFinesCollected()
    {
        return totalFinesCollected;
    }

    public void setTotalFinesCollected(BigDecimal totalFinesCollected)
    {
        this.totalFinesCollected = totalFinesCollected;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    @Override
    public String toString()
    {
        return  "RevenueReport [ " +
                "reportId = " + reportId +
                ", reportDate = " + reportDate +
                ", notes = '" + notes + "'" +
                " ]";
    }
}
