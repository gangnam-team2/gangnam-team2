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

    /** 모든 회원 정보를 출력하는 메서드*/
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



    /*public Map<String, List<BookDTO>> apthem (String a, List<BookDTO> b){

        // a 가 중복되면 b에 정보가 쌓이도록..

        Map<String, List<BookDTO>> map = new HashMap<>();
        if (a==a) {
            map.put(a,new ArrayList<>(b));
        }else {
        }
        return null;
    }*/

    /** 대여 가능한 도서 목록을 출력하는 메서드*/
    public List<BorrowRecordDTO> selectBooksAndUser(Connection con) {


        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<BorrowRecordDTO> borrowRecords = new ArrayList<>();
        String query = prop.getProperty("selectBookList");

        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();

            while(rset.next()){
                BorrowRecordDTO borrowRecord = new BorrowRecordDTO();
                borrowRecord.setUserId(rset.getString("user_id"));
                borrowRecord.setBookCode(rset.getInt("book_code"));
                borrowRecord.setBookTitle(rset.getString("book_title"));
                borrowRecord.setBorrowDate(rset.getDate("borrow_date"));
                borrowRecord.setDueDate(rset.getDate("due_date"));

                borrowRecords.add(borrowRecord);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
            close(pstmt);
            close(rset);
        }
        return borrowRecords;
    }

        /*List<BookDTO> allBooksInfoList = new ArrayList<>();
        Map<String, List<BookDTO>> userAndBooks = new HashMap<>();

        String query = prop.getProperty("selectBookList");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setBoolean(1, true);  // 대여 가능한 책 목록 조회
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

                allBooksInfoList.add(bookInfo);

                // 여기서 메소드를 이용해서 중복되는 유저 아이디의 책 정보들을 모아서, 유저별로 대여 도서를 보여 줄 수 있도록 한다.
                apthem(a,allBooksInfoList);

                userAndBooks.put(a, allBooksInfoList);

            }



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
        return userAndBooks;
    }*/

    /** 연체 이력이 있는 회원 정보를 출력하는 메서드*/
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
                record.setUserId(rset.getString("user_id"));
                record.setBookTitle(rset.getString("book_title"));

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
