package com.ohgiraffers.book.controller;

import com.ohgiraffers.book.dao.BestSellersDAO;
import com.ohgiraffers.book.dto.BestSellersDTO;
import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.common.JDBCTemplate;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class BestSellersController {
    private BestSellersDAO bestSellersDAO;
    private Scanner sc;

    public BestSellersController() {
        bestSellersDAO = new BestSellersDAO();
        sc = new Scanner(System.in);
    }

    public void showBestSellers() {
        System.out.println("조회할 기간을 선택하세요");
        System.out.println("1. 이번주 베스트셀러");
        System.out.println("2. 최근 한달간 베스트셀러");
        System.out.println("3. 최근 일년간 베스트셀러");
        System.out.print("선택 : ");

        int choice = sc.nextInt();
        sc.nextLine();

        String period = null;
        switch (choice) {
            case 1:
                period = "WEEK";
                break;
            case 2:
                period = "MONTH";
                break;
            case 3:
                period = "YEAR";
                break;
            default:
                System.out.println("잘못된 선택입니다.");
                return;
        }

        Connection con = JDBCTemplate.getConnection();
        List<BookDTO> bestSellersList = bestSellersDAO.selectTopBorrowedBooksByPeriod(con, period);
        JDBCTemplate.close(con);

        if (!bestSellersList.isEmpty()) {
            System.out.println("가장 많이 대여된 도서 목록 (" + period + "):");
            for (BookDTO book : bestSellersList) {
                System.out.println(book.getBookTitle() + " - 대여 횟수: " + book.getBorrowCount());
            }
        } else {
            System.out.println("해당 기간의 베스트셀러가 없습니다.");
        }
    }
}
