package com.ohgiraffers.book.dto;

import java.util.Date;

public class BookCategoryDTO {

    private int bookCode;
    private int categoryId;
    private Date createdAt;

    public BookCategoryDTO() {}

    public BookCategoryDTO(int bookCode, int categoryId, Date createdAt) {
        this.bookCode = bookCode;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
    }

    public int getBookCode() {
        return bookCode;
    }

    public void setBookCode(int bookCode) {
        this.bookCode = bookCode;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "BookCategoryDTO{" +
                "bookCode=" + bookCode +
                ", categoryId=" + categoryId +
                ", createdAt=" + createdAt +
                '}';
    }
}
