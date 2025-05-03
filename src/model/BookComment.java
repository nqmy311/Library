package model;

public class BookComment {
    private int commentId;
    private int userId;
    private int bookId;
    private String comment;
    private String userName;
    private String bookTitle;

    public BookComment() {

    }

    public BookComment(int userId, int bookId, String comment)
    {
        this.userId = userId;
        this.bookId = bookId;
        this.comment = comment;
    }

    public int getCommentId()
    {
        return commentId;
    }

    public void setCommentId(int commentId)
    {
        this.commentId = commentId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getBookTitle()
    {
        return bookTitle;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setBookTitle(String bookTitle)
    {
        this.bookTitle = bookTitle;
    }

    public String toString()
    {
        return  "Comment [ " +
                "commentId = " + commentId +
                ", userId = " + userId +
                ", bookId = " + bookId +
                ", comment = '" + comment + "'" +
                " ]";
    }
}
