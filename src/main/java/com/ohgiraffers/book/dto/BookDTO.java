package com.ohgiraffers.book.dto;

import com.ohgiraffers.user.dto.UserDTO;

import java.security.Timestamp;
import java.util.Date;

public class BookDTO {
    private int bookCode;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private String bookGenre;
    private boolean bookStatus; // true: 대여 중, false: 대여 가능
    private int borrowCount;

    public BookDTO() {}

    public BookDTO(int bookCode, String bookTitle, String bookAuthor, String bookPublisher, String bookGenre, boolean bookStatus,
                   int borrowCount) {
        this.bookCode = bookCode;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookGenre = bookGenre;
        this.bookStatus = bookStatus;
        this.borrowCount = borrowCount;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
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
                ", borrowCount=" + borrowCount +
                '}';
    }

    public void setUserInfo(UserDTO userInfo) {

    }
}