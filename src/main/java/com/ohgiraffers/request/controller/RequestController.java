package com.ohgiraffers.request.controller;

import com.ohgiraffers.request.dao.RequestDAO;
import com.ohgiraffers.request.dto.RequestDTO;
import com.ohgiraffers.user.dto.UserDTO;
import java.util.InputMismatchException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class RequestController { // 도서 요청 컨트롤러, 서현준이가 맹급니다.

    private static RequestDAO requestDAO = new RequestDAO();

    public static void insertRequestedBook() {
        Scanner scr = new Scanner(System.in);
        RequestDTO requestDTO = new RequestDTO();
        int result = 0;
        UserDTO userDTO = new UserDTO();

        requestDTO.setUserId(userDTO.getUserId());

        while (true){
            try {
                System.out.println("도서관에 추가하고 싶은 책의 정보를 받겠습니다.");

                System.out.print("책의 제목을 알려주세요 : ");
                requestDTO.setBookTitle(scr.nextLine());
                System.out.print("작가를 알려주세요 : ");
                requestDTO.setBookAuthor(scr.nextLine());
                System.out.print("출판사를 알려주세요 : ");
                requestDTO.setBookPublisher(scr.nextLine());

                result = requestDAO.insertRequestedBook(getConnection(), requestDTO);
                if(result > 0){
                    System.out.println("도서 요청이 성공적으로 이루어졌습니다.");
                    System.out.println("계속 추가하시겠습니까?\n" +
                            "1. 계속 도서 추가하기\n" +
                            "2. 이전 선택창으로 돌아가기");
                    System.out.print("선택 : ");
                    int answer = scr.nextInt();
                    if(answer==1){
                        continue;
                    }else if(answer==2){
                        break;
                    }else{
                        throw new InputMismatchException();
                    }
                }else {
                    System.out.println("요청이 거부되었습니다. 다시 시도해 주세요");
                }
            }catch (InputMismatchException e){
            }
            System.out.println("입력 과정에 문제가 발생했습니다. 확인해 보시고 다시 시도해 주세요.");
            scr.nextLine();
        result = requestDAO.insertRequestedBook(getConnection(), requestDTO);
        if (result > 0) {
            System.out.println("도서 요청이 성공적으로 이루어졌습니다.");
        } else {
            System.out.println("요청이 거부되었습니다. 다시 시도해 주세요");
        }

    }

    public List<RequestDTO> getRequestedBooks() {
        Connection con = getConnection();

        // DAO 인스턴스를 통해 메서드를 호출하는 것
        List<RequestDTO> requestedBooks = requestDAO.getRequestedBooks(con);

        close(con);
        return requestedBooks;
    }

    // 요청된 도서를 삭제하는 메서드
    public boolean deleteRequestedBook(int requestId) {
        Connection con = getConnection();
        boolean isDeleted = false;
        try {
            isDeleted = requestDAO.deleteRequest(con, requestId); // 요청 ID를 통해 삭제
        } catch (SQLException e) {
            System.out.println("요청된 도서 삭제에 실패하였습니다.");
            e.printStackTrace();
        } finally {
            close(con);
        }
        return isDeleted;
    }
}
}
