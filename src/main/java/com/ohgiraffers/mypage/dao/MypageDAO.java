package com.ohgiraffers.mypage.dao;

import com.ohgiraffers.book.dto.BorrowRecordDTO;
import com.ohgiraffers.book.dto.UserDTO;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;

import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class MypageDAO {

    private Properties prop = new Properties();

    public MypageDAO() {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/mypage-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int updateRequest1(Connection con, BorrowRecordDTO borrowRecordDTO){
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("updateRequest1");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,borrowRecordDTO.getBookCode());
            pstmt.setDate(2, borrowRecordDTO.getBorrowDate());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }return result;

    }

    public int updateRequest2(Connection con, BorrowRecordDTO borrowRecordDTO){
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("updateRequest2");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,borrowRecordDTO.getBookCode());
            pstmt.setDate(2, borrowRecordDTO.getDueDate());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }return result;

    }

    public int updateRequest3(Connection con, BorrowRecordDTO borrowRecordDTO){
       PreparedStatement pstmt = null;
        int result = 0;
       String query = prop.getProperty("updateRequest3");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,borrowRecordDTO.getBookCode());
            pstmt.setBoolean(2,true);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
       }return result;
    }


    public List<BorrowRecordDTO> currentBorrowBooks(Connection con, BorrowRecordDTO borrowRecordDTO){
        Statement stmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("currentBorrowBooks");
        List<BorrowRecordDTO> currentBorrowBooks = new ArrayList<>();

        try {
            stmt = con.createStatement();

            rset = stmt.executeQuery(query);

            while (rset.next()) {
                BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO(
                        rset.getInt("book_code"),
                        rset.getString("book_title"),
                       rset.getDate("borrow_date"),
                        rset.getDate("due_date"),
                        rset.getDate("returnDate"));
                currentBorrowBooks.add(borrowRecordDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(stmt);
            close(rset);
        }
        return currentBorrowBooks;
    }


    public List<BorrowRecordDTO> allBorrowBookList(Connection con){
        Statement stmt = null;
        ResultSet rset = null;
       String query = prop.getProperty("AllBorrowBookList");
        List<BorrowRecordDTO> allBorrowBookList = new ArrayList<>();

       try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            while (rset.next()) {
               BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO(
                        rset.getInt("book_code"),
                        rset.getString("book_title"),
                        rset.getDate("borrow_date"),
                        rset.getDate("due_date"),
                        rset.getDate("returnDate")
                );
                allBorrowBookList.add(borrowRecordDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(stmt);
            close(rset);
        }
        return allBorrowBookList;
   }


    public int pwdUpdate (Connection con, UserDTO userDTO, String changepwd){
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("pwdUpdate");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,userDTO.getUserPwd());
            pstmt.setString(2,changepwd);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
       }finally {
            close(con);
            close(pstmt);
        }return result;
    }
}
