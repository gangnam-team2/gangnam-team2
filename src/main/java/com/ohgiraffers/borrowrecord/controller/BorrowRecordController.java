package com.ohgiraffers.borrowrecord.controller;


import com.ohgiraffers.usersession.UserSession;
import com.ohgiraffers.borrowrecord.dao.BorrowRecordDAO;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.user.dto.UserDTO;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class BorrowRecordController {

    /** 도서 대여 메서드*/
    public void rentBook() {
        try {
            BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
            BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

            // showBookList를 호출해서 List형식으로 반환
            List<Integer> availableBookCodes = borrowRecordDAO.showBookList(getConnection(), borrowRecordDTO);

            // availableBookCodes 이거는 리스트가 비어 있으면 대여할 수 있는 책이 있는지 없는지 검증
            if (availableBookCodes.isEmpty()) {
                System.out.println("\n대여 가능한 책이 없습니다.");
                return;
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("대여하고 싶은 도서의 코드를 입력해주세요 :");
            int bookCode = sc.nextInt();

            // availableBookCodes여기에 사용자가 입력한 도서 코드가 없으면 실행 -> 리스트로 가져온 북 리스트에서 북 코드 비교
            if (!availableBookCodes.contains(bookCode)) {
                System.out.println("\n이미 대여되었거나 존재하지 않는 도서입니다.");
                return;
            }

            borrowRecordDTO.setBookCode(bookCode);
            Date borrowDate = Date.valueOf(LocalDate.now());
            borrowRecordDTO.setBorrowDate(borrowDate);

            int result = borrowRecordDAO.rentBook(getConnection(), borrowRecordDTO);
            if (result > 0) {
                System.out.println("\n대여가 완료되었습니다!  대여 시작일은 " + borrowDate +
                        ", 반납 예정일은 대여 시작일로부터 2주 후인 " + borrowDate.toLocalDate().plusWeeks(2) + "입니다.");
            } else {
                System.out.println("\n도서 대여에 실패했습니다.");
            }

        } catch (InputMismatchException e) {
            System.out.println("\n보기의 메뉴를 선택해주세요. ");
        }
    }


    /** 도서 반납 메서드*/
    public void returnBook() {
        Connection con = getConnection();
        try {
            BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
            BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
            UserDTO userDTO = UserSession.getUserDTO(); // 로그인된 사용자 정보 가져오기

            // 현재 로그인된 사용자가 대여한 책 목록을 보여줌
            List<BorrowRecordDTO> borrowedBooks = borrowRecordDAO.getBorrowedBooks(con, userDTO.getUserId());

            if (borrowedBooks.isEmpty()) {
                System.out.println("\n현재 대여 중인 책이 없습니다. ");
                return;
            }

            String logind = UserSession.getUserDTO().getUserId();
            System.out.println("=== " + logind + "님이 대여 중인 책 목록 ===");
            for (BorrowRecordDTO book : borrowedBooks) {
                System.out.printf("도서 코드: %d | 도서 제목: %s | 대여일: %s\n",
                        book.getBookCode(), book.getBookTitle(), book.getBorrowDate());
            }
            System.out.println("======================");

            // 반납할 책의 코드 입력 받기
            Scanner sc = new Scanner(System.in);
            System.out.print("반납할 도서의 코드 번호를 입력해주세요 : ");
            int bookCode = sc.nextInt();
            borrowRecordDTO.setBookCode(bookCode);
            borrowRecordDTO.setUserId(userDTO.getUserId()); // 유저 ID 설정
            LocalDate returnDate = LocalDate.now();
            borrowRecordDTO.setReturnDate(Date.valueOf(returnDate));

            int result = borrowRecordDAO.returnBook(con, borrowRecordDTO);
            if (result > 0) {
                System.out.println("\n도서 반납이 완료되었습니다.");
            } else {
                System.out.println("\n도서 반납에 실패했습니다. ");
            }
        } catch (InputMismatchException e) {
            System.out.println("\n보기의 메뉴를 선택해주세요. ");
        } finally {
            close(con);
        }
    }


    /** 연체된 도서 목록 출력 메서드*/
    public void overDueBooks() {
        BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
        BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

        borrowRecordDAO.getBorrowRecords(getConnection(), borrowRecordDTO); // 대여 기록 조회
        int result = borrowRecordDAO.overDueBook(getConnection(), borrowRecordDTO); // 연체 여부 확인 및 상태 업데이트

        if (result > 0) {
            System.out.println("=== 연체된 책 목록 ===");
            borrowRecordDAO.overDueBookList(getConnection(), borrowRecordDTO);
            System.out.println("====================");
        } else {
            System.out.println("\n도서 연체 목록이 없습니다. 이전 메뉴로 돌아갑니다 !");
        }
    }


    /** borrow_records 테이블의 user_id 값 모두 출력*/
    public void printAllUserIds() {
        Connection con = getConnection();
        BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
        List<String> userIds = borrowRecordDAO.getAllUserIds(con);
        close(con);

        if (!userIds.isEmpty()) {
            System.out.println("=== 대여 기록에 있는 사용자 ID 목록 ===");
            for (String userId : userIds) {
                System.out.println(userId);
            }
            System.out.println("===================");
        } else {
            System.out.println("\n대여 기록에 사용자 정보가 없습니다. 이전 메뉴로 돌아갑니다 !");
        }
    }
}

