package model;

import java.util.Date;

public class Violation {
    private int violationId;
    private int userId;
    private int recordId;
    private Date violationDate;
    private String reason;
    private double fineAmount;
    private boolean isPaid;
    private String userName;

    public Violation() {
    }

    public Violation(int userId, int recordId, Date violationDate, String reason, double fineAmount) {
        this.userId = userId;
        this.recordId = recordId;
        this.violationDate = violationDate;
        this.reason = reason;
        this.fineAmount = fineAmount;
        this.isPaid = false;
    }

    public int getViolationId() {
        return violationId;
    }

    public void setViolationId(int violationId) {
        this.violationId = violationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public Date getViolationDate() {
        return violationDate;
    }

    public void setViolationDate(Date violationDate) {
        this.violationDate = violationDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
