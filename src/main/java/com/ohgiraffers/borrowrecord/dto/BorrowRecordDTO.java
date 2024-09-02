/*
package com.ohgiraffers.borrowrecord.dto;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

public class BorrowRecordDTO {

    private int userId;                // 대여자 아이디
    private int bookCode;              // 대여한 도서 코드
    private Date borrowDate;           // 대여일
    private Date dueDate;              // 반납 예정일
    private Date returnDate;           // 실제 반납일
    private boolean bookStatus;
    private boolean overDueBooks;

    public BorrowRecordDTO() {}

    public BorrowRecordDTO(int userId, int bookCode, Date borrowDate, Date dueDate, Date returnDate, boolean bookStatus, boolean overduebooks) {
        this.userId = userId;
        this.bookCode = bookCode;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.bookStatus = bookStatus;
        this.overDueBooks = overduebooks;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookCode() {
        return bookCode;
    }

    public void setBookCode(int bookCode) {
        this.bookCode = bookCode;
    }

    public ChronoLocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public java.sql.Date getReturnDate() {
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
*/
