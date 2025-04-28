package model;

import java.util.Date;

public class BorrowRequest {
    private int request_id;
    private int user_id;
    private int book_id;
    private Date requestDate;
    private String status;

    public BorrowRequest(){

    }

    public BorrowRequest(int user_id, int book_id, Date requestDate){
        this.user_id = user_id;
        this.book_id = book_id;
        this.requestDate = requestDate;
        this.status = "Đang chờ duyệt";
    }

    public int getRequestId() {
        return this.request_id;
    }

    public void setRequestId(int request_id) {
        this.request_id = request_id;
    }

    public int getUserId() {
        return this.user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getBookId() {
        return this.book_id;
    }

    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}