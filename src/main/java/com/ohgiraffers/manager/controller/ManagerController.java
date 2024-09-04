package com.ohgiraffers.manager.controller;

import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.borrowrecord.dto.*;
import com.ohgiraffers.manager.dao.ManagerDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class ManagerController {

private ManagerDAO managerDAO = new ManagerDAO("src/main/resources/mapper/manager-query.xml");
    Scanner scr = new Scanner(System.in);

    public void displayManager(){

        loop: while(true){
            try {
                System.out.println("사용자 관리 모드 입니다.");

                System.out.println("1. 회원들의 정보를 조회합니다.");
                System.out.println("2. 대여 중인 책과 회원들을 조회합니다.");
                System.out.println("3. 현재 연체 중인 회원들을 조회합니다.");
                System.out.println("4. 이전 선택창으로 돌아가기");
                System.out.print("선택 : ");

                int num = scr.nextInt();
                switch (num){
                    case 1: allMembersInfo(); break;
                    case 2: findBookList(); break;
                    case 3: memberHistoy(); break;
                    case 4: break loop;

                    default: break;

                }
            } catch (InputMismatchException e) {
                System.out.println("선택창 내의 숫자를 입력해 주세요.");
                scr.nextLine();
            }
        }



    }

    public void allMembersInfo(){
        List<UserDTO> allMembersInfo;
        allMembersInfo = managerDAO.selectAllMembersInfo(getConnection());
        System.out.println("회원 정보 리스트");
        for (UserDTO member : allMembersInfo){
            System.out.println("USER ID: " + member.getUserId() + "   USER NAME: " + member.getUserName()
            + "   USER JOIN DATE" + member.getUserCreatedAt() + "   USER UPDATE DATE" + member.getUserUpdatedAt());
        }
    }

    public void findBookList() {
        Map<String, List<BookDTO>> userBooks= managerDAO.selectAllBooksInfo(getConnection());
        System.out.println("대여 중인 책과 그 회원 목록:");
        for (Map.Entry<String, List<BookDTO>> list : userBooks.entrySet()) {
            String userId = list.getKey();
            List<BookDTO> booksInfo = list.getValue();

            System.out.println("도서를 대여 중인 사용자: " +userId);

            for(BookDTO book : booksInfo){
                System.out.println("책 제목: " + book.getBookTitle()+ "   작가: "+book.getBookAuthor()
                + "   책 고유번호: " + book.getBookCode());

            }

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

