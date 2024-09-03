package com.ohgiraffers.book.dao;

import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.common.JDBCTemplate;
import com.ohgiraffers.request.controller.RequestController;
import com.ohgiraffers.request.dao.RequestDAO;
import com.ohgiraffers.request.dto.RequestDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class BookDAO {

    private Properties prop;

    public BookDAO() {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/book-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 도서 추가
    public int insertBook(Connection con, BookDTO bookDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertBook");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, bookDTO.getBookTitle());
            pstmt.setString(2, bookDTO.getBookAuthor());
            pstmt.setString(3, bookDTO.getBookPublisher());
            pstmt.setString(4, bookDTO.getBookGenre());
            pstmt.setBoolean(5, bookDTO.isBookStatus());
            pstmt.setInt(6, bookDTO.getBorrowCount());

            result = pstmt.executeUpdate();

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    // 도서 수정
    public int updateBook(Connection con, BookDTO bookDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("updateBook");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, bookDTO.getBookTitle());
            pstmt.setString(2, bookDTO.getBookAuthor());
            pstmt.setString(3, bookDTO.getBookPublisher());
            pstmt.setString(4, bookDTO.getBookGenre());
            pstmt.setBoolean(5, bookDTO.isBookStatus());
            pstmt.setInt(6, bookDTO.getBorrowCount());
            pstmt.setInt(7, bookDTO.getBookCode());

            result = pstmt.executeUpdate();

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    // 도서 삭제
    public int deleteBook(Connection con, int bookCode) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("deleteBook");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookCode);

            result = pstmt.executeUpdate();

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    // 도서 코드로 도서 가져오기
    public BookDTO getBookById(Connection con, int bookCode) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BookDTO bookDTO = null;
        String query = prop.getProperty("getBookByCode");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bookDTO = new BookDTO(
                        rs.getInt("book_code"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("book_publisher"),
                        rs.getString("book_genre"),
                        rs.getBoolean("book_status"),
                        rs.getInt("borrow_count")
                );
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return bookDTO;
    }

    // 도서 코드로 도서 검색
    public BookDTO searchBooksByCode(Connection con, int bookCode) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BookDTO bookDTO = null;
        String query = prop.getProperty("searchBooksByCode");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bookDTO = new BookDTO(
                        rs.getInt("book_code"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("book_publisher"),
                        rs.getString("book_genre"),
                        rs.getBoolean("book_status"),
                        rs.getInt("borrow_count")
                );
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return bookDTO;
    }

    // 도서 제목으로 도서 검색
    public List<BookDTO> searchBooksByTitle(Connection con, String title) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> books = new ArrayList<>();
        String query = prop.getProperty("searchBooksByTitle");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + title + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO bookDTO = new BookDTO(
                        rs.getInt("book_code"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("book_publisher"),
                        rs.getString("book_genre"),
                        rs.getBoolean("book_status"),
                        rs.getInt("borrow_count")
                );
                books.add(bookDTO);
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return books;
    }

    // 연체된 도서 목록 검색
    public List<BookDTO> getOverdueBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> books = new ArrayList<>();
        String query = prop.getProperty("getOverdueBooks");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO bookDTO = new BookDTO(
                        rs.getInt("book_code"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("book_publisher"),
                        rs.getString("book_genre"),
                        rs.getBoolean("book_status"),
                        rs.getInt("borrow_count")
                );
                books.add(bookDTO);
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력됨...");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return books;
    }


    public void insertBookIntoDB(Connection con, BookDTO bookDTO) throws SQLException {
        con = null;
        BookDAO bookDAO = new BookDAO();
        int result = 0;
        try {
            result = bookDAO.insertBook(con, bookDTO);
        } finally {
            close(con);
        }

        if (result > 0) {
            System.out.println("도서가 추가되었습니다.");
        } else {
            System.out.println("도서 추가에 실패하였습니다.");
        }
    }

    // 대여 가능한 책 리스트를 가져오는 메서드
    // !!!! 근데 로그인한 아이디에서 대여한건지 검증 필요 !!!!
    public List<BookDTO> getAvailableBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> availableBooks = new ArrayList<>();
        String query = prop.getProperty("getAvailableBooks");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setBookCode(rs.getInt("book_code"));
                bookDTO.setBookTitle(rs.getString("book_title"));
                bookDTO.setBookAuthor(rs.getString("book_author"));
                bookDTO.setBookPublisher(rs.getString("book_publisher"));
                bookDTO.setBookGenre(rs.getString("book_genre"));

                availableBooks.add(bookDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return availableBooks;
    }

}