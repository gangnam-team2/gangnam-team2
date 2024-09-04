package com.ohgiraffers.book.controller;

import com.ohgiraffers.book.dao.BestSellersDAO;
import com.ohgiraffers.book.dto.BookDTO;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class BestSellersController { // 사용자 관리자 모두 함께 공유하는 클래스이다.
    private BestSellersDAO bestSellersDAO;
    private Scanner sc; // 필드에서 클래스들을 선언

    public BestSellersController() {
        bestSellersDAO = new BestSellersDAO();
        sc = new Scanner(System.in);
    }

    /** 기간별 베스트셀러 도서 출력 메서드*/
    public void showBestSellers(Scanner sc) {
        System.out.println("조회할 기간을 선택하세요");
        System.out.println("1. 이번주 베스트셀러");
        System.out.println("2. 최근 한달간 베스트셀러");
        System.out.println("3. 최근 일년간 베스트셀러");
        System.out.print("선택 : ");

        int choice = sc.nextInt();
        sc.nextLine(); // 개행 받고~

        String period = null;

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
                System.out.println("잘못된 선택입니다. 다시 시도해 주세요.");
                return; // 메소드 탈출 > 호출한 곳으로 복귀
        }

        Connection con = getConnection();
        List<BookDTO> bestSellersList = bestSellersDAO.selectBestSellersByPeriod(con, period); // 알뜰살뜰하신 팀장님.. 마이 배웁니다.
        close(con);

        if (!bestSellersList.isEmpty()) {
            System.out.println("==================== " + period + "의 베스트셀러입니다. ====================");
            for (BookDTO book : bestSellersList) {
                System.out.printf("도서 제목: %s | 도서 저자: %s | 도서 장르: %s | 대여 횟수: %d\n",
                        book.getBookTitle(), book.getBookAuthor(), book.getBookGenre(), book.getBorrowCount());
                System.out.println("---------------------------------------------------------------------");
            }
        } else {
            System.out.println("해당 기간의 베스트셀러가 없습니다.");
        }
    }
}

