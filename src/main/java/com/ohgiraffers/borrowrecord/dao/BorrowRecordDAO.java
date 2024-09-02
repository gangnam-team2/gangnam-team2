//package com.ohgiraffers.borrowrecord.dao;
//
//import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.Properties;
//
//import static com.ohgiraffers.common.JDBCTemplate.*;
//
//
//public class BorrowRecordDAO {
//
//    private Properties prop = new Properties();
//
//    public BorrowRecordDAO() {
//        prop = new Properties();
//        try {
//            prop.loadFromXML(getClass().getResourceAsStream("/mapper/mypage-query.xml"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void showBookList(Connection con, BorrowRecordDTO borrowRecordDTO){
//
//        Statement stmt = null;
//        ResultSet rs = null;
//        String query = "showBookList";
//
//        try {
//            stmt =con.createStatement();
//            rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " +rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            close(rs);
//            close(stmt);
//            close(con);
//        }
//    }
//
//
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
//                pstmt.setString(3, borrowRecordDTO.getBorrowDate());
//                pstmt.setString(4, borrowRecordDTO.getDueDate());
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
//
//
//    public int returnBook(Connection con, BorrowRecordDTO borrowRecordDTO){
//        PreparedStatement pstmt = null;
//        int result = 0;
//        String query = prop.getProperty("returnBook");
//
//        try {
//            pstmt =con.prepareStatement(query);
//            pstmt.setInt(1,borrowRecordDTO.getBookCode());
//            pstmt.setDate(2,borrowRecordDTO.getReturnDate());
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            close(pstmt);
//            close(con);
//        }return result;
//    }
//
//
//    public int overDueBook(Connection con, BorrowRecordDTO borrowRecordDTO){
//
//        PreparedStatement pstmt = null;
//        int result = 0;
//        String query = prop.getProperty("overDueBook");
//        LocalDate currentDate = LocalDate.now();
//        if (currentDate.isAfter(borrowRecordDTO.getDueDate())){
//            try {
//                pstmt = con.prepareStatement(query);
//                pstmt.setInt(1,borrowRecordDTO.getBookCode());
//                pstmt.setString(2,borrowRecordDTO.getUserId());
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }finally {
//                close(con);
//                close(pstmt);
//             }
//
//        }
//        return result;
//    }
//
//
//}
//
//
//
