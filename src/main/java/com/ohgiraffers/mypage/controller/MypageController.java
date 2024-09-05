package com.ohgiraffers.mypage.controller;


import com.ohgiraffers.borrowrecord.dao.BorrowRecordDAO;
import com.ohgiraffers.usersession.UserSession;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.mypage.dao.MypageDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.util.List;
import java.util.Scanner;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class MypageController {

    /**현재 로그인한 사용자가 대여한 도서 목록을 출력하는 메서드*/
    public void currentBorrowBookList() {
        MypageDAO mypageDAO = new MypageDAO();
        UserDTO userDTO = UserSession.getUserDTO();  // 세션에서 사용자 정보 가져오기

        System.out.println("=== 현재 대여중인 책 목록 ===");

        List<BorrowRecordDTO> currentBorrowedBooks = mypageDAO.currentBorrowBooks(getConnection(), userDTO.getUserId());

        if (currentBorrowedBooks.isEmpty()) {
            System.out.println("\n현재 대여중인 책이 없습니다. 이전 메뉴로 돌아갑니다!");
        } else {
            for (BorrowRecordDTO book : currentBorrowedBooks) {
                System.out.printf("북코드: %d | 제목: %s | 대여 날짜: %s | 반납 예정일: %s\n",
                        book.getBookCode(), book.getBookTitle(), book.getBorrowDate(), book.getDueDate());
            }
        }

        System.out.println("===============");
    }

    /** 현재 로그인한 사용자가 대여한 도서를 출력하는 메서드 -- 대여 취소 기능 추후 사용을 위해 주석**/
    public void updateRequestBook() {
        MypageDAO mypageDAO = new MypageDAO();
        Scanner sc = new Scanner(System.in);

        System.out.println("\n현재 신청하신 대여 책 목록입니다 !");

        // 현재 대여 중인 책 목록
        List<BorrowRecordDTO> currentBorrowedBooks = mypageDAO.currentBorrowBooks(getConnection(), UserSession.getUserDTO().getUserId());

        // 대여 중인 책이 없으면 종료
        if (currentBorrowedBooks.isEmpty()) {
            System.out.println("\n현재 대여 중인 책이 없습니다. 이전 메뉴로 돌아갑니다 !");
            return;
        }

        // 대여 중인 책 목록 출력
        for (BorrowRecordDTO book : currentBorrowedBooks) {
            System.out.printf("북코드: %d | 제목: %s | 대여일: %s | 반납 예정일: %s\n",
                    book.getBookCode(), book.getBookTitle(), book.getBorrowDate(), book.getDueDate());
        }

        // 대여 중인 책이 있는 경우에만 북코드를 입력받음
        System.out.println("==대여 취소할 도서의 코드를 입력해주세요.==");
        System.out.print("도서 코드 : ");
        BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
        borrowRecordDTO.setBookCode(sc.nextInt());

        System.out.println("\n신청하신 도서의 대여를 취소합니다.");
        int result = mypageDAO.updateRequest(getConnection(), borrowRecordDTO);
        if (result > 0) {
            System.out.println("\n도서 대여 취소가 완료되었습니다 ! ");
        } else {
            System.out.println("\n도서 대여 취소에 실패했습니다. ");
        }
    }

    /** 현재 사용자가 대여한 도서를 출력하는메서드*/
    public void allBorrowBookList() {
        MypageDAO mypageDAO = new MypageDAO();
        BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
        UserDTO userDTO = UserSession.getUserDTO();  // 세션에서 사용자 정보 가져오기

        System.out.println("===" + userDTO.getUserId() + "님의 대여기록  ===");
        mypageDAO.allBorrowBookList(getConnection(), borrowRecordDTO, userDTO);
        System.out.println("===============");
    }

    /** 현재 사용자의 연체된 도서 목록을 출력하는 메서드*/
    public void myOverDueBooks() {
        MypageDAO mypageDAO = new MypageDAO();
        BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
        UserDTO userDTO = UserSession.getUserDTO();  // 세션에서 사용자 정보 가져오기
        BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
        borrowRecordDAO.getBorrowRecords(getConnection(),borrowRecordDTO);
        int result = borrowRecordDAO.overDueBook(getConnection(), borrowRecordDTO);
        if (result > 0) {
            System.out.println("=== 현재 " + userDTO.getUserId() + "님의 연체중인 책 목록 ===");
            mypageDAO.myOverDueBooks(getConnection(), borrowRecordDTO, userDTO);
            }else {
                System.out.println("연체 목록을 가져오지 못했습니다. 다시 시도합니다 !");
            }
        System.out.println("================");
    }

    /** 사용자의 비밀번호를 수정하는 메서드*/
    public void pwdUpdate() {
        MypageDAO mypageDAO = new MypageDAO();
        UserDTO userDTO = UserSession.getUserDTO();  // 세션에서 사용자 정보 가져오기

        Scanner sc = new Scanner(System.in);
        System.out.println("=== 비밀번호 변경 ===");
        System.out.println("현재 비밀번호를 입력해주세요 :");
        String password = sc.nextLine();
        if (password.equals(userDTO.getUserPwd())) {
            System.out.println("새로운 비밀번호를 입력해주세요 :");
            String changePwd = sc.nextLine();
            if (changePwd.equals(userDTO.getUserPwd())) {
                System.out.println("현재 비밀번호와 같습니다. 비밀번호 변경에 실패했습니다. ");
            } else {
                int result = mypageDAO.pwdUpdate(getConnection(), userDTO, changePwd);
                if (result > 0) {
                    System.out.println("비밀번호가 변경되었습니다.");
                } else {
                    System.out.println("비밀번호 변경에 실패했습니다. 다시 시도해 주세요.");
                }
            }
        } else {
            System.out.println("\n현재 사용 중인 비밀번호와 다릅니다. ");
        }
    }
}

