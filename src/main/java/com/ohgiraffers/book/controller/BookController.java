package com.ohgiraffers.book.controller;

import com.ohgiraffers.book.dao.BookDAO;
import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.common.JDBCTemplate;
import com.ohgiraffers.request.controller.RequestController;
import com.ohgiraffers.request.dao.RequestDAO;
import com.ohgiraffers.request.dto.RequestDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.*;

public class BookController {
    private RequestDAO requestDAO;
    private BookDAO bookDAO;
    private Scanner sc;

    public BookController() {
        bookDAO = new BookDAO();
        sc = new Scanner(System.in);
        requestDAO = new RequestDAO();
    }

    public void insertBook() throws SQLException {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("=====새 도서 추가=====");

            // 도서 제목 입력
            System.out.print("도서 제목 : ");
            String bookTitle = sc.nextLine().trim();
            if (bookTitle.isEmpty()) {
                System.out.println("도서 제목은 비워둘 수 없습니다.");
                continue;
            }

            // 도서 저자 입력
            System.out.print("도서 저자 : ");
            String bookAuthor = sc.nextLine().trim();
            if (bookAuthor.isEmpty()) {
                System.out.println("도서 저자는 비워둘 수 없습니다.");
                continue;
            }

            // 도서 출판사 입력
            System.out.print("도서 출판사 : ");
            String bookPublisher = sc.nextLine().trim();
            if (bookPublisher.isEmpty()) {
                System.out.println("도서 출판사는 비워둘 수 없습니다.");
                continue;
            }

            // 도서 장르 입력
            System.out.print("도서 장르 : ");
            String bookGenre = sc.nextLine().trim();
            if (bookGenre.isEmpty()) {
                System.out.println("도서 장르는 비워둘 수 없습니다.");
                continue;
            }

            boolean bookStatus = true; // true = 도서 대여 가능

            BookDTO bookDTO = new BookDTO();
            bookDTO.setBookTitle(bookTitle);
            bookDTO.setBookAuthor(bookAuthor);
            bookDTO.setBookPublisher(bookPublisher);
            bookDTO.setBookGenre(bookGenre);
            bookDTO.setBookStatus(bookStatus);

            insertBookIntoDB(bookDTO);

            validInput = true; // 입력이 모두 유효할 경우 루프를 종료
        }
    }

    public void updateBook() {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("=====도서 정보 수정=====");
            System.out.print("수정할 도서 코드: ");
            int bookCode = 0;

            if (sc.hasNextInt()) {
                bookCode = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("유효한 도서 코드를 입력하세요.");
                sc.next();
                continue;
            }

            Connection con = getConnection();
            BookDTO bookDTO = bookDAO.getBookById(con, bookCode);

            if (bookDTO == null) {
                System.out.println("해당 도서를 찾을 수 없습니다.");
                close(con);
                return;
            }

            System.out.print("새 도서 제목: ");
            String bookTitle = sc.nextLine().trim();
            if (bookTitle.isEmpty()) {
                System.out.println("도서 제목은 비워둘 수 없습니다.");
                continue;
            }

            System.out.print("새 도서 저자: ");
            String bookAuthor = sc.nextLine().trim();
            if (bookAuthor.isEmpty()) {
                System.out.println("도서 저자는 비워둘 수 없습니다.");
                continue;
            }

            System.out.print("새 도서 출판사: ");
            String bookPublisher = sc.nextLine().trim();
            if (bookPublisher.isEmpty()) {
                System.out.println("도서 출판사는 비워둘 수 없습니다.");
                continue;
            }

            System.out.print("새 도서 장르: ");
            String bookGenre = sc.nextLine().trim();
            if (bookGenre.isEmpty()) {
                System.out.println("도서 장르는 비워둘 수 없습니다.");
                continue;
            }

            System.out.print("도서 상태 (true: 대여 중, false: 대여 가능): ");
            boolean bookStatus = false;
            if (sc.hasNextBoolean()) {
                bookStatus = sc.nextBoolean();
                sc.nextLine();
            } else {
                System.out.println("유효한 값을 입력하세요 (true 또는 false).");
                sc.next();
                continue;
            }

            bookDTO.setBookTitle(bookTitle);
            bookDTO.setBookAuthor(bookAuthor);
            bookDTO.setBookPublisher(bookPublisher);
            bookDTO.setBookGenre(bookGenre);
            bookDTO.setBookStatus(bookStatus);

            int result = 0;
            try {
                result = bookDAO.updateBook(con, bookDTO);
            } finally {
                JDBCTemplate.close(con);
            }

            if (result > 0) {
                System.out.println("도서 정보가 수정되었습니다.");
                validInput = true;
            } else {
                System.out.println("도서 정보 수정에 실패하였습니다.");
            }
        }
    }

    public void deleteBook() {
        System.out.println("=====도서 삭제======");
        System.out.print("삭제할 도서 코드: ");
        int bookCode = sc.nextInt();
        sc.nextLine();

        Connection con = getConnection();
        int result = bookDAO.deleteBook(con, bookCode);
        close(con);

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

        Connection con = getConnection();
        BookDTO bookDTO = bookDAO.getBookById(con, bookCode);
        close(con);

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

        Connection con = getConnection();
        List<BookDTO> books = bookDAO.searchBooksByTitle(con, title);
        close(con);

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

        Connection con = getConnection();
        List<BookDTO> books = bookDAO.searchBooksByCategory(con, categoryId);
        close(con);

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

        Connection con = getConnection();
        List<BookDTO> books = bookDAO.getOverdueBooks(con);
        close(con);

        if (!books.isEmpty()) {
            for (BookDTO book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("연체된 도서가 없습니다.");
        }
    }

    private final RequestController requestController = new RequestController();

    public void manageBooksMenu(Scanner sc) throws SQLException {
        boolean managing = true;

        while (managing) {
            System.out.println("\n== 도서 관리 메뉴 ==");
            System.out.println("1. 도서 추가");
            System.out.println("2. 도서 수정");
            System.out.println("3. 도서 삭제");
            System.out.println("4. 요청된 도서 확인 및 추가");  // 새로운 메뉴 추가
            System.out.println("0. 이전 메뉴로 돌아가기");

            System.out.print("선택: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    insertBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    showRequestedBooks(sc);  // 요청된 도서 확인 및 추가 기능 호출
                    break;
                case 0:
                    managing = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }


    // 요청된 도서 목록을 보여주고 선택적으로 도서 목록에 추가하는 메서드
        public void showRequestedBooks(Scanner sc) throws SQLException {
            List<RequestDTO> requestedBooks = requestController.getRequestedBooks();

            if (requestedBooks.isEmpty()) {
                System.out.println("현재 요청된 도서가 없습니다.");
                return;
            }

            System.out.println("\n===== 요청된 도서 목록 =====");
            for (int i = 0; i < requestedBooks.size(); i++) {
                RequestDTO book = requestedBooks.get(i);
                System.out.printf("%d. 제목: %s - 저자: %s - 출판사: %s - 장르: %s%n",
                        i + 1,
                        book.getBookTitle(),
                        book.getBookAuthor(),
                        book.getBookPublisher(),
                        book.getBookGenre() != null ? book.getBookGenre() : "미정");
            }
            System.out.println("=============================");

            System.out.println("도서를 추가하시겠습니까? (1: 예, 2: 아니오)");
            System.out.print("선택 : ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("추가할 도서 번호를 선택하세요:");
                int bookChoice = sc.nextInt();
                sc.nextLine();

                if (bookChoice > 0 && bookChoice <= requestedBooks.size()) {
                    RequestDTO selectedBook = requestedBooks.get(bookChoice - 1);

                    // 도서를 DB에 추가
                    addRequestedBook(selectedBook);

                    // 요청 목록에서 삭제
                    boolean isDeleted = requestController.deleteRequestedBook(selectedBook.getRequestId());
                    if (isDeleted) {
                        System.out.println("요청된 도서 " + selectedBook.getBookTitle() + "이 목록에서 삭제되었습니다.");
                    } else {
                        System.out.println("요청된 도서 삭제에 실패하였습니다.");
                    }
                } else {
                    System.out.println("잘못된 선택입니다.");
                }
            }
        }

        private void insertBookIntoDB(BookDTO bookDTO) throws SQLException {
            Connection con = getConnection();
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

        public void addRequestedBook(RequestDTO requestedBook) throws SQLException {
            BookDTO newBook = new BookDTO();
            newBook.setBookTitle(requestedBook.getBookTitle());
            newBook.setBookAuthor(requestedBook.getBookAuthor());
            newBook.setBookPublisher(requestedBook.getBookPublisher());
            newBook.setBookGenre(requestedBook.getBookGenre() != null ? requestedBook.getBookGenre() : "미정");
            newBook.setBookStatus(true);

            // DB에 도서 추가
            insertBookIntoDB(newBook);
        }
    }
