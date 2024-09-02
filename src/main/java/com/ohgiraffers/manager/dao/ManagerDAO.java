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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    public List<BookDTO> selectAllBooksInfo(Connection con){
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<BookDTO> allBooksInfolist = new ArrayList<>();
        String query = prop.getProperty("selectBookList");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setBoolean(1, false); // 대여 가능한 책 목록 조회. 트루면 대여 중, 펄스면 대여 가능.
            rset = pstmt.executeQuery();

            while(rset.next()){
                BookDTO bookInfo = new BookDTO();
                UserDTO userInfo = new UserDTO();
                // 사용자 아이디도 출력해야 하는데, 쿼리문에 조인을 어떻게 써야할 지 모르겠다...
                userInfo.setUserId(rset.getString("user_id"));
                bookInfo.setBookCode(rset.getInt("book_code"));
                bookInfo.setBookTitle(rset.getString("book_title"));
                bookInfo.setBookAuthor(rset.getString("book_author"));
                bookInfo.setBookPublisher(rset.getString("book_publisher"));
                bookInfo.setBookGenre(rset.getString("book_genre"));

                allBooksInfolist.add(bookInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }
        return allBooksInfolist;
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
                record.setUserId(rset.getInt("user_id"));

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
