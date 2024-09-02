package com.ohgiraffers.borrowrecord.controller;


import com.ohgiraffers.borrowrecord.dao.BorrowRecordDAO;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.mypage.dao.MypageDAO;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class BorrowRecordController {
    BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
    BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

    public void rentBook(){

       borrowRecordDAO.showBookList(getConnection(),borrowRecordDTO);

        Scanner sc = new Scanner(System.in);
        System.out.println("대여하고 싶은 책의 코드를 입력해주세요.");
        int bookCode = sc.nextInt();
        System.out.println("대여일을 입력해주세요.(년/월/일)");
        LocalDate borrowDate = LocalDate.parse(sc.next());
        borrowRecordDTO.setBorrowDate(borrowDate);

        int result = borrowRecordDAO.rentBook(getConnection(),borrowRecordDTO);
        if(result > 0){
            System.out.println(borrowDate + " 부터 도서 대여가 시작됩니다. 반납 예정일은 2주 후 입니다.");
        }else{
            System.out.println("도서 대여에 실패했습니다. 다시 시도해주세요.");
        }

        borrowRecordDAO.overDueBook(getConnection(),borrowRecordDTO);
    }



    public void returnBook(){

        MypageDAO mypageDAO = new MypageDAO();
        mypageDAO.currentBorrowBooks(getConnection(),borrowRecordDTO);

        Scanner sc = new Scanner(System.in);
        System.out.println("반납할 책의 코드 번호를 입력해주세요.");
        int bookCode = sc.nextInt();
        LocalDate returnDate = LocalDate.now();
        borrowRecordDTO.setReturnDate(returnDate);

        int result = borrowRecordDAO.returnBook(getConnection(),borrowRecordDTO);
        if(result > 0){
            System.out.println("도서 반납이 완료 되었습니다.");
        }else {
            System.out.println("도서 반납에 실패했습니다. 다시 시도해주세요.");
        }
    }



}
