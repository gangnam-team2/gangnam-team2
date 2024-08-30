package com.ohgiraffers.book.dto;

import java.security.Timestamp;
import java.util.Date;

public class BookDTO {
    private int bookCode;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private String bookGenre;
    private boolean bookStatus; // true: 대여 중, false: 대여 가능
    private int bookBrrowCount

    public BookDTO() {}

    public BookDTO(int bookCode, String bookTitle, String bookAuthor, String bookPublisher, String bookGenre, boolean bookStatus,
                   int bookBrrowCount) {
        this.bookCode = bookCode;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookGenre = bookGenre;
        this.bookStatus = bookStatus;
        this.bookBrrowCount = bookBrrowCount;
    }

    public int getBookBrrowCount() {
        return bookBrrowCount;
    }

    public void setBookBrrowCount(int bookBrrowCount) {
        this.bookBrrowCount = bookBrrowCount;
    }

    public int getBookCode() {
        return bookCode;
    }

    public void setBookCode(int bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public boolean isBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(boolean bookStatus) {
        this.bookStatus = bookStatus;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "bookCode=" + bookCode +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", bookGenre='" + bookGenre + '\'' +
                ", bookStatus=" + bookStatus +
                ", bookBrrowCount=" + bookBrrowCount +
                '}';
    }
}