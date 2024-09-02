package com.ohgiraffers.manager.controller;

import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.borrowrecord.dto.*;
import com.ohgiraffers.manager.dao.ManagerDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class ManagerController {

private ManagerDAO managerDAO = new ManagerDAO("src/main/resources/mapper/manager-query.xml");

    public void displayManager(){
        System.out.println("사용자 관리 모드 입니다.");

        System.out.println("1. 회원들의 정보를 조회합니다.");
        System.out.println("2. 대여 중인 책과 회원들을 조회합니다.");
        System.out.println("3. 현재 연체 중인 회원들을 조회합니다.");
        System.out.print("선택 : ");
        Scanner scr = new Scanner(System.in);
        int num = scr.nextInt();
        switch (num){
            case 1: allMembersInfo(); break;
            case 2: findBookList(); break;
            case 3: memberHistoy(); break;

            default: break;

        }

    }

    public void allMembersInfo(){
        List<UserDTO> allMembersInfo;
        allMembersInfo = managerDAO.selectAllMembersInfo(getConnection());
        System.out.println("회원 정보 리스트");
        for (UserDTO member : allMembersInfo){
            System.out.println(member);
        }
    }

    public void findBookList() {
        List<BookDTO> allBookList;
        allBookList = managerDAO.selectAllBooksInfo(getConnection());
        System.out.println("대여 중인 책과 그 회원 목록:");
        for (BookDTO book : allBookList) {
            System.out.println(book);
        }
    }

        public void memberHistoy () {
            List<BorrowRecordDTO> lateMember;
            lateMember = managerDAO.selectMemberHistoy(getConnection());
            System.out.println("연체 이력이 있는 회원 목록입니다."); // 실제 반납일 > 대여 마감일, 현재를 반영하기 어렵다.
            for (BorrowRecordDTO member : lateMember) {
                System.out.println(member);
            }


        }

    }

