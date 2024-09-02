package com.ohgiraffers.request.dto;

import java.util.Date;

public class RequestDTO {

    private int requestId;
    private String userId;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private boolean requestStatus;
    private Date createdAt;
    private String bookGenre;
    private int bookQuantity;

    public RequestDTO() {
    }

    public RequestDTO(int requestId, String userId, String bookTitle, String bookAuthor,
                      String bookPublisher, boolean requestStatus, Date createdAt, String bookGenre, int bookQuantity) {
        this.requestId = requestId;
        this.userId = userId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.requestStatus = requestStatus;
        this.createdAt = createdAt;
        this.bookGenre = bookGenre;
        this.bookQuantity = bookQuantity;
    }


    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(boolean requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "requestId=" + requestId +
                ", userId='" + userId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", requestStatus=" + requestStatus +
                ", createdAt=" + createdAt +
                ", bookGenre='" + bookGenre + '\'' +
                '}';
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}
