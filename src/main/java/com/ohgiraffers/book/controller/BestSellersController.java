package com.ohgiraffers.book.controller;

import com.ohgiraffers.book.dao.BestSellersDAO;
import com.ohgiraffers.book.dto.BestSellersDTO;
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

    // 선택지를 통해 기간별 베스트셀러를 조회하는 메서드
    public void showBestSellersBySelectedPeriod() {
        System.out.println("조회할 기간을 선택하세요:");
        System.out.println("1. 일주일");
        System.out.println("2. 한 달");
        System.out.println("3. 1년");
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
        List<BestSellersDTO> bestSellersList = bestSellersDAO.selectBestSellersByPeriod(con, period);
        JDBCTemplate.close(con);

        if (!bestSellersList.isEmpty()) {
            for (BestSellersDTO bestSellersDTO : bestSellersList) {
                System.out.println(bestSellersDTO);
            }
        } else {
            System.out.println("해당 기간의 베스트셀러가 없습니다.");
        }
    }

    public void showAllBestSellers() {
        Connection con = JDBCTemplate.getConnection();
        List<BestSellersDTO> bestSellersList = bestSellersDAO.selectAllBestSellers(con);
        JDBCTemplate.close(con);

        if (!bestSellersList.isEmpty()) {
            for (BestSellersDTO bestSellersDTO : bestSellersList) {
                System.out.println(bestSellersDTO);
            }
        } else {
            System.out.println("베스트셀러가 없습니다.");
        }
    }
}
