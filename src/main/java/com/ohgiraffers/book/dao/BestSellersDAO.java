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
    private Properties prop;

    public BestSellersDAO() {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/bestSellers-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BookDTO> selectBestSellersByPeriod(Connection con, String period) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> bestSellers = new ArrayList<>();

        String periodInterval;
        switch (period) {
            case "이번주":
                periodInterval = "WEEK";
                break;
            case "이번달":
                periodInterval = "MONTH";
                break;
            case "올해":
                periodInterval = "YEAR";
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        String query = prop.getProperty("selectBestSellersByPeriod").replace("#{period}", periodInterval);

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

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
        } finally {
            close(rs);
            close(pstmt);
        }

        return bestSellers;
    }
}
