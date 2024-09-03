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
            pstmt.setBoolean(2,true);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }return result;
    }


    public void currentBorrowBooks(Connection con, BorrowRecordDTO borrowRecordDTO, UserDTO userDTO){

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("currentBorrowBooks");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userDTO.getUserId());
            rset = pstmt.executeQuery();

            while (rset.next()) {
                System.out.println("북코드: " + rset.getInt(1)+ " " +"제목: " + rset.getString(2)+ " "
                        +"대여 날짜: " + rset.getDate(3)+ " " +"반납 예정일: " + rset.getDate(4));
            }

            if (borrowRecordDTO.isOverDueBooks() == true){
                System.out.println("현재 연체 된 책이 있습니다.");
                overDueBooks(getConnection(), borrowRecordDTO, userDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
    }

    public void overDueBooks(Connection con, BorrowRecordDTO borrowRecordDTO, UserDTO userDTO){
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("overDueBooks");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userDTO.getUserId());
            rset = pstmt.executeQuery();

            while (rset.next()) {
                System.out.println("북코드: " + rset.getInt(1)+ " "+ "제목: " + rset.getString(2) + " " + "대여일: " + rset.getDate(3)+ " " + "반납 예정일: " + rset.getDate(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void allBorrowBookList(Connection con, BorrowRecordDTO borrowRecordDTO,UserDTO userDTO){
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("allBorrowBookList");

       try {

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userDTO.getUserId());
            rset = pstmt.executeQuery();

            while (rset.next()) {
                System.out.println("북코드: " + rset.getInt(1)+ " "+ "제목: " + rset.getString(2) + " " + "대여일: " + rset.getDate(3)+ " " + "반납 예정일: " + rset.getDate(4) + " " + "실제 반납일: "+ rset.getDate(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
    }


    public int pwdUpdate (Connection con, UserDTO userDTO, String changePwd){
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("pwdUpdate");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(2,userDTO.getUserPwd());
            pstmt.setString(1,changePwd);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }return result;
    }
}
