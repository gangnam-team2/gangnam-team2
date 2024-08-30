package com.ohgiraffers.book.dto;
import java.util.Date;

public class BorrowRecordDTO {

    private int userId;                // 대여자 아이디
    private int bookCode;              // 대여한 도서 코드
    private Date borrowDate;           // 대여일
    private Date dueDate;              // 반납 예정일
    private Date returnDate;           // 실제 반납일
    private boolean bookStatus;

    public BorrowRecordDTO(int bookCode, String bookTitle, java.sql.Date borrowDate, java.sql.Date dueDate, java.sql.Date returnDate) {}

    public BorrowRecordDTO() {
        this.userId = userId;
        this.bookCode = bookCode;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.bookStatus = bookStatus;
    }

    public int getUserId() {
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

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(boolean bookStatus) {
        this.bookStatus = bookStatus;
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
                '}';
    }
}
