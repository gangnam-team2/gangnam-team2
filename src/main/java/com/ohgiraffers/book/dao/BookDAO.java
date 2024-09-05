package com.ohgiraffers.book.dao;

import com.ohgiraffers.book.dto.BookDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class BookDAO {

    private Properties prop;

    public BookDAO() {
        prop = new Properties();
        try {
            prop.loadFromXML(getClass().getResourceAsStream("/mapper/book-query.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 도서 추가하는 메서드*/
    public int insertBook(Connection con, BookDTO bookDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertBook");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, bookDTO.getBookTitle());
            pstmt.setString(2, bookDTO.getBookAuthor());
            pstmt.setString(3, bookDTO.getBookPublisher());
            pstmt.setString(4, bookDTO.getBookGenre());
            pstmt.setBoolean(5, bookDTO.isBookStatus() ? bookDTO.isBookStatus() : false);
            pstmt.setInt(6, bookDTO.getBorrowCount());

            result = pstmt.executeUpdate();

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력되었습니다. 다시 시도해주세요.");
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    /** 도서 수정하는 메서드*/
    public int updateBook(Connection con, BookDTO bookDTO) {
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("updateBook");

        try {
            pstmt = con.prepareStatement(query);

            pstmt.setString(1, bookDTO.getBookTitle());
            pstmt.setString(2, bookDTO.getBookAuthor());
            pstmt.setString(3, bookDTO.getBookPublisher());
            pstmt.setString(4, bookDTO.getBookGenre());
            pstmt.setBoolean(5, bookDTO.isBookStatus());
            pstmt.setInt(6, bookDTO.getBorrowCount());
            pstmt.setInt(7, bookDTO.getBookCode());

            result = pstmt.executeUpdate();

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력되었습니다. 다시 시도해주세요.");
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
    }

    /** 도서 삭제하는 메서드*/
    public int deleteBook(Connection con, int bookCode) {
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            // 1. borrow_records 테이블에서 해당 도서와 관련된 기록 삭제
            String deleteFromBorrowRecordsQuery = prop.getProperty("deleteFromBorrowRecords");
            pstmt = con.prepareStatement(deleteFromBorrowRecordsQuery);
            pstmt.setInt(1, bookCode);
            pstmt.executeUpdate(); // 삭제 결과는 무시하고 다음 삭제로 진행

            pstmt.close();

            // 2. best_sellers 테이블에서 해당 도서 삭제
            String deleteFromBestSellersQuery = prop.getProperty("deleteFromBestSellers");
            pstmt = con.prepareStatement(deleteFromBestSellersQuery);
            pstmt.setInt(1, bookCode);
            pstmt.executeUpdate(); // 삭제 결과는 무시하고 books 삭제로 진행

            pstmt.close();

            // 3. books 테이블에서 도서 삭제
            String deleteBookQuery = prop.getProperty("deleteBook");
            pstmt = con.prepareStatement(deleteBookQuery);
            pstmt.setInt(1, bookCode);
            result = pstmt.executeUpdate(); // books 테이블에서 도서 삭제

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력되었습니다. 다시 시도해주세요.");
            e.printStackTrace();
        } finally {
            close(pstmt);
        }

        return result; // 삭제 성공 시 1, 실패 시 0 반환
    }

    /** 도서 식별번호로 도서 가져오는 메서드*/
    public BookDTO getBookById(Connection con, int bookCode) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BookDTO bookDTO = null;
        String query = prop.getProperty("getBookByCode");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bookDTO = new BookDTO(
                        rs.getInt("book_code"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("book_publisher"),
                        rs.getString("book_genre"),
                        rs.getBoolean("book_status"),
                        rs.getInt("borrow_count")
                );
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력되었습니다. 다시 시도해주세요.");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return bookDTO;
    }

    /** 도서 식별번호를 이용해 도서 찾는 메서드*/
    public BookDTO searchBooksByCode(Connection con, int bookCode) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BookDTO bookDTO = null;
        String query = prop.getProperty("searchBooksByCode");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookCode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bookDTO = new BookDTO(
                        rs.getInt("book_code"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("book_publisher"),
                        rs.getString("book_genre"),
                        rs.getBoolean("book_status"),
                        rs.getInt("borrow_count")
                );
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력되었습니다. 다시 시도해주세요.");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return bookDTO;
    }

    /** 도서 제목으로 도서 찾는 메서드*/
    public List<BookDTO> searchBooksByTitle(Connection con, String title) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> books = new ArrayList<>();
        String query = prop.getProperty("searchBooksByTitle");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + title + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO bookDTO = new BookDTO(
                        rs.getInt("book_code"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("book_publisher"),
                        rs.getString("book_genre"),
                        rs.getBoolean("book_status"),
                        rs.getInt("borrow_count")
                );
                books.add(bookDTO);
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력되었습니다. 다시 시도해주세요.");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return books;
    }

    /** 연체된 도서 목록을 조회하는 메서드*/
    public List<BookDTO> getOverdueBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> books = new ArrayList<>();
        String query = prop.getProperty("getOverdueBooks");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO bookDTO = new BookDTO(
                        rs.getInt("book_code"),
                        rs.getString("book_title"),
                        rs.getString("book_author"),
                        rs.getString("book_publisher"),
                        rs.getString("book_genre"),
                        rs.getBoolean("book_status"),
                        rs.getInt("borrow_count")
                );
                books.add(bookDTO);
            }

        } catch (SQLException | NullPointerException e) {
            System.out.println("잘못된 값이 입력되었습니다. 다시 시도해주세요.");
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return books;
    }


    /** 요청된 도서를 추가하는 메서드 - 추후 필요시 사용*/
    public void insertBookIntoDB(Connection con, BookDTO bookDTO) throws SQLException {
        con = null;
        BookDAO bookDAO = new BookDAO();
        int result = 0;
        try {
            result = bookDAO.insertBook(con, bookDTO);
        } finally {
            close(con);
        }

        if (result > 0) {
            System.out.println("도서가 추가되었습니다.");
        } else {
            System.out.println("도서 추가에 실패하였습니다.");
        }
    }

    /** 대여 가능한 도서를 가져오는 메서드 - 추후 필요시 사용*/
    public List<BookDTO> getAvailableBooks(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookDTO> availableBooks = new ArrayList<>();
        String query = prop.getProperty("getAvailableBooks");

        try {
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setBookCode(rs.getInt("book_code"));
                bookDTO.setBookTitle(rs.getString("book_title"));
                bookDTO.setBookAuthor(rs.getString("book_author"));
                bookDTO.setBookPublisher(rs.getString("book_publisher"));
                bookDTO.setBookGenre(rs.getString("book_genre"));

                availableBooks.add(bookDTO);
            }
        } catch (SQLException e) {
            System.err.println("도서를 불러오는 도중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace(); // 필요시 제거 가능
        } finally {
            // null 체크 후 close 처리
            if (rs != null) {
                try {
                    rs.close();
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
        }

        return availableBooks;
    }

}