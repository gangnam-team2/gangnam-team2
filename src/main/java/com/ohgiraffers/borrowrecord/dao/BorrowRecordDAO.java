package com.ohgiraffers.borrowrecord.dao;

import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
import com.ohgiraffers.user.dto.UserDTO;
import java.sql.*;
import java.time.LocalDate;
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


        public void showBookList(Connection con, BorrowRecordDTO borrowRecordDTO) {

            Statement stmt = null;
            ResultSet rs = null;
            String query = prop.getProperty("showBookList");
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs != null) {
                    while (rs.next()) {
                        System.out.println("북코드: " + rs.getInt(1) + " "
                                + "제목: " + rs.getString(2) + " "
                                + "작가: " + rs.getString(3) + " "
                                + "장르: " + rs.getString(4) + " "
                                + "출판사: " + rs.getString(5));
                    }
                } else {
                    System.out.println("대여 가능한 책이 없습니다.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                close(rs);
                close(stmt);
                close(con);
            }
        }


        public int rentBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
            PreparedStatement pstmt = null;
            UserDTO userDTO = new UserDTO();
            int result = 0;
            String rentBookQuery = prop.getProperty("rentBook");
            String updateBookStatusQuery = prop.getProperty("updateBookStatus");

            try {
                // 도서 대여 정보를 삽입
                pstmt = con.prepareStatement(rentBookQuery);
                pstmt.setInt(1, borrowRecordDTO.getBookCode());
                pstmt.setString(2, borrowRecordDTO.getUserId());
                pstmt.setDate(3, borrowRecordDTO.getBorrowDate());
                ;
                result = pstmt.executeUpdate();

                // 도서 상태를 업데이트
                if (result > 0) {
                    pstmt = con.prepareStatement(updateBookStatusQuery);
                    pstmt.setBoolean(1, true); // 책을 대여했으므로 상태를 true로 설정 (대여 중)
                    pstmt.setInt(2, borrowRecordDTO.getBookCode());
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
            String query = prop.getProperty("returnBook");

            try {
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, borrowRecordDTO.getBookCode());
                pstmt.setDate(2, borrowRecordDTO.getReturnDate());
                BookDTO bookDTO = new BookDTO();
                bookDTO.setBookStatus(true);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                close(pstmt);
                close(con);
            }
            return result;
        }


        public int overDueBook(Connection con, BorrowRecordDTO borrowRecordDTO) {

            PreparedStatement pstmt = null;
            int result = 0;
            String query = prop.getProperty("overDueBook");
            LocalDate currentDate = LocalDate.now();
            if (currentDate.isAfter(borrowRecordDTO.getDueDate().toLocalDate())) {
                try {
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, borrowRecordDTO.getBookCode());
                    pstmt.setString(2, borrowRecordDTO.getUserId());
                    pstmt.setBoolean(3, true);

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



/*    // 책 대여 메소드
    public int rentBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("rentBook"); // 쿼리 확인

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, borrowRecordDTO.getBookCode());
            pstmt.setString(2, borrowRecordDTO.getUserId());
            pstmt.setDate(3, borrowRecordDTO.getBorrowDate());
            
            // dueDate는 Mysql에서 자동으로 추가 되니까 따로 추가 ㄴㄴ

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    // 책 반납 메서드
    public int returnBook(Connection con, BorrowRecordDTO borrowRecordDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("returnBook");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setDate(1, borrowRecordDTO.getReturnDate());
            pstmt.setInt(2, borrowRecordDTO.getBookCode());
            pstmt.setString(3, borrowRecordDTO.getUserId());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }*/

/*
    // 사용자가 대여 중인 책 목록을 가져오는 메서드
    public List<BorrowRecordDTO> getBorrowedBooksByUser(Connection con, String userId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BorrowRecordDTO> borrowedBooks = new ArrayList<>();
        String query = prop.getProperty("getBorrowedBooksByUser");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BorrowRecordDTO record = new BorrowRecordDTO();
                record.setBookCode(rs.getInt("book_code"));
                record.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                record.setDueDate(rs.getDate("due_date").toLocalDate());
                borrowedBooks.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return borrowedBooks;
    }

    // 대여 가능한 책 목록을 가져오는 메서드
    public List<BookDTO> getAvailableBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> availableBooks = new ArrayList<>();
        String query = prop.getProperty("getAvailableBooks");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO book = new BookDTO();
                book.setBookCode(rs.getInt("book_code"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookGenre(rs.getString("book_genre"));
                availableBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return availableBooks;
    }*/
    }



