package com.ohgiraffers.borrowrecord.dto;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class BorrowRecordDTO {

    private String userId;                // 대여자 아이디
    private int bookCode;              // 대여한 도서 코드
    private Timestamp borrowDate;           // 대여일
    private Date dueDate;              // 반납 예정일
    private Timestamp returnDate;           // 실제 반납일
    private boolean bookStatus;
    private boolean overDueBooks;

    public BorrowRecordDTO() {}

    public BorrowRecordDTO(String userId, int bookCode, Timestamp borrowDate, Date dueDate, Timestamp returnDate, boolean bookStatus, boolean overDueBooks) {
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

    public Timestamp getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Timestamp borrowDate) {
        this.borrowDate = borrowDate;
    }

    public java.sql.Date getDueDate() {
        return (java.sql.Date) dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
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
                "userId=" + userId +
                ", bookCode=" + bookCode +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", bookStatus=" + bookStatus +
                ", overduebooks=" + overDueBooks +
                '}';
    }
}
