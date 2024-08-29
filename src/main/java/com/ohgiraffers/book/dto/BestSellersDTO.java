package com.ohgiraffers.book.dto;

import java.util.Date;

public class BestSellersDTO {

    private int bestSellerId;
    private int bookCood;
    private int borrowCount;
    private String period;
    private Date createdAt;

    public BestSellersDTO() {}

    public BestSellersDTO(int bestSellerId, int bookCood, int borrowCount, String period, Date createdAt) {
        this.bestSellerId = bestSellerId;
        this.bookCood = bookCood;
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

    public int getBookCood() {
        return bookCood;
    }

    public void setBookCood(int bookCood) {
        this.bookCood = bookCood;
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
                ", bookCood=" + bookCood +
                ", borrowCount=" + borrowCount +
                ", period='" + period + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
