package com.ohgiraffers.book.dao;

import com.ohgiraffers.book.dto.BookDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BestSellersDAO {
    private final Properties prop = new Properties();

    public BestSellersDAO() {
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/bestSellers-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BookDTO> selectBestSellersByPeriod(Connection con, String period) {
        List<BookDTO> bestSellers = new ArrayList<>();
        String periodInterval;

        // 선택된 기간에 따른 필터링 처리
        switch (period) {
            case "이번주" -> periodInterval = "WEEK";
            case "이번달" -> periodInterval = "MONTH";
            case "올해" -> periodInterval = "YEAR";
            default -> throw new IllegalArgumentException(period + "은 유효하지 않는 기간입니다.");
        }

        // XML 쿼리에서 #{period}를 대체하는 방식으로 쿼리 준비
        String query = prop.getProperty("selectBestSellersByPeriod").replace("#{period}", periodInterval);

        // try-with-resources로 PreparedStatement와 ResultSet을 안전하게 처리
        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 집합을 돌면서 베스트셀러 목록을 BookDTO에 저장
            while (rs.next()) {
                BookDTO book = new BookDTO();
                book.setBookCode(rs.getInt("book_code"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookGenre(rs.getString("book_genre"));
                book.setBorrowCount(rs.getInt("borrow_count"));
                bestSellers.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 베스트셀러 리스트 반환
        return bestSellers;
    }

    // 베스트셀러 테이블 업데이트 로직
    public void updateBestSellersTable(Connection con) {
        String updateBestSellersWeek = prop.getProperty("updateBestSellersWeek");
        String updateBestSellersMonth = prop.getProperty("updateBestSellersMonth");
        String updateBestSellersYear = prop.getProperty("updateBestSellersYear");

        try (PreparedStatement pstmtWeek = con.prepareStatement(updateBestSellersWeek);
             PreparedStatement pstmtMonth = con.prepareStatement(updateBestSellersMonth);
             PreparedStatement pstmtYear = con.prepareStatement(updateBestSellersYear)) {

            int weekResult = pstmtWeek.executeUpdate();
            int monthResult = pstmtMonth.executeUpdate();
            int yearResult = pstmtYear.executeUpdate();

            if (weekResult > 0 || monthResult > 0 || yearResult > 0) {
                System.out.println("베스트셀러 테이블이 업데이트되었습니다.");
            } else {
                System.out.println("베스트셀러 테이블에 업데이트할 데이터가 없습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
