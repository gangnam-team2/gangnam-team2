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

    /** 관리자 메뉴 출력 메서드*/
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

                    default:
                        System.out.println("선택창 내의 숫자를 입력해 주세요.");break;

                }
            } catch (InputMismatchException e) {
                System.out.println("선택창 내의 숫자를 입력해 주세요.");
                scr.nextLine();
            }
        }



    }

    /** 모든 회원 정보를 보여주는 메서드*/
    public void allMembersInfo(){
        List<UserDTO> allMembersInfo;
        allMembersInfo = managerDAO.selectAllMembersInfo(getConnection());
        System.out.println("회원 정보 리스트");
        for (UserDTO member : allMembersInfo){

            System.out.printf("회원 아이디: %s | 회원 이름: %s | 정보 수정일: %s | 가입일: %s\n",
                    member.getUserId(), member.getUserName(), member.getUserUpdatedAt(), member.getUserCreatedAt());

            /*"회원 아이디: " + member.getUserId()
                          + "   회원 이름: " + member.getUserName()
                          + "   가입일" + member.getUserCreatedAt()
                          + "   정보 수정일" + member.getUserUpdatedAt()*/

        }
    }

    /** 대여중인 책과 회원들을 출력하는 메서드*/
    public void findBookList() {
        List<BorrowRecordDTO> booksInfoAndUserId;
        booksInfoAndUserId =managerDAO.selectBooksAndUser(getConnection());
        System.out.println("대여 중인 책과 해당 회원 아이디");
        for (BorrowRecordDTO book : booksInfoAndUserId) {
            System.out.printf("빌려간 회원 아이디: %s | 대여 시작일: %s | 대여 만기일: %s | 책 제목: %s | 책 코드: %d\n",
                    book.getUserId(), book.getBorrowCode(), book.getDueDate(), book.getBookTitle(), book.getBookCode());

                    /*"책 제목: " + book.getBookTitle()
                          + "   책 코드: " + book.getBookCode()
                          + "   빌려간 회원 아이디: " + book.getUserId()
                          + "   대여한 날짜: " + book.getBorrowCode()
                          + "   대여 만기일: " + book.getDueDate()*/

        }



        /*Map<String, List<BookDTO>> userAndBooks = managerDAO.selectAllBooksInfo(getConnection());
        System.out.println("대여 중인 책과 그 회원 목록:");

        for (Map.Entry<BorrowRecordDTO, List<BookDTO>> list : userAndBooks.entrySet()) {
            BorrowRecordDTO userId = list.getKey();
            List<BookDTO> booksInfo = list.getValue();
            for (BorrowRecordDTO user : userId){
                System.out.println("도서를 대여 중인 사용자: " +user.getUserId());
                for(BookDTO book : booksInfo){
                    System.out.println("   책 제목: " + book.getBookTitle()+ "   작가: "+book.getBookAuthor()
                            + "   책 고유번호: " + book.getBookCode());

                }

            }

        }*/

    }
        /** 연체 이력이 있는 회원을 출력하는 메서드*/
        public void memberHistoy () {
            List<BorrowRecordDTO> lateMember;
            lateMember = managerDAO.selectMemberHistoy(getConnection());
            System.out.println("현재 대여 만기일을 넘긴 회원들의 목록과 해당 책 제목"); // 실제 반납일 > 대여 마감일, 현재를 반영하기 어렵다.
            for (BorrowRecordDTO member : lateMember) {
                System.out.printf("회원 아이디: %s | 책 제목: %s | 책 코드: %d\n",
                        member.getUserId(), member.getBookTitle(), member.getBookCode());

                        /*"회원 아이디: " + member.getUserId()
                              + "   책 제목: " + member.getBookTitle());*/
            }

        }

    }

