package com.ohgiraffers.borrowrecord.controller;


import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.borrowrecord.dao.BorrowRecordDAO;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.mypage.dao.MypageDAO;
import com.ohgiraffers.user.dto.UserDTO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class BorrowRecordController {
    BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
    BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    UserDTO userDTO = new UserDTO();
    BookDTO bookDTO = new BookDTO();
    MypageDAO mypageDAO = new MypageDAO();


    public void rentBook(){
        try{
            borrowRecordDAO.showBookList(getConnection(),borrowRecordDTO);

            Scanner sc = new Scanner(System.in);
            System.out.println("대여하고 싶은 책의 코드를 입력해주세요.");
            int bookCode = sc.nextInt();
            borrowRecordDTO.setBookCode(bookCode);
            Date borrowDate = Date.valueOf(LocalDate.now());
            borrowRecordDTO.setBorrowDate(borrowDate);

            System.out.println(userDTO.getUserId() + " id ");
            System.out.println(bookDTO.isBookStatus() +"status");

            int result = borrowRecordDAO.rentBook(getConnection(), borrowRecordDTO);
            if (result > 0) {
                System.out.println("대여가 완료 되었습니다. 대여 시작일은 " + borrowDate + ", 반납 예정일은 대여 시작일로부터 2주 후인 "+ borrowDate.toLocalDate().plusWeeks(2) + "입니다.");
                }else{
                System.out.println("도서 대여에 실패했습니다. 다시 시도해주세요.");
            }
        } catch (
                InputMismatchException e) {
        }
    }


    public void returnBook() {
        try {
            mypageDAO.currentBorrowBooks(getConnection(), borrowRecordDTO, userDTO);

            Scanner sc = new Scanner(System.in);
            System.out.println("반납할 책의 코드 번호를 입력해주세요.");
            int bookCode = sc.nextInt();
            bookDTO.setBookCode(bookCode);
            LocalDate returnDate = LocalDate.now();
            borrowRecordDTO.setReturnDate(Date.valueOf(returnDate));

            int result = borrowRecordDAO.returnBook(getConnection(), borrowRecordDTO);
            if (result > 0) {
                System.out.println("도서 반납이 완료 되었습니다.");
            } else {
                System.out.println("도서 반납에 실패했습니다. 다시 시도해주세요.");
            }
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
        }
    }

    public void overDueBooks(){
        int result = borrowRecordDAO.overDueBook(getConnection(), borrowRecordDTO);
        if (result > 0) {
            System.out.println("-----------연체된 책 목록------------");
            borrowRecordDAO.overDueBookList(getConnection(), borrowRecordDTO);
        }else {
            System.out.println("연체 목록을 가져오지 못했습니다. 다시 시도해주세요.");
        }
    }



   /* private String loggedInUserId;          // 로그인된 사용자 ID를 저장할 필드임
    BookDAO bookDAO = new BookDAO();

    // 로그인 후 사용자 ID를 설정하는 메서드
    public void setLoggedInUserId(String userId) {
        this.loggedInUserId = userId;
        borrowRecordDTO.setUserId(userId); // BorrowRecordDTO에도 설정
    }


    public void rentBook() {
        // 대여 가능한 책 리스트를 가져오기
        List<BookDTO> availableBooks = bookDAO.getAvailableBooks(getConnection());

        if (availableBooks.isEmpty()) {
            System.out.println("대여 가능한 책이 없습니다.");
            return;
        }

        System.out.println("========== 대여 가능한 책 목록 ==========");
        for (BookDTO book : availableBooks) {
            System.out.println("코드 : " + book.getBookCode() + ", 제목 : " + book.getBookTitle() +
                    ", 저자 : " + book.getBookAuthor() + ", 장르 : " + book.getBookGenre());
        }
        System.out.println("========================================");

        Scanner sc = new Scanner(System.in);
        System.out.print("\n대여하고 싶은 책의 코드를 입력해주세요 : ");
        int bookCode = sc.nextInt();
        System.out.print("대여일을 입력해주세요 (YYYY-MM-DD) : ");
        LocalDate borrowDate = LocalDate.parse(sc.next());

        borrowRecordDTO.setBookCode(bookCode);
        borrowRecordDTO.setBorrowDate(borrowDate);
        borrowRecordDTO.setUserId(loggedInUserId); // 로그인된 사용자 ID 설정

        int result = borrowRecordDAO.rentBook(getConnection(), borrowRecordDTO);
        if(result > 0){
            System.out.println(borrowDate + "부터 도서 대여가 시작됩니다. 반납 예정일은 2주 후 입니다.");
        }else{
            System.out.println("도서 대여에 실패했습니다. 다시 시도해주세요.");
        }
    }

    public void returnBook() {
        Scanner sc = new Scanner(System.in);
        System.out.println("========== 현재 대여 중인 책 목록 ==========");

        // 로그인된 사용자의 대여 기록 가져오기
        String loggedInUserId = borrowRecordDTO.getUserId(); // 로그인된 사용자의 ID를 가져옴
        List<BorrowRecordDTO> borrowedBooks = borrowRecordDAO.getBorrowedBooksByUser(getConnection(), loggedInUserId);

        if (borrowedBooks.isEmpty()) {
            System.out.println("현재 대여 중인 책이 없습니다.");
            return;
        }

        for (BorrowRecordDTO record : borrowedBooks) {
            System.out.println("코드: " + record.getBookCode() + ", 대여일: " + record.getBorrowDate() + ", 반납 예정일: " + record.getDueDate());
        }
        System.out.println("========================================");

        System.out.print("\n반납할 책의 코드 번호를 입력해주세요 : ");
        int bookCode = sc.nextInt();
        LocalDate returnDate = LocalDate.now();

        borrowRecordDTO.setBookCode(bookCode);
        borrowRecordDTO.setReturnDate(returnDate);
        borrowRecordDTO.setUserId(loggedInUserId); // 로그인된 사용자 ID 설정

        int result = borrowRecordDAO.returnBook(getConnection(), borrowRecordDTO);
        if(result > 0){
            System.out.println("도서 반납이 완료되었습니다.");
        }else {
            System.out.println("도서 반납에 실패했습니다. 다시 시도해주세요.");
        }
    }*/
}

