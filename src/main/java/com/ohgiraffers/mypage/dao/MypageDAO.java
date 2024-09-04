package com.ohgiraffers.mypage.dao;

import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.user.dto.UserDTO;
import com.ohgiraffers.usersession.UserSession;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class MypageDAO {

    private Properties prop = new Properties();

    public MypageDAO() {
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/mypage-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int updateRequest(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("updateRequest");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, borrowRecordDTO.getBookCode());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }

    public List<BorrowRecordDTO> currentBorrowBooks(Connection con, String userId) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<BorrowRecordDTO> borrowedBooks = new ArrayList<>();
        String query = prop.getProperty("currentBorrowBooks");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                BorrowRecordDTO book = new BorrowRecordDTO();
                book.setBookCode(rset.getInt("book_code"));
                book.setBookTitle(rset.getString("book_title"));
                book.setBorrowDate(rset.getDate("borrow_date"));
                book.setDueDate(rset.getDate("due_date"));
                borrowedBooks.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
        }
        return borrowedBooks;
    }




    public void myOverDueBooks(Connection con, BorrowRecordDTO borrowRecordDTO, UserDTO userDTO) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = prop.getProperty("myOverDueBooks");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userDTO.getUserId());
            rs = pstmt.executeQuery();

            if (rs != null && rs.next()) {
                do {
                    System.out.printf("북코드: %d | 제목: %s | 대여일: %s | 반납 예정일: %s\n",
                            rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4));
                } while (rs.next());
            } else {
                System.out.println("연체된 책이 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    public void allBorrowBookList(Connection con, BorrowRecordDTO borrowRecordDTO, UserDTO userDTO) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("allBorrowBookList");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userDTO.getUserId());
            rset = pstmt.executeQuery();

            if (rset != null && rset.next()) {
                do {
                    System.out.printf("북코드: %d | 제목: %s | 대여일: %s | 반납 예정일: %s | 실제 반납일: %s\n",
                            rset.getInt(1), rset.getString(2),
                            rset.getDate(3), rset.getDate(4), rset.getDate(5));
                } while (rset.next());
            } else {
                System.out.println("대여 목록이 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
        }
    }

    public int pwdUpdate(Connection con, UserDTO userDTO, String changePwd) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("pwdUpdate");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, changePwd);
            pstmt.setString(2, userDTO.getUserPwd());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }
}
