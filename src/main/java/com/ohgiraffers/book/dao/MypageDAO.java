package com.ohgiraffers.book.dao;

import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import java.sql.*;
import java.util.*;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class MypageDAO {

    private Properties prop = new Properties();

    public MypageDAO(String url) {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/mypage-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int updateRequestBook(Connection con, BorrowRecordDTO borrowRecordDTO,boolean bookStatus){
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("updateRequestBook");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,borrowRecordDTO.getBookCode());
            pstmt.setDate(2, new java.sql.Date(borrowRecordDTO.getBorrowDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(borrowRecordDTO.getDueDate().getTime()));
            pstmt.setBoolean(4,bookStatus);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }return result;

    }





}
