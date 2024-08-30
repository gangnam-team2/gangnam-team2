package com.ohgiraffers.request.controller;

import com.ohgiraffers.request.dao.RequestDAO;
import com.ohgiraffers.request.dto.RequestDTO;

import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class RequestController { // 도서 요청 컨트롤러, 서현준이가 맹급니다.

    private static RequestDAO requestDAO = new RequestDAO("src/main/resources/mapper/manRe-query.xml");

    public static void insertRequestedBook()
    {
        Scanner scr = new Scanner(System.in);
        RequestDTO requestDTO = new RequestDTO();
        int result = 0;

        System.out.println("도서관에 추가하고 싶은 책의 정보를 받겠습니다.");

        System.out.println("책의 제목을 알려주세요 : ");
        requestDTO.setBookTitle(scr.nextLine());
        System.out.println("작가를 알려주세요 : ");
        requestDTO.setBookAuthor(scr.nextLine());
        System.out.println("출판사를 알려주세요 : ");
        requestDTO.setBookPublisher(scr.nextLine());

        result = requestDAO.insertRequestedBook(getConnection(), requestDTO);
        if(result > 0){
            System.out.println("도서 요청이 성공적으로 이루어졌습니다.");
        }else {
            System.out.println("요청이 거부되었습니다. 다시 시도해 주세요");
        }

    }

}
