package com.ohgiraffers.borrowrecord.dto;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

public class BorrowRecordDTO {

    private String userId;                // 대여자 아이디
    private int bookCode;              // 대여한 도서 코드
    private LocalDate borrowDate;           // 대여일
    private LocalDate dueDate;              // 반납 예정일
    private LocalDate returnDate;           // 실제 반납일
    private boolean bookStatus;
    private boolean overDueBooks;

    public BorrowRecordDTO() {
    }

    public BorrowRecordDTO(String userId, int bookCode, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, boolean bookStatus, boolean overDueBooks) {
        this.userId = userId;
        this.bookCode = bookCode;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.bookStatus = bookStatus;
        this.overDueBooks = overDueBooks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBookCode() {
        return bookCode;
    }

    public void setBookCode(int bookCode) {
        this.bookCode = bookCode;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(boolean bookStatus) {
        this.bookStatus = bookStatus;
    }

    public boolean isOverDueBooks() {
        return overDueBooks;
    }

    public void setOverDueBooks(boolean overDueBooks) {
        this.overDueBooks = overDueBooks;
    }

    @Override
    public String toString() {
        return "BorrowRecordDTO{" +
                "userId='" + userId + '\'' +
                ", bookCode=" + bookCode +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", bookStatus=" + bookStatus +
                ", overDueBooks=" + overDueBooks +
                '}';
    }
}
