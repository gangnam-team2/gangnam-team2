package com.ohgiraffers.request.dao;

import com.ohgiraffers.book.dao.BookDAO;
import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.common.JDBCTemplate;
import com.ohgiraffers.request.dto.RequestDTO;

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


public class RequestDAO
{
    private Properties prop = new Properties();

    public RequestDAO() {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/request-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public int insertRequestedBook(Connection con, RequestDTO requestDTO) throws SQLException {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertRequestedBook");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, requestDTO.getUserId());
            pstmt.setString(2, requestDTO.getBookTitle());
            pstmt.setString(3, requestDTO.getBookAuthor());
            pstmt.setString(4, requestDTO.getBookPublisher());

            // book_genre가 없으면 기본값 '미정'을 설정
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

//    public RequestDAO() {
//        prop = new Properties();
//        try {
//            prop.loadFromXML(getClass().getResourceAsStream("/mapper/request-query.xml"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return requestedBooks;
    }
    public void addRequestedBook(RequestDTO requestedBook) {
        Connection con = null;
        try {
            con = JDBCTemplate.getConnection();

            // 도서 추가
            BookDTO newBook = new BookDTO();
            newBook.setBookTitle(requestedBook.getBookTitle());
            newBook.setBookAuthor(requestedBook.getBookAuthor());
            newBook.setBookPublisher(requestedBook.getBookPublisher());
            newBook.setBookGenre(requestedBook.getBookGenre() != null ? requestedBook.getBookGenre() : "미정");
            newBook.setBookStatus(true);

            // 로그 추가 - 디버깅용
            System.out.println("Request ID: " + requestedBook.getRequestId());

            BookDAO bookDAO = new BookDAO();
            RequestDAO requestDAO = new RequestDAO();
            bookDAO.insertBookIntoDB(con, newBook);

            // 요청된 도서 삭제
            boolean isDeleted = requestDAO.deleteRequest(con, requestedBook.getRequestId());

            if (isDeleted) {
                System.out.println("요청된 도서가 목록에서 삭제되었습니다.");
            } else {
                System.out.println("요청된 도서 삭제에 실패하였습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close(); // 커넥션 종료
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    public boolean deleteRequest(Connection con, int requestId) throws SQLException {
        PreparedStatement pstmt = null;
        String query = prop.getProperty("deleteRequestedBook");
        boolean isDeleted = false;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestId);
            int result = pstmt.executeUpdate();
            isDeleted = result > 0;
            // 트랜잭션 처리
            con.commit(); // 커밋
        } catch (SQLException e) {
            con.rollback(); // 롤백
            throw e; // 예외를 다시 던짐
        } finally {
            close(pstmt);
        }

        return isDeleted;
    }

}
