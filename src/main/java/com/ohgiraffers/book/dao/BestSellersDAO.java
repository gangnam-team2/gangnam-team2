package com.ohgiraffers.book.dao;

import com.ohgiraffers.book.dto.BestSellersDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class BestSellersDAO {
    private Properties prop;

    public BestSellersDAO() {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/best-sellers-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 베스트셀러 추가
    public int insertBestSeller(Connection con, BestSellersDTO bestSellersDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertBestSeller");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bestSellersDTO.getBookCode());
            pstmt.setInt(2, bestSellersDTO.getBorrowCount());
            pstmt.setString(3, bestSellersDTO.getPeriod());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    // 특정 기간의 베스트셀러 조회
    public List<BestSellersDTO> selectBestSellersByPeriod(Connection con, String period) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BestSellersDTO> bestSellersList = new ArrayList<>();
        String query = prop.getProperty("selectBestSellersByPeriod");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, period);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BestSellersDTO bestSellersDTO = new BestSellersDTO(
                        rs.getInt("best_seller_id"),
                        rs.getInt("book_code"),
                        rs.getInt("borrow_count"),
                        rs.getString("period"),
                        rs.getTimestamp("created_at")
                );
                bestSellersList.add(bestSellersDTO);
            }

        } catch (SQLException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return bestSellersList;
    }

    // 모든 베스트셀러 조회
    public List<BestSellersDTO> selectAllBestSellers(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BestSellersDTO> bestSellersList = new ArrayList<>();
        String query = prop.getProperty("selectAllBestSellers");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BestSellersDTO bestSellersDTO = new BestSellersDTO(
                        rs.getInt("best_seller_id"),
                        rs.getInt("book_code"),
                        rs.getInt("borrow_count"),
                        rs.getString("period"),
                        rs.getTimestamp("created_at")
                );
                bestSellersList.add(bestSellersDTO);
            }

        } catch (SQLException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return bestSellersList;
    }
}
