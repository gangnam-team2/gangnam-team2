package com.ohgiraffers.request.dao;

import com.ohgiraffers.book.dao.BookDAO;
import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.common.JDBCTemplate;
import com.ohgiraffers.request.dto.RequestDTO;
import com.ohgiraffers.usersession.UserSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;


public class RequestDAO {

    private Properties prop = new Properties();

    public RequestDAO() {
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/request-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 요청된 도서를 추가하는 메서드*/
    public int insertRequestedBook(Connection con, RequestDTO requestDTO) throws SQLException {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertRequestedBook");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, UserSession.getUserDTO().getUserId());
            pstmt.setString(2, requestDTO.getBookTitle());
            pstmt.setString(3, requestDTO.getBookAuthor());
            pstmt.setString(4, requestDTO.getBookPublisher());

            if (requestDTO.getBookGenre() == null || requestDTO.getBookGenre().isEmpty()) {
                pstmt.setString(5, "미정");
            } else {
                pstmt.setString(5, requestDTO.getBookGenre());
            }

            result = pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }

        return result;
    }

    /** 요청된 도서를 가져오는 메서드*/
    public List<RequestDTO> getRequestedBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<RequestDTO> requestedBooks = new ArrayList<>();

        String query = prop.getProperty("getRequestedBooks");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RequestDTO book = new RequestDTO();
                book.setRequestId(rs.getInt("request_id")); // request_id 필드 설정
                book.setBookTitle(rs.getString("book_title"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookPublisher(rs.getString("book_publisher"));
                book.setRequestStatus(rs.getBoolean("requests_status"));
                book.setCreatedAt(rs.getTimestamp("created_at"));
                book.setBookGenre(rs.getString("book_genre"));

                requestedBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }

        return requestedBooks;
    }

    /** 요청된 도서를 삭제하는 메서드*/
    public boolean deleteRequest(Connection con, int requestId) throws SQLException {
        PreparedStatement pstmt = null;
        String query = prop.getProperty("deleteRequestedBook");
        boolean isDeleted = false;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestId);
            int result = pstmt.executeUpdate();
            isDeleted = result > 0;
        } catch (SQLException e) {
            System.out.println("요청된 도서를 삭제하는데 문제가 생겼습니다.");
        } finally {
            close(pstmt);
        }

        return isDeleted;
    }
}
