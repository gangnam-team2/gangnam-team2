package com.ohgiraffers.mypage.controller;

import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.mypage.dao.MypageDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class MypageController {

    MypageDAO mypageDAO = new MypageDAO();
    BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();


    public void currentBorrowBookList() {
        System.out.println("----------------현재 대여중인 책 목록---------------");
        mypageDAO.currentBorrowBooks(getConnection(), borrowRecordDTO);
    }

    public void updateRequestBook() {

        Scanner sc = new Scanner(System.in);
        System.out.println("현재 신청하신 대여 책 목록입니다.");
        mypageDAO.currentBorrowBooks(getConnection(), borrowRecordDTO);

       System.out.println("대여 신청한 책의 북코드를 입력해주세요.");
        borrowRecordDTO.setBookCode(sc.nextInt());
        System.out.println("변경 사항을 골라주세요.");
        System.out.println("1.대여일 변경  2.대여 취소");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("대여일 변경 날짜를 입력해주세요.");
                LocalDate changeDate = LocalDate.parse(sc.next());
              borrowRecordDTO.setBorrowDate(changeDate);
               int result = mypageDAO.updateRequest1(getConnection(), borrowRecordDTO);
                if (result > 0) {
                    System.out.println("도서 대여일 수정 완료");
                } else {
                    System.out.println("대여일 변경에 실패했습니다.");
                }
                break;
            case 2:
                borrowRecordDTO.setBookStatus(true);
                System.out.println("대여 신청이 취소되었습니다.");
                break;
            default:
                System.out.println("잘못 된 번호입니다. 다시 선택해주세요.");
                break;
        }

    }

    public void allBorrowBookList(){
        System.out.println("------------------전체 대여 목록-------------------------");
        mypageDAO.allBorrowBookList(getConnection(), borrowRecordDTO);
    }

    public void pwdUpdate(){
        UserDTO userDTO = new UserDTO();
        Scanner sc = new Scanner(System.in);
        System.out.println("비밀번호를 입력해주세요.");
        userDTO.setUserPwd(sc.nextLine());
        System.out.println("변경할 비밀번호를 입력해주세요.");
        String changePwd = sc.nextLine();

        int result = mypageDAO.pwdUpdate(getConnection(),userDTO, changePwd);
        if (result > 0) {
            System.out.println("비밀번호가 변경 되었습니다.");
        }else{
            System.out.println("비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
        }
    }

}