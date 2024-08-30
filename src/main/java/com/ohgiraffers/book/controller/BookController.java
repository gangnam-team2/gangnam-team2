package com.ohgiraffers.book.controller;

import com.ohgiraffers.book.dao.BookDAO;
import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.common.JDBCTemplate;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class BookController {
    private BookDAO bookDAO;
    private Scanner sc;

    public BookController() {
        bookDAO = new BookDAO();
        sc = new Scanner(System.in);
    }

    public void insertBook() {
        System.out.println("=====새 도서 추가=====");
        System.out.print("도서 제목 : ");
        String bookTitle = sc.nextLine();
        System.out.print("도서 저자 : ");
        String bookAuthor = sc.nextLine();
        System.out.print("도서 출판사 : ");
        String bookPublisher = sc.nextLine();
        System.out.print("도서 장르 : ");
        String bookGenre = sc.nextLine();
        boolean bookStatus = false;                 // false = 도서 대여 가능

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookTitle(bookTitle);
        bookDTO.setBookAuthor(bookAuthor);
        bookDTO.setBookPublisher(bookPublisher);
        bookDTO.setBookGenre(bookGenre);
        bookDTO.setBookStatus(bookStatus);

        Connection con = JDBCTemplate.getConnection();

        int result = bookDAO.insertBook(con, bookDTO);
        JDBCTemplate.close(con);

        if (result > 0) {
            System.out.println("도서가 추가되었습니다.");
        } else {
            System.out.println("도서 추가에 실패하였습니다.");
        }
    }

    public void updateBook() {
        System.out.println("=====도서 정보 수정=====");
        System.out.print("수정할 도서 코드: ");
        int bookCode = sc.nextInt();
        sc.nextLine();

        Connection con = JDBCTemplate.getConnection();
        BookDTO bookDTO = bookDAO.getBookById(con, bookCode);

        if (bookDTO == null) {
            System.out.println("해당 도서를 찾을 수 없습니다.");
            JDBCTemplate.close(con);
            return;
        }

        System.out.print("새 도서 제목: ");
        String bookTitle = sc.nextLine();
        System.out.print("새 도서 저자: ");
        String bookAuthor = sc.nextLine();
        System.out.print("새 도서 출판사: ");
        String bookPublisher = sc.nextLine();
        System.out.print("새 도서 장르: ");
        String bookGenre = sc.nextLine();
        System.out.print("도서 상태 (true: 대여 중, false: 대여 가능): ");
        boolean bookStatus = sc.nextBoolean();

        bookDTO.setBookTitle(bookTitle);
        bookDTO.setBookAuthor(bookAuthor);
        bookDTO.setBookPublisher(bookPublisher);
        bookDTO.setBookGenre(bookGenre);
        bookDTO.setBookStatus(bookStatus);

        int result = bookDAO.updateBook(con, bookDTO);
        JDBCTemplate.close(con);

        if (result > 0) {
            System.out.println("도서 정보가 수정되었습니다.");
        } else {
            System.out.println("도서 정보 수정에 실패하였습니다.");
        }
    }

    public void deleteBook() {
        System.out.println("=====도서 삭제======");
        System.out.print("삭제할 도서 코드: ");
        int bookCode = sc.nextInt();
        sc.nextLine();

        Connection con = JDBCTemplate.getConnection();
        int result = bookDAO.deleteBook(con, bookCode);
        JDBCTemplate.close(con);

        if (result > 0) {
            System.out.println("도서가 삭제되었습니다.");
        } else {
            System.out.println("도서 삭제에 실패하였습니다.");
        }
    }

    public void searchBookById() {
        System.out.println("=====도서 검색 (코드로 검색)=====");
        System.out.print("검색할 도서 코드: ");
        int bookCode = sc.nextInt();
        sc.nextLine();

        Connection con = JDBCTemplate.getConnection();
        BookDTO bookDTO = bookDAO.getBookById(con, bookCode);
        JDBCTemplate.close(con);

        if (bookDTO != null) {
            System.out.println(bookDTO);
        } else {
            System.out.println("해당 도서를 찾을 수 없습니다.");
        }
    }

    public void searchBooksByTitle() {
        System.out.println("=====도서 검색 (제목으로 검색)=====");
        System.out.print("검색할 도서 제목: ");
        String title = sc.nextLine();

        Connection con = JDBCTemplate.getConnection();
        List<BookDTO> books = bookDAO.searchBooksByTitle(con, title);
        JDBCTemplate.close(con);

        if (!books.isEmpty()) {
            for (BookDTO book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("해당 제목의 도서를 찾을 수 없습니다.");
        }
    }

    public void searchBooksByCategory() {
        System.out.println("=====도서 검색 (카테고리로 검색)=====");
        System.out.print("검색할 카테고리 ID: ");
        int categoryId = sc.nextInt();
        sc.nextLine();

        Connection con = JDBCTemplate.getConnection();
        List<BookDTO> books = bookDAO.searchBooksByCategory(con, categoryId);
        JDBCTemplate.close(con);

        if (!books.isEmpty()) {
            for (BookDTO book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("해당 카테고리의 도서를 찾을 수 없습니다.");
        }
    }

    public void searchOverdueBooks() {
        System.out.println("=====연체된 도서 목록 조회======");

        Connection con = JDBCTemplate.getConnection();
        List<BookDTO> books = bookDAO.getOverdueBooks(con);
        JDBCTemplate.close(con);

        if (!books.isEmpty()) {
            for (BookDTO book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("연체된 도서가 없습니다.");
        }
    }
}
