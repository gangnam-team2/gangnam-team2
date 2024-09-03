package com.ohgiraffers.manager.dao;

import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.user.dto.UserDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class ManagerDAO {

    private Properties prop = new Properties();

    public ManagerDAO(String url){
        try {
            prop.loadFromXML(new FileInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserDTO> selectAllMembersInfo(Connection con){

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<UserDTO> allMembersInfolist = new ArrayList<>();
        String query = prop.getProperty("selectAllMembersInfo");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();
            while(rset.next()){
                UserDTO memberInfo = new UserDTO();
                memberInfo.setUserId(rset.getString("user_id"));
                memberInfo.setUserName(rset.getString("user_name"));
                memberInfo.setUserRole(rset.getBoolean("user_role"));
                memberInfo.setUserCreatedAt(rset.getTimestamp("user_created_at"));
                memberInfo.setUserUpdatedAt(rset.getTimestamp("user_updated_at"));

                allMembersInfolist.add(memberInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
        return allMembersInfolist;
    }

    public Map<String, List<BookDTO>> selectAllBooksInfo(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<UserDTO> userId =new ArrayList<>();
        List<BookDTO> allBooksInfolist = new ArrayList<>();
        Map<String, List<BookDTO>> userBooks = new HashMap<>();


        String query = prop.getProperty("selectBookList");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setBoolean(1, false);  // 대여 가능한 책 목록 조회
            rset = pstmt.executeQuery();

            String a="";

            while (rset.next()) {
                BookDTO bookInfo = new BookDTO();

                a = rset.getString("user_id");

                bookInfo.setBookCode(rset.getInt("book_code"));
                bookInfo.setBookTitle(rset.getString("book_title"));
                bookInfo.setBookAuthor(rset.getString("book_author"));
                bookInfo.setBookPublisher(rset.getString("book_publisher"));
                bookInfo.setBookGenre(rset.getString("book_genre"));

                // bookInfo.setUserInfo(userInfo);  // 책 정보에 사용자 정보 추가 불가

                allBooksInfolist.add(bookInfo);


            }

            userBooks.put(a, allBooksInfolist);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 리소스 해제 시 null 체크 추가
            if (rset != null) {
                try {
                    rset.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userBooks;
    }

    public List<BorrowRecordDTO> selectMemberHistoy(Connection con){
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<BorrowRecordDTO> lateMember = new ArrayList<>();
        String query = prop.getProperty("selectMemberHistoy");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            while(rset.next()){
                BorrowRecordDTO record = new BorrowRecordDTO();
                record.setUserId(String.valueOf(rset.getInt("user_id")));

                lateMember.add(record);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
        return lateMember;
    }



}
