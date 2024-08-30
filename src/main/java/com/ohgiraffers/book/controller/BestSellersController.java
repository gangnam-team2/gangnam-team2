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

    public void showBestSellersByPeriod() {
        System.out.print("조회할 기간을 입력하세요 (WEEK, MONTH, YEAR): ");
        String period = sc.nextLine().toUpperCase();

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

    public void addBestSeller() {
        System.out.print("도서 코드를 입력하세요: ");
        int bookCode = sc.nextInt();
        System.out.print("대여 횟수를 입력하세요: ");
        int borrowCount = sc.nextInt();
        sc.nextLine();
        System.out.print("기간을 입력하세요 (WEEK, MONTH, YEAR): ");
        String period = sc.nextLine().toUpperCase();

        BestSellersDTO bestSellersDTO = new BestSellersDTO();
        bestSellersDTO.setBookCode(bookCode);
        bestSellersDTO.setBorrowCount(borrowCount);
        bestSellersDTO.setPeriod(period);

        Connection con = JDBCTemplate.getConnection();
        int result = bestSellersDAO.insertBestSeller(con, bestSellersDTO);
        JDBCTemplate.close(con);

        if (result > 0) {
            System.out.println("베스트셀러가 추가되었습니다.");
        } else {
            System.out.println("베스트셀러 추가에 실패했습니다.");
        }
    }
}
