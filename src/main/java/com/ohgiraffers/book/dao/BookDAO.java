//package com.ohgiraffers.book.dao;
//
//import com.ohgiraffers.book.dto.BookDTO;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//import static com.ohgiraffers.common.JDBCTemplate.*;
//
//public class BookDAO {
//
//    private Properties prop;
//
//    public BookDAO() {
//        prop = new Properties();
//        try {
//            prop.loadFromXML(getClass().getResourceAsStream("/mapper/book-query.xml"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 도서 추가
//    public int insertBook(Connection con, BookDTO bookDTO) {
//        PreparedStatement pstmt = null;
//        int result = 0;
//        String query = prop.getProperty("insertBook");
//
//        try {
//            pstmt = con.prepareStatement(query);
//
//            pstmt.setString(1, bookDTO.getBookTitle());
//            pstmt.setString(2, bookDTO.getBookAuthor());
//            pstmt.setString(3, bookDTO.getBookPublisher());
//            pstmt.setString(4, bookDTO.getBookGenre());
//            pstmt.setBoolean(5, bookDTO.isBookStatus());
//            pstmt.setInt(6, bookDTO.getBorrowCount());  // borrow_count 설정
//
//            result = pstmt.executeUpdate();
//
//        } catch (SQLException | NullPointerException e) {
//            System.out.println("잘못된 값이 입력됨...");
//            e.printStackTrace();
//        } finally {
//            close(pstmt);
//        }
//        return result;
//    }
//
//    // 도서 수정
//    public int updateBook(Connection con, BookDTO bookDTO) {
//        PreparedStatement pstmt = null;
//        int result = 0;
//        String query = prop.getProperty("updateBook");
//
//        try {
//            pstmt = con.prepareStatement(query);
//
//            pstmt.setString(1, bookDTO.getBookTitle());
//            pstmt.setString(2, bookDTO.getBookAuthor());
//            pstmt.setString(3, bookDTO.getBookPublisher());
//            pstmt.setString(4, bookDTO.getBookGenre());
//            pstmt.setBoolean(5, bookDTO.isBookStatus());
//            pstmt.setInt(6, bookDTO.getBorrowCount());  // borrow_count 설정
//            pstmt.setInt(7, bookDTO.getBookCode());
//
//            result = pstmt.executeUpdate();
//
//        } catch (SQLException | NullPointerException e) {
//            System.out.println("잘못된 값이 입력됨...");
//            e.printStackTrace();
//        } finally {
//            close(pstmt);
//        }
//        return result;
//    }
//
//    // 도서 삭제
//    public int deleteBook(Connection con, int bookCode) {
//        PreparedStatement pstmt = null;
//        int result = 0;
//        String query = prop.getProperty("deleteBook");
//
//        try {
//            pstmt = con.prepareStatement(query);
//            pstmt.setInt(1, bookCode);
//
//            result = pstmt.executeUpdate();
//
//        } catch (SQLException | NullPointerException e) {
//            System.out.println("잘못된 값이 입력됨...");
//            e.printStackTrace();
//        } finally {
//            close(pstmt);
//        }
//        return result;
//    }
//
//    // 도서 ID로 도서 검색
//    public BookDTO getBookById(Connection con, int bookCode) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        BookDTO bookDTO = null;
//        String query = prop.getProperty("getBookById");
//
//        try {
//            pstmt = con.prepareStatement(query);
//            pstmt.setInt(1, bookCode);
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                bookDTO = new BookDTO(
//                        rs.getInt("book_code"),
//                        rs.getString("book_title"),
//                        rs.getString("book_author"),
//                        rs.getString("book_publisher"),
//                        rs.getString("book_genre"),
//                        rs.getBoolean("book_status"),
//                        rs.getInt("borrow_count")  // borrow_count 추가
//                );
//            }
//
//        } catch (SQLException | NullPointerException e) {
//            System.out.println("잘못된 값이 입력됨...");
//            e.printStackTrace();
//        } finally {
//            close(rs);
//            close(pstmt);
//        }
//        return bookDTO;
//    }
//
//    // 도서 제목으로 도서 검색
//    public List<BookDTO> searchBooksByTitle(Connection con, String title) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        List<BookDTO> books = new ArrayList<>();
//        String query = prop.getProperty("searchBooksByTitle");
//
//        try {
//            pstmt = con.prepareStatement(query);
//            pstmt.setString(1, "%" + title + "%");
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                BookDTO bookDTO = new BookDTO(
//                        rs.getInt("book_code"),
//                        rs.getString("book_title"),
//                        rs.getString("book_author"),
//                        rs.getString("book_publisher"),
//                        rs.getString("book_genre"),
//                        rs.getBoolean("book_status"),
//                        rs.getInt("borrow_count")  // borrow_count 추가
//                );
//                books.add(bookDTO);
//            }
//
//        } catch (SQLException | NullPointerException e) {
//            System.out.println("잘못된 값이 입력됨...");
//            e.printStackTrace();
//        } finally {
//            close(rs);
//            close(pstmt);
//        }
//        return books;
//    }
//
//    // 카테고리로 도서 검색
//    public List<BookDTO> searchBooksByCategory(Connection con, int categoryId) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        List<BookDTO> books = new ArrayList<>();
//        String query = prop.getProperty("searchBooksByCategory");
//
//        try {
//            pstmt = con.prepareStatement(query);
//            pstmt.setInt(1, categoryId);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                BookDTO bookDTO = new BookDTO(
//                        rs.getInt("book_code"),
//                        rs.getString("book_title"),
//                        rs.getString("book_author"),
//                        rs.getString("book_publisher"),
//                        rs.getString("book_genre"),
//                        rs.getBoolean("book_status"),
//                        rs.getInt("borrow_count")  // borrow_count 추가
//                );
//                books.add(bookDTO);
//            }
//
//        } catch (SQLException | NullPointerException e) {
//            System.out.println("잘못된 값이 입력됨...");
//            e.printStackTrace();
//        } finally {
//            close(rs);
//            close(pstmt);
//        }
//        return books;
//    }
//
//    // 연체된 도서 목록 검색
//    public List<BookDTO> getOverdueBooks(Connection con) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        List<BookDTO> books = new ArrayList<>();
//        String query = prop.getProperty("getOverdueBooks");
//
//        try {
//            pstmt = con.prepareStatement(query);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                BookDTO bookDTO = new BookDTO(
//                        rs.getInt("book_code"),
//                        rs.getString("book_title"),
//                        rs.getString("book_author"),
//                        rs.getString("book_publisher"),
//                        rs.getString("book_genre"),
//                        rs.getBoolean("book_status"),
//                        rs.getInt("borrow_count")  // borrow_count 추가
//                );
//                books.add(bookDTO);
//            }
//
//        } catch (SQLException | NullPointerException e) {
//            System.out.println("잘못된 값이 입력됨...");
//            e.printStackTrace();
//        } finally {
//            close(rs);
//            close(pstmt);
//        }
//        return books;
//    }
//}