package model;

public class Book {
    private int book_id;
    private String title;
    private String author;
    private String publisher;
    private int year_published;
    private String category;
    private String location;
    private String language;
    private int quantity;
    private int available;
    private int penalty_rate;

    public Book(String title, String author, String publisher, int year_published, String category, String location, String language, int quantity, int available, int penalty_rate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year_published = year_published;
        this.category = category;
        this.location = location;
        this.language = language;
        this.quantity = quantity;
        this.available = available;
        this.penalty_rate = penalty_rate;
    }

    public Book() {

    }

    public int getBook_Id() {
        return this.book_id;
    }

    public void setBook_Id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear_published() {
        return this.year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailable() {
        return this.available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getPenalty_rate() {
        return this.penalty_rate;
    }

    public void setPenalty_rate(int penalty_rate) {
        this.penalty_rate = penalty_rate;
    }
}
