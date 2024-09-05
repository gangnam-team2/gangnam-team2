package com.ohgiraffers.borrowrecord.dao;

import com.ohgiraffers.usersession.UserSession;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import static com.ohgiraffers.common.JDBCTemplate.*;



public class BorrowRecordDAO {

    private Properties prop;

    public BorrowRecordDAO() {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/borrowBook-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /** 대여 가능한 도서 목록을 출력하는 메서드*/
        public List<Integer> showBookList(Connection con, BorrowRecordDTO borrowRecordDTO) {
            List<Integer> bookList = new ArrayList<Integer>();
            Statement stmt = null;
            ResultSet rs = null;
            String query = prop.getProperty("showBookList");
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs != null) {
                    System.out.println("\n=== 대여 가능한 도서 ===");
                    while (rs.next()) {
                        System.out.println("도서코드: " + rs.getInt(1) + " "
                                + "제목: " + rs.getString(2) + " "
                                + "작가: " + rs.getString(3) + " "
                                + "장르: " + rs.getString(4) + " "
                                + "출판사: " + rs.getString(5));
                        bookList.add(rs.getInt(1));
                    }
                    System.out.println("======================================");
                } else {
                    System.out.println("\n대여 가능한 책이 없습니다. 이전 메뉴로 돌아갑니다 !");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                close(rs);
                close(stmt);
                close(con);
            }return bookList;
        }

        /**도서 대여하는 메서드*/
    public int rentBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String rentBookQuery = prop.getProperty("rentBook");
        String updateBookStatusQuery = prop.getProperty("updateBookStatus");
        String incrementBorrowCountQuery = prop.getProperty("incrementBorrowCount");
        String selectBookTitleByCodeQuery = prop.getProperty("selectBookTitleByCode"); // XML에서 쿼리 가져오기

        try {
            // 대여하려는 책의 제목을 가져오는 쿼리
            pstmt = con.prepareStatement(selectBookTitleByCodeQuery);
            pstmt.setInt(1, borrowRecordDTO.getBookCode());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                borrowRecordDTO.setBookTitle(rs.getString("book_title"));
            } else {
                throw new SQLException("\n해당 코드의 책을 찾을 수 없습니다. ");
            }
            rs.close();
            pstmt.close();

            // 도서 대여 정보를 삽입
            pstmt = con.prepareStatement(rentBookQuery);
            pstmt.setInt(1, borrowRecordDTO.getBookCode());
            pstmt.setString(2, UserSession.getUserDTO().getUserId());
            pstmt.setString(3, borrowRecordDTO.getBookTitle());  // bookTitle 설정
            pstmt.setDate(4, borrowRecordDTO.getBorrowDate());

            result = pstmt.executeUpdate();

            // 여기서는 도서 상태를 업데이트
            if (result > 0) {
                pstmt = con.prepareStatement(updateBookStatusQuery);
                pstmt.setBoolean(1, true); // 책을 대여했으므로 상태를 true로 설정
                pstmt.setInt(2, borrowRecordDTO.getBookCode());
                pstmt.executeUpdate();

                // borrow_count 증가
                pstmt = con.prepareStatement(incrementBorrowCountQuery);
                pstmt.setInt(1, borrowRecordDTO.getBookCode());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }
        return result;
    }


    /** 도서 반납하는 메서드*/
    public int returnBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String updateReturnDateQuery = prop.getProperty("returnBook");
        String updateBookStatusQuery = prop.getProperty("updateBookStatus");
        String updateOverdueBooksStatusQuery = prop.getProperty("updateOverdueBooksStatus");

        try {
            // 반납일 업데이트
            pstmt = con.prepareStatement(updateReturnDateQuery);
            pstmt.setDate(1, borrowRecordDTO.getReturnDate());
            pstmt.setString(2, borrowRecordDTO.getUserId());
            pstmt.setInt(3, borrowRecordDTO.getBookCode());
            result = pstmt.executeUpdate();

            if (result > 0) {
                // book_status를 대여 가능 상태로 (false)로 설정
                pstmt = con.prepareStatement(updateBookStatusQuery);
                pstmt.setBoolean(1, false);
                pstmt.setInt(2, borrowRecordDTO.getBookCode());
                pstmt.executeUpdate();

                // over_due_books를 false로 변경 (연체 상태 초기화)
                pstmt = con.prepareStatement(updateOverdueBooksStatusQuery);
                pstmt.setInt(1, borrowRecordDTO.getBookCode());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }

        return result;
    }

    /**대여 가능한 도서들의 여부를 확인하려고 만든 메서드 -> 사용자가 대여중인 도서 목록 가져오려고
    이게 있어야 내가 뭘 대여했는지 알 수 있고 그래야 대여 가능한 도서에서만 대여를 할 수 있음.*/
    public List<BorrowRecordDTO> getBorrowedBooks(Connection con, String userId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BorrowRecordDTO> borrowedBooks = new ArrayList<>();
        String query = prop.getProperty("getBorrowedBooks");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BorrowRecordDTO book = new BorrowRecordDTO();
                book.setBookCode(rs.getInt("book_code"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBorrowDate(rs.getDate("borrow_date"));
                borrowedBooks.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }

        return borrowedBooks;
    }



    /**대여 이력의 정보를 받아오는 메서드*/
    public List<Integer> getBorrowRecords(Connection con, BorrowRecordDTO borrowRecordDTO) {
        Statement stmt = null;
        ResultSet rset = null;
        List<Integer> borrowRecords = new ArrayList<Integer>();
        String query = prop.getProperty("borrowRecords");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            while (rset.next()) {
                borrowRecordDTO.setBorrowCode(rset.getInt("borrow_code"));
                borrowRecordDTO.setUserId(rset.getString("user_id"));
                borrowRecordDTO.setBookTitle(rset.getString("book_title"));
                borrowRecordDTO.setBookCode(rset.getInt("book_code"));
                borrowRecordDTO.setBorrowDate(rset.getDate("borrow_date"));
                borrowRecordDTO.setDueDate(rset.getDate("due_date"));
                borrowRecordDTO.setReturnDate(rset.getDate("return_date"));
                borrowRecordDTO.setOverDueBooks(rset.getBoolean("over_due_books"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(stmt);
            close(rset);
        }return borrowRecords;
    }


    /**연체된 도서의 상태를 업데이트하는 메서드*/
    public int overDueBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("overDueBooks");

        try {
            // 연체 상태를 확인하고, due_date가 현재 날짜를 넘는 경우에만 실행
            if (borrowRecordDTO.getDueDate() != null && LocalDate.now().isAfter(borrowRecordDTO.getDueDate().toLocalDate())) {
                pstmt = con.prepareStatement(query);
                pstmt.setBoolean(1, true); // 연체 상태를 true로 설정 (1)
                pstmt.setInt(2, borrowRecordDTO.getBookCode()); // 해당 도서의 book_code
                result = pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // PreparedStatement가 null이 아닌 경우에만 close() 호출
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
        return result;
    }


    /**모든 연체된 도서의 목록을 보는 메서드*/
    public void overDueBookList(Connection con, BorrowRecordDTO borrowRecordDTO) {
        Statement stmt = null;
        ResultSet rs = null;
        String query = prop.getProperty("overDueBookList");

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if (rs != null && rs.next()) {
                do {
                    System.out.printf("ID: %s | 북코드: %d | 제목: %s | 대여일: %s | 반납 예정일: %s\n",
                            rs.getString(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getDate(5));
                } while (rs.next());
            } else {
                System.out.println("\n연체된 책이 없습니다. 이전 메뉴로 돌아갑니다 !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
        }
    }

    // borrow_records 테이블에 있는 모든 user_id를 가져오는 메서드
    public List<String> getAllUserIds(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> userIds = new ArrayList<>();
        String query = prop.getProperty("getAllUserIds");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userIds.add(rs.getString("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }

        return userIds;
    }
}



