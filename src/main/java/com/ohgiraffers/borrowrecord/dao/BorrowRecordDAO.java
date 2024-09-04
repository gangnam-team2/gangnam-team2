package com.ohgiraffers.borrowrecord.dao;

import com.ohgiraffers.usersession.UserSession;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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


        public List<Integer> showBookList(Connection con, BorrowRecordDTO borrowRecordDTO) {
            List<Integer> bookList = new ArrayList<Integer>();
            Statement stmt = null;
            ResultSet rs = null;
            String query = prop.getProperty("showBookList");
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs != null) {
                    System.out.println("============= 대여 가능한 도서 =============");
                    while (rs.next()) {
                        System.out.println("북코드: " + rs.getInt(1) + " "
                                + "제목: " + rs.getString(2) + " "
                                + "작가: " + rs.getString(3) + " "
                                + "장르: " + rs.getString(4) + " "
                                + "출판사: " + rs.getString(5));
                        bookList.add(rs.getInt(1));
                    }
                    System.out.println("=======================================");
                } else {
                    System.out.println("대여 가능한 책이 없습니다.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                close(rs);
                close(stmt);
                close(con);
            }return bookList;
        }


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
                throw new SQLException("해당 코드의 책을 찾을 수 없습니다.");
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


    public int returnBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String logind = UserSession.getUserDTO().getUserId();

        String deleteBorrowRecordQuery = prop.getProperty("deleteBorrowRecord");
        String updateBookStatusQuery = prop.getProperty("updateBookStatus");

        try {

            // 우선 borrow_records 테이블에서 대여하는 사람이 반납하는 거니까 해당 도서 대여 기록 삭제
            pstmt = con.prepareStatement(deleteBorrowRecordQuery);
            pstmt.setInt(1, borrowRecordDTO.getBookCode());
            pstmt.setString(2, logind);
            result = pstmt.executeUpdate();
            close(pstmt);

            if (result > 0) {
                // 여기서는 books 테이블에서 book_status를 false로 업데이트 이러면 대여 가능 상태로 변경
                pstmt = con.prepareStatement(updateBookStatusQuery);
                pstmt.setBoolean(1, false);
                pstmt.setInt(2, borrowRecordDTO.getBookCode());
                pstmt.executeUpdate();

            } else {
                System.out.println("대여 기록이 존재하지 않습니다.");
                return 0; // 대여 기록이 없을 경우 반납 실패 처리
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }

        return result;
    }

    // 대여 가능한 도서들의 여부를 확인하려고 만든 메서드 -> 사용자가 대여중인 도서 목록 가져오려고
    // 이게 있어야 내가 뭘 대여했는지 알 수 있고 그래야 대여 가능한 도서에서만 대여를 할 수 있음.
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


        public int overDueBook(Connection con, BorrowRecordDTO borrowRecordDTO) {

            PreparedStatement pstmt = null;
            int result = 0;
            String query = prop.getProperty("overDueBooks");
            LocalDate currentDate = LocalDate.now();
            if (currentDate.isAfter(borrowRecordDTO.getDueDate().toLocalDate())) {
                try {
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, borrowRecordDTO.getBookCode());
                    pstmt.setBoolean(2, true);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    close(con);
                    close(pstmt);
                }
            }
            return result;
        }

        public void overDueBookList(Connection con, BorrowRecordDTO borrowRecordDTO) {
            Statement stmt = null;
            ResultSet rs = null;
            String query = prop.getProperty("overDueBookList");

            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs != null) {
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getString(1) + " " + "북코드: " + rs.getInt(2) + " "
                                + "제목: " + rs.getString(3) + " " + "대여일: " + rs.getDate(4) + " " + "반납 예정일: " + rs.getDate(5));
                    }
                } else {
                    System.out.println("연체된 책이 없습니다.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



