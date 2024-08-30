package com.ohgiraffers.book.dto;

import java.util.Date;

public class BookCategoryDTO {
    private int bookCode;
    private int categoryId;

    public BookCategoryDTO() {}

    public BookCategoryDTO(int bookCode, int categoryId) {
        this.bookCode = bookCode;
        this.categoryId = categoryId;
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

    @Override
    public String toString() {
        return "BookCategoryDTO{" +
                "bookCode=" + bookCode +
                ", categoryId=" + categoryId +
                '}';
    }
}
