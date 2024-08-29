package com.ohgiraffers.book.dto;

import java.util.Date;

public class BookDTO {
    private int bookCode;              // 도서 아이디
    private String bookTitle;          // 도서 제목
    private String bookAuthor;         // 도서 저자
    private String bookPublisher;      // 도서 출판사
    private String bookGenre;          // 도서 장르
    private String bookStatus;         // 도서 상태 (AVAILABLE, RESERVED, BORROWED)
    private Date bookCreatedAt;       // 도서 등록일
    private Date bookUpdatedAt;       // 도서 수정일

    // 기본 생성자
    public BookDTO() {}

    public BookDTO(int bookCode, String bookTitle, String bookAuthor, String bookPublisher,
                   String bookGenre, String bookStatus, Date bookCreatedAt, Date bookUpdatedAt) {
        this.bookCode = bookCode;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookGenre = bookGenre;
        this.bookStatus = bookStatus;
        this.bookCreatedAt = bookCreatedAt;
        this.bookUpdatedAt = bookUpdatedAt;
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

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Date getBookCreatedAt() {
        return bookCreatedAt;
    }

    public void setBookCreatedAt(Date bookCreatedAt) {
        this.bookCreatedAt = bookCreatedAt;
    }

    public Date getBookUpdatedAt() {
        return bookUpdatedAt;
    }

    public void setBookUpdatedAt(Date bookUpdatedAt) {
        this.bookUpdatedAt = bookUpdatedAt;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "bookCode=" + bookCode +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", bookGenre='" + bookGenre + '\'' +
                ", bookStatus='" + bookStatus + '\'' +
                ", bookCreatedAt=" + bookCreatedAt +
                ", bookUpdatedAt=" + bookUpdatedAt +
                '}';
    }
}