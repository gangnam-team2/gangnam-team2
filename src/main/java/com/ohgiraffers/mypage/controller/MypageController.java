package com.ohgiraffers.mypage.controller;


import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.mypage.dao.MypageDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.sql.Connection;
import java.util.Scanner;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class MypageController {

    MypageDAO mypageDAO = new MypageDAO();
    BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
    UserDTO userDTO = new UserDTO();

    public void currentBorrowBookList() {
        System.out.println("----------------현재 대여중인 책 목록---------------");
        mypageDAO.currentBorrowBooks(getConnection(), borrowRecordDTO, userDTO);
    }


    public void updateRequestBook() {
/*        Connection con = getConnection();
        BorrowRecordDTO borrowDTO = new BorrowRecordDTO();*/
        Scanner sc = new Scanner(System.in);
        System.out.println("현재 신청하신 대여 책 목록입니다.");
        currentBorrowBookList();

       System.out.println("변경 신청할 책의 북코드를 입력해주세요.");
        borrowRecordDTO.setBookCode(sc.nextInt());

        System.out.println("신청하신 도서의 대여를 취소합니다.");
        mypageDAO.updateRequest(getConnection(), borrowRecordDTO);
        // 예외처리 필요

    }


    public void allBorrowBookList(){
        System.out.println("-------------------나의 전체 대여 목록-------------------------");
        mypageDAO.allBorrowBookList(getConnection(), borrowRecordDTO, userDTO);
    }

    public void myOverDueBooks() {
        System.out.println("-------------나의 연체 목록----------------");
        mypageDAO.myOverDueBooks(getConnection(), borrowRecordDTO);
    }


    public void pwdUpdate(){

        System.out.println("비번" + userDTO.getUserPwd());

        Scanner sc = new Scanner(System.in);
        System.out.println("비밀번호를 입력해주세요.");
        String password = sc.nextLine();
        if(password == userDTO.getUserPwd()){

            System.out.println("새로운 비밀번호를 입력해주세요.");
            String changePwd = sc.nextLine();

            int result = mypageDAO.pwdUpdate(getConnection(),userDTO, changePwd);
            if (result > 0) {
                System.out.println("비밀번호가 변경 되었습니다.");
            }else{
                System.out.println("비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
            }
        }
            System.out.println("현재 사용 중인 비밀번호와 다릅니다. 다시 시도해 주세요.");
        }
    }

