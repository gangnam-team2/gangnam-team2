package com.ohgiraffers.book.dto;

import java.util.Date;

public class RequestDTO {

    private int requestId;
    private int userId;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private String requestStatus;
    private Date createdAt;

    public RequestDTO() {}

    public RequestDTO(int requestId, int userId, String bookTitle, String bookAuthor,
                      String bookPublisher, String requestStatus, Date createdAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.requestStatus = requestStatus;
        this.createdAt = createdAt;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
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
                ", userId=" + userId +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", requestStatus='" + requestStatus + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
