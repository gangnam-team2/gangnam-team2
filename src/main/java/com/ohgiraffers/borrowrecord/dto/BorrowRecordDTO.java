package com.ohgiraffers.borrowrecord.dto;
import java.util.Date;

public class BorrowRecordDTO {

    private int userId;                // 대여자 아이디
    private int bookCode;              // 대여한 도서 코드
    private Date borrowDate;           // 대여일
    private Date dueDate;              // 반납 예정일
    private Date returnDate;           // 실제 반납일
    private Date brCreatedAt;         // 도서대여 등록일
    private Date brUpdatedAt;         // 도서대여 수정일

    public BorrowRecordDTO() {}

    public BorrowRecordDTO(int borrowCode, int userId, int bookCode,
                           Date borrowDate, Date dueDate, Date returnDate, Date brCreatedAt, Date brUpdatedAt) {

        this.userId = userId;
        this.bookCode = bookCode;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.brCreatedAt = brCreatedAt;
        this.brUpdatedAt = brUpdatedAt;
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

    public Date getBrCreatedAt() {
        return brCreatedAt;
    }

    public void setBrCreatedAt(Date brCreatedAt) {
        this.brCreatedAt = brCreatedAt;
    }

    public Date getBrUpdatedAt() {
        return brUpdatedAt;
    }

    public void setBrUpdatedAt(Date brUpdatedAt) {
        this.brUpdatedAt = brUpdatedAt;
    }

    @Override
    public String toString() {
        return "BorrowRecordDTO{" +
                ", userId=" + userId +
                ", bookCode=" + bookCode +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", brCreatedAt=" + brCreatedAt +
                ", brUpdatedAt=" + brUpdatedAt +
                '}';
    }
}
