package com.ohgiraffers.book.dto;

import java.util.Date;

public class BestSellersDTO {

    private int bestSellerId;
    private int bookCode;
    private int borrowCount;
    private String period;
    private Date createdAt;

    public BestSellersDTO() {}

    public int bestSellerId() {
        return bestSellerId;
    }

    public BestSellersDTO(int bestSellerId, int bookCode, int borrowCount, String period, Date createdAt) {
        this.bestSellerId = bestSellerId;
        this.bookCode = bookCode;
        this.borrowCount = borrowCount;
        this.period = period;
        this.createdAt = createdAt;
    }

    public int getBestSellerId() {
        return bestSellerId;
    }

    public void setBestSellerId(int bestSellerId) {
        this.bestSellerId = bestSellerId;
    }

    public int getBookCode() {
        return bookCode;
    }

    public void setBookCode(int bookCode) {
        this.bookCode = bookCode;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "BestSellersDTO{" +
                "bestSellerId=" + bestSellerId +
                ", bookCode=" + bookCode +
                ", borrowCount=" + borrowCount +
                ", period='" + period + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
