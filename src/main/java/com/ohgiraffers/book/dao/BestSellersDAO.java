package com.ohgiraffers.book.dao;

import com.ohgiraffers.book.dto.BestSellersDTO;
import com.ohgiraffers.book.dto.BookDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class BestSellersDAO {
    public List<BookDTO> selectTopBorrowedBooksByPeriod(Connection con, String period) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> bestSellersList = new ArrayList<>();

        String query = "SELECT b.book_code, b.book_title, b.borrow_count " +
                "FROM books b " +
                "JOIN borrow_records br ON b.book_code = br.book_code " +
                "WHERE br.borrow_date >= DATE_SUB(NOW(), INTERVAL 1 " + period + ") " +
                "GROUP BY b.book_code " +
                "ORDER BY b.borrow_count DESC " +
                "LIMIT 10";

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO book = new BookDTO();
                book.setBookCode(rs.getInt("book_code"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBookBorrowCount(rs.getInt("borrow_count"));

                bestSellersList.add(book);
            }

        } catch (SQLException e) {
            System.out.println("베스트셀러 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return bestSellersList;
    }
}
