package com.ohgiraffers.mypage.dao;

import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.user.dto.UserDTO;

import java.sql.*;
import java.sql.Date;
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
            pstmt.setDate(2, Date.valueOf(borrowRecordDTO.getBorrowDate()));

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
            pstmt.setBoolean(2,true);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
       }return result;
    }


    public void currentBorrowBooks(Connection con, BorrowRecordDTO borrowRecordDTO){
        Statement stmt = null;
        ResultSet rset = null;
        String query = "SELECT\n" +
                "            BOOK_CODE, BOOK_TITLE, BORROW_DATE, DUE_DATE\n" +
                "          FROM BORROW_RECORDS\n" +
                "        WHERE USER_ID = '" +borrowRecordDTO.getUserId() + "' AND BOOK_STATUS = 'FALSE';";

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            while (rset.next()) {
                System.out.println(rset.getInt(1)+ " " + rset.getString(2)+ " "
                        + rset.getDate(3)+ " " + rset.getDate(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(stmt);
            close(rset);
        }
    }


    public void allBorrowBookList(Connection con, BorrowRecordDTO borrowRecordDTO){
        Statement stmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("allBorrowBookList");


       try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            while (rset.next()) {
                System.out.println(rset.getInt(1)+ " " + rset.getString(2)+ " "+rset.getDate(3)+" "+rset.getDate(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(stmt);
            close(rset);
        }
   }


    public int pwdUpdate (Connection con, UserDTO userDTO, String changePwd){
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("pwdUpdate");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,userDTO.getUserPwd());
            pstmt.setString(2,changePwd);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
       }finally {
            close(con);
            close(pstmt);
        }return result;
    }
}
