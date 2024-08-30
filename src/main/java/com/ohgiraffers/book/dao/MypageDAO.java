package com.ohgiraffers.book.dao;

import com.ohgiraffers.book.dto.BorrowRecordDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class MypageDAO {

    private Properties prop = new Properties();

    public MypageDAO(String url) {
        try {
            prop.loadFromXML(new FileInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public int updateRequestBook(Collection con, BorrowRecordDTO borrowRecordDTO,boolean bookStatus){
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
