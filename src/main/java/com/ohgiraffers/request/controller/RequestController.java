package com.ohgiraffers.request.controller;

import com.ohgiraffers.request.dao.RequestDAO;
import com.ohgiraffers.request.dto.RequestDTO;
import com.ohgiraffers.user.dto.UserDTO;
import com.ohgiraffers.usersession.UserSession;

import java.util.InputMismatchException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class RequestController {

    private static RequestDAO requestDAO = new RequestDAO();

    /** 요청된 도서를 추가하는 메서드*/
    public static boolean insertRequestedBook() throws SQLException {
        Scanner scr = new Scanner(System.in);
        RequestDTO requestDTO = new RequestDTO();
        int result = 0;
        UserDTO userDTO = UserSession.getUserDTO(); // 세션에서 로그인된 사용자 정보 가져오기

        requestDTO.setUserId(userDTO.getUserId());

        while (true) {
            try {
                System.out.println("도서관에 추가하고 싶은 책의 정보를 받겠습니다.");

                // 책 제목 입력
                while (true) {
                    System.out.print("책의 제목을 알려주세요 (특수문자 제외): ");
                    String bookTitle = scr.nextLine().trim();
                    if (bookTitle.matches("^[a-zA-Z0-9가-힣 ]+$")) { // 특수문자 체크 정규식
                        requestDTO.setBookTitle(bookTitle);
                        break;
                    } else {
                        System.out.println("책 제목에 특수문자가 포함될 수 없습니다. 다시 입력해주세요.");
                    }
                }

                // 작가 입력 (숫자 허용) -> 예명 사용하는 작가도 있기 때문
                while (true) {
                    System.out.print("작가를 알려주세요 (특수문자 제외): ");
                    String bookAuthor = scr.nextLine().trim();
                    if (bookAuthor.matches("^[a-zA-Z0-9가-힣 ]+$")) {
                        requestDTO.setBookAuthor(bookAuthor);
                        break;
                    } else {
                        System.out.println("작가 이름에 특수문자가 포함될 수 없습니다. 다시 입력해주세요.");
                    }
                }

                // 출판사 입력
                while (true) {
                    System.out.print("출판사를 알려주세요 (특수문자 제외): ");
                    String bookPublisher = scr.nextLine().trim();
                    if (bookPublisher.matches("^[a-zA-Z0-9가-힣 ]+$")) { // 특수문자 체크 정규식
                        requestDTO.setBookPublisher(bookPublisher);
                        break;
                    } else {
                        System.out.println("출판사 이름에 특수문자가 포함될 수 없습니다. 다시 입력해주세요.");
                    }
                }

                // 요청한 도서를 데이터베이스에 삽입
                result = requestDAO.insertRequestedBook(getConnection(), requestDTO);
                if (result > 0) {
                    System.out.println("도서 요청이 성공적으로 이루어졌습니다.");
                    System.out.println("계속 추가하시겠습니까?\n" +
                            "1. 계속 도서 추가하기\n" +
                            "2. 이전 선택창으로 돌아가기");
                    System.out.print("선택 : ");
                    int answer = scr.nextInt();
                    scr.nextLine();
                    if (answer == 1) {
                        continue;
                    } else if (answer == 2) {
                        break;
                    } else {
                        throw new InputMismatchException();
                    }
                } else {
                    System.out.println("요청이 거부되었습니다. 다시 시도해 주세요");
                }

            } catch (InputMismatchException e) {
                System.out.println("입력 과정에 문제가 발생했습니다. 확인해 보시고 다시 시도해 주세요.");
                scr.nextLine(); // 입력 버퍼 비우기
            }
        }
        return result > 0;
    }

    /** 요청된 도서를 가져오는 메서드*/
    public List<RequestDTO> getRequestedBooks() {
        Connection con = getConnection();
        List<RequestDTO> requestedBooks = requestDAO.getRequestedBooks(con);
        close(con);
        return requestedBooks;
    }

    /** 요청된 도서를 삭제하는 메서드*/
    public boolean deleteRequestedBook(int requestId) {
        Connection con = getConnection();
        boolean isDeleted = false;
        try {
            isDeleted = requestDAO.deleteRequest(con, requestId);
        } catch (SQLException e) {
            System.out.println("요청된 도서 삭제에 실패하였습니다.");
            e.printStackTrace();
        } finally {
            close(con);
        }
        return isDeleted;
    }
}

