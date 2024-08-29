package com.ohgiraffers.book.dto;

import java.util.Date;

public class CategoriesDTO {

    private int categoryId;
    private String categoryName;
    private Date createdAt;

    public CategoriesDTO() {}

    public CategoriesDTO(int categoryId, String categoryName, Date createdAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
