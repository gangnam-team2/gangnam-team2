package com.ohgiraffers.book.controller;

import com.ohgiraffers.book.dao.BestSellersDAO;
import com.ohgiraffers.book.dto.BookDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class BestSellersController {

    private final BestSellersDAO bestSellersDAO = new BestSellersDAO();

    public void showBestSellers(Scanner sc) {
        System.out.println("=== 조회할 기간을 선택하세요 ===");
        System.out.println("1. 이번주 베스트셀러");
        System.out.println("2. 최근 한달간 베스트셀러");
        System.out.println("3. 최근 일년간 베스트셀러");
        System.out.print("원하시는 메뉴의 번호를 선택해주세요: ");

        int choice = sc.nextInt();
        sc.nextLine();

        String period;
        switch (choice) {
            case 1:
                period = "이번주";
                break;
            case 2:
                period = "이번달";
                break;
            case 3:
                period = "올해";
                break;
            default:
                System.out.println("\n잘못된 선택입니다. 다시 시도합니다 !");
                return;
        }

        Connection con = getConnection();
        List<BookDTO> bestSellersList = bestSellersDAO.selectBestSellersByPeriod(con, period); // 여기서 베스트셀러 목록 조회
        close(con);

        if (!bestSellersList.isEmpty()) {
            System.out.println("=== " + period + "의 베스트셀러입니다. ===");
            for (BookDTO book : bestSellersList) {
                System.out.printf("도서 제목: %s | 도서 저자: %s | 도서 장르: %s | 대여 횟수: %d\n",
                        book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), book.getBorrowCount());
                System.out.println("=========================================");
            }
        } else {
            System.out.println("\n해당 기간의 베스트셀러가 없습니다. ");
        }
    }

    public void updateBestSellers() {
        try (Connection con = getConnection()) {
            bestSellersDAO.updateBestSellersTable(con);  // 베스트셀러 테이블 업데이트
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

