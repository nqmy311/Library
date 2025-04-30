package model;

import java.util.Date;

public class BorrowRecord {
    private int record_id;
    private int user_id;
    private int book_id;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private String status;
    private String book_condition;
    private String bookTitle;
    private String userName;

    public BorrowRecord(){
    }

    public BorrowRecord(int userId, int bookId, Date borrowDate, Date dueDate) {
        this.user_id = userId;
        this.book_id = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = "Đang được mượn";
        this.book_condition = "Bình thường";
    }

    public int getRecord_id() {
        return this.record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
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

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Date getBorrowDate() {
        return this.borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBook_condition() {
        return this.book_condition;
    }

    public void setBook_condition(String book_condition) {
        this.book_condition = book_condition;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}