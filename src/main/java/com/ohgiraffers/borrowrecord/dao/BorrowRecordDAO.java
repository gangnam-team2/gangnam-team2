package com.ohgiraffers.borrowrecord.dao;

import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.*;


public class BorrowRecordDAO {

    private Properties prop = new Properties();

    public BorrowRecordDAO() {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/borrowBook-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void showBookList(Connection con, BorrowRecordDTO borrowRecordDTO){
//
//        Statement stmt = null;
//        ResultSet rs = null;
//        String query = prop.getProperty("showBookList");
//        try {
//            stmt =con.createStatement();
//            rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + " "
//                                    + rs.getString(2) + " "
//                                    + rs.getString(3) + " "
//                                    + rs.getString(4) + " "
//                                    + rs.getString(5) );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            close(rs);
//            close(stmt);
//            close(con);
//        }
//    }


//    public int rentBook(Connection con, BorrowRecordDTO borrowRecordDTO){
//
//        PreparedStatement pstmt = null;
//        int result = 0;
//        String query = prop.getProperty("rentBook");
//        LocalDate currentDate = LocalDate.now();
//        if (currentDate.isAfter(borrowRecordDTO.getBorrowDate())) {
//            try {
//                pstmt = con.prepareStatement(query);
//                pstmt.setInt(1, borrowRecordDTO.getBookCode());
//                pstmt.setString(2, borrowRecordDTO.getUserId());
//                pstmt.setDate(3, Date.valueOf(borrowRecordDTO.getBorrowDate()));
//
//                result = pstmt.executeUpdate();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } finally {
//                close(pstmt);
//                close(con);
//            }
//        }
//        return result;
//
//
//    }


//    public int returnBook(Connection con, BorrowRecordDTO borrowRecordDTO){
//        PreparedStatement pstmt = null;
//        int result = 0;
//        String query = prop.getProperty("returnBook");
//
//        try {
//            pstmt =con.prepareStatement(query);
//            pstmt.setInt(1,borrowRecordDTO.getBookCode());
//            pstmt.setDate(2, Date.valueOf(borrowRecordDTO.getReturnDate()));
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            close(pstmt);
//            close(con);
//        }return result;
//    }


    public int overDueBook(Connection con, BorrowRecordDTO borrowRecordDTO){

        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("overDueBook");
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(borrowRecordDTO.getDueDate())){
            try {
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1,borrowRecordDTO.getBookCode());
                pstmt.setString(2,borrowRecordDTO.getUserId());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                close(con);
                close(pstmt);
             }
        }
        return result;
    }

    // 책 대여 메소드
    public int rentBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("rentBook"); // 쿼리 확인

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, borrowRecordDTO.getBookCode());
            pstmt.setString(2, borrowRecordDTO.getUserId());
            pstmt.setDate(3, Date.valueOf(borrowRecordDTO.getBorrowDate()));
            
            // dueDate는 Mysql에서 자동으로 추가 되니까 따로 추가 ㄴㄴ

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    // 책 반납 메서드
    public int returnBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("returnBook");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setDate(1, Date.valueOf(borrowRecordDTO.getReturnDate()));
            pstmt.setInt(2, borrowRecordDTO.getBookCode());
            pstmt.setString(3, borrowRecordDTO.getUserId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    // 사용자가 대여 중인 책 목록을 가져오는 메서드
    public List<BorrowRecordDTO> getBorrowedBooksByUser(Connection con, String userId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BorrowRecordDTO> borrowedBooks = new ArrayList<>();
        String query = prop.getProperty("getBorrowedBooksByUser");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BorrowRecordDTO record = new BorrowRecordDTO();
                record.setBookCode(rs.getInt("book_code"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());
                borrowedBooks.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return borrowedBooks;
    }

    // 대여 가능한 책 목록을 가져오는 메서드
    public List<BookDTO> getAvailableBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> availableBooks = new ArrayList<>();
        String query = prop.getProperty("getAvailableBooks");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO book = new BookDTO();
                book.setBookCode(rs.getInt("book_code"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookGenre(rs.getString("book_genre"));
                availableBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return availableBooks;
    }
}



