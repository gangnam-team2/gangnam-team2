package com.ohgiraffers.book.controller;

import com.ohgiraffers.book.dao.BookDAO;
import com.ohgiraffers.book.dto.BookDTO;
import com.ohgiraffers.borrowrecord.dao.BorrowRecordDAO;
import com.ohgiraffers.borrowrecord.dto.BorrowRecordDTO;
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
    private static BookDAO bookDAO;
    private static Scanner sc;

    public BookController() {
        bookDAO = new BookDAO();
        sc = new Scanner(System.in);
        requestDAO = new RequestDAO();
    }

    /** 도서추가 메서드*/
    public void insertBook() throws SQLException {
        boolean validInput = false;

        // 특수문자 입력 못하게 정규식사용 -> 알파벳, 숫자, 공백만 허용
        String regex = "^[a-zA-Z0-9\\s]+$";

        while (!validInput) {
            System.out.println("\n=== 신규 도서 추가 ===\n");

            // 도서 제목 입력
            System.out.print("\n도서 제목을 입력해주세요 : ");
            String bookTitle = sc.nextLine().trim();
            if (bookTitle.isEmpty()) {
                System.out.println("\n도서 제목은 비워둘 수 없습니다. 다시 시도합니다 !");
                continue;
            } else if (!bookTitle.matches(regex)) {
                System.out.println("\n도서 제목에 특수문자는 사용할 수 없습니다. 다시 시도합니다 !");
                continue;
            }

            // 도서 저자 입력
            System.out.print("\n도서 저자를 입력해주세요 :");
            String bookAuthor = sc.nextLine().trim();
            if (bookAuthor.isEmpty()) {
                System.out.println("\n도서 저자는 비워둘 수 없습니다. 다시 시도합니다 !");
                continue;
            } else if (!bookAuthor.matches(regex)) {
                System.out.println("\n도서 저자에 특수문자는 사용할 수 없습니다. 다시 시도합니다 !");
                continue;
            }

            // 도서 출판사 입력
            System.out.print("\n도서 출판사를 입력해주세요 : ");
            String bookPublisher = sc.nextLine().trim();
            if (bookPublisher.isEmpty()) {
                System.out.println("\n도서 출판사는 비워둘 수 없습니다. 다시 시도합니다 !");
                continue;
            } else if (!bookPublisher.matches(regex)) {
                System.out.println("\n도서 출판사에 특수문자는 사용할 수 없습니다. 다시 시도합니다 !");
                continue;
            }

            // 도서 장르 입력
            System.out.print("\n도서 장르를 입력해주세요 : ");
            String bookGenre = sc.nextLine().trim();
            if (bookGenre.isEmpty()) {
                System.out.println("\n도서 장르는 비워둘 수 없습니다. 다시 시도합니다 !");
                continue;
            } else if (!bookGenre.matches(regex)) {
                System.out.println("\n도서 장르에 특수문자는 사용할 수 없습니다. 다시 시도합니다 !");
                continue;
            }

            // 도서 상태는 기본적으로 대여 가능으로 설정 (false)
            boolean bookStatus = false;

            // BookDTO 객체에 값 설정
            BookDTO bookDTO = new BookDTO();
            bookDTO.setBookTitle(bookTitle);
            bookDTO.setBookAuthor(bookAuthor);
            bookDTO.setBookPublisher(bookPublisher);
            bookDTO.setBookGenre(bookGenre);
            bookDTO.setBookStatus(bookStatus);

            // 데이터베이스에 도서 추가
            insertBookIntoDB(bookDTO);

            validInput = true; // 모든 입력이 유효하면 루프 종료
        }
    }

    /** 도서 수정 메서드*/
    public void updateBook() {
        boolean validInput = false;

        // 특수문자 입력 못하게 정규식사용 -> 알파벳, 숫자, 공백만 허용
        String regex = "^[a-zA-Z0-9\\s]+$";

        while (!validInput) {
            System.out.println("\n=== 도서 정보 수정 ===");
            System.out.print("수정할 도서 코드를 입력해주세요 : ");
            int bookCode = 0;

            if (sc.hasNextInt()) {
                bookCode = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println("\n유효한 도서 코드를 입력해주세요. 다시 시도합니다 !");
                sc.next();  // 잘못된 입력은 여기서 제거
                continue;
            }

            Connection con = getConnection();
            BookDTO bookDTO = bookDAO.getBookById(con, bookCode);

            if (bookDTO == null) {
                System.out.println("\n해당 도서를 찾을 수 없습니다. 이전 메뉴로 돌아갑니다 !");
                close(con);
                return;
            }

            // 새 도서 제목 입력
            System.out.print("\n 신규 도서 제목을 입력해주세요 : ");
            String bookTitle = sc.nextLine().trim();
            if (bookTitle.isEmpty()) {
                System.out.println("\n도서 제목은 비워둘 수 없습니다. 다시 시도합니다 !");
                continue;
            } else if (!bookTitle.matches(regex)) {
                System.out.println("\n도서 제목에 특수문자는 사용할 수 없습니다. 다시 시도합니다 !");
                continue;
            }

            // 새 도서 저자 입력
            System.out.print("\n신규 도서의 저자를 입력해주세요 : ");
            String bookAuthor = sc.nextLine().trim();
            if (bookAuthor.isEmpty()) {
                System.out.println("도서 저자는 비워둘 수 없습니다.");
                continue;
            } else if (!bookAuthor.matches(regex)) {
                System.out.println("도서 저자에 특수문자는 사용할 수 없습니다.");
                continue;
            }

            // 새 도서 출판사 입력
            System.out.print("새 도서 출판사: ");
            String bookPublisher = sc.nextLine().trim();
            if (bookPublisher.isEmpty()) {
                System.out.println("도서 출판사는 비워둘 수 없습니다.");
                continue;
            } else if (!bookPublisher.matches(regex)) {
                System.out.println("도서 출판사에 특수문자는 사용할 수 없습니다.");
                continue;
            }

            // 새 도서 장르 입력
            System.out.print("새 도서 장르: ");
            String bookGenre = sc.nextLine().trim();
            if (bookGenre.isEmpty()) {
                System.out.println("도서 장르는 비워둘 수 없습니다.");
                continue;
            } else if (!bookGenre.matches(regex)) {
                System.out.println("도서 장르에 특수문자는 사용할 수 없습니다.");
                continue;
            }

            // 대여 상태 입력
            System.out.print("대여 상태 (true: 대여 중, false: 대여 가능): ");
            boolean bookStatus = false;
            if (sc.hasNextBoolean()) {
                bookStatus = sc.nextBoolean();
                sc.nextLine();  // 개행 문자 제거
            } else {
                System.out.println("유효한 값을 입력하세요 (true 또는 false).");
                sc.next();  // 잘못된 입력 제거
                continue;
            }

            // BookDTO에 수정된 값 설정
            bookDTO.setBookTitle(bookTitle);
            bookDTO.setBookAuthor(bookAuthor);
            bookDTO.setBookPublisher(bookPublisher);
            bookDTO.setBookGenre(bookGenre);
            bookDTO.setBookStatus(bookStatus);

            int result = 0;
            try {
                result = bookDAO.updateBook(con, bookDTO);
            } finally {
                close(con);
            }

            if (result > 0) {
                System.out.println("\n도서 정보 수정이 완료되었습니다 !.");
                validInput = true;
            } else {
                System.out.println("\n도서 정보 수정에 실패하였습니다. 다시 시도합니다 !");
            }
        }
    }

    /** 도서 삭제 메서드*/
    public void deleteBook() {
        System.out.println("\n=== 도서 삭제 ===");
        System.out.print("삭제할 도서 코드를 입력해주세요 : ");
        int bookCode = sc.nextInt();
        sc.nextLine();

        Connection con = getConnection();
        int result = bookDAO.deleteBook(con, bookCode);
        close(con);

        if (result > 0) {
            System.out.println("\n도서 삭제가 완료되었습니다 !");
        } else {
            System.out.println("\n도서 삭제에 실패하였습니다. 다시 시도합니다 !");
        }
    }

    /**조건 별 도서 검색 메서드*/
    public static void searchBookMenu(Scanner sc) {
        System.out.println("=== 도서 검색 ===");
        System.out.println("1. 도서 제목으로 검색");
        System.out.println("2. 도서 코드로 검색");
        System.out.print("원하시는 메뉴의 번호를 선택해주세요 : ");

        int searchChoice = sc.nextInt();
        sc.nextLine();

        switch (searchChoice) {
            case 1:
                searchBooksByTitle();  // 도서 제목으로 검색
                break;
            case 2:
                searchBooksByCode();  // 도서 코드로 검색
                break;
            default:
                System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                searchBookMenu(sc);  // 잘못된 선택 시 다시 검색 메뉴로 돌아가기
                break;
        }
    }

    /** 도서 식별번호를 이용한 도서 찾기 메서드*/
    public static void searchBooksByCode() {
        System.out.println("\n=== 도서 검색 ===");
        System.out.print("검색할 도서의 코드를 입력해주세요 : ");

        int bookCode;
        try {
            bookCode = Integer.parseInt(sc.nextLine());  // 도서 코드를 정수로 변환
        } catch (NumberFormatException e) {
            System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
            return;  // 잘못된 입력이므로 메서드 종료
        }

        Connection con = getConnection();
        BookDTO book = bookDAO.searchBooksByCode(con, bookCode);
        close(con);

        if (book != null) {
            System.out.printf(
                    "도서 코드: %d\n도서 제목: %s\n도서 저자: %s\n도서 출판사: %s\n도서 장르: %s\n대여 상태: %s\n대여 횟수: %d\n",
                    book.getBookCode(),
                    book.getBookTitle(),
                    book.getBookAuthor(),
                    book.getBookPublisher(),
                    book.getBookGenre(),
                    book.isBookStatus() ? "대여 가능" : "대여 중",
                    book.getBorrowCount()
            );
            System.out.println("=================");  // 각 도서 사이 구분선
        } else {
            System.out.println("\n해당 코드의 도서를 찾을 수 없습니다. 다시 시도합니다 !");
        }
    }
    
    /** 도서 제목으로 도서 찾는 메서드*/
    public static void searchBooksByTitle() {
        System.out.println("=== 도서 제목으로 찾기 ===");
        System.out.print("검색할 도서 제목을 입력해주세요 : ");
        String title = sc.nextLine();

        // 입력된 도서 제목이 공백이거나 비어있는지 확인
        if (title == null || title.trim().isEmpty()) {
            System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
            return;  // 잘못된 입력이므로 메서드 종료
        }

        Connection con = getConnection();
        List<BookDTO> books = bookDAO.searchBooksByTitle(con, title);
        close(con);

        if (!books.isEmpty()) {
            for (BookDTO book : books) {
                System.out.printf(
                        "도서 코드: %d\n도서 제목: %s\n도서 저자: %s\n도서 출판사: %s\n도서 장르: %s\n대여 상태: %s\n대여 횟수: %d\n",
                        book.getBookCode(),
                        book.getBookTitle(),
                        book.getBookAuthor(),
                        book.getBookPublisher(),
                        book.getBookGenre(),
                        book.isBookStatus() ? "대여 가능" : "대여 중",
                        book.getBorrowCount()
                );
                System.out.println("==========================");  // 각 도서 사이 구분선
            }
        } else {
            System.out.println("\n해당 제목의 도서를 찾을 수 없습니다. 다시 시도합니다 !");
        }
    }

    /** 연체된 도서 목록을 조회하는 메서드 - 추후 필요시 사용*/
    public void searchOverdueBooks() {
        System.out.println("=== 연체된 도서 목록 조회 ===");

        Connection con = getConnection();
        List<BookDTO> books = bookDAO.getOverdueBooks(con);
        close(con);

        if (!books.isEmpty()) {
            for (BookDTO book : books) {
                System.out.printf(
                        "도서 코드: %d\n도서 제목: %s\n도서 저자: %s\n도서 출판사: %s\n도서 장르: %s\n대여 상태: %s\n대여 횟수: %d\n",
                        book.getBookCode(),
                        book.getBookTitle(),
                        book.getBookAuthor(),
                        book.getBookPublisher(),
                        book.getBookGenre(),
                        book.isBookStatus() ? "대여 가능" : "대여 중",
                        book.getBorrowCount()
                );
                System.out.println("==========================");  // 각 도서 사이 구분선임
            }
        } else {
            System.out.println("\n연체된 도서가 없습니다. 이전 메뉴로 돌아갑니다 !");
        }
    }

    private final RequestController requestController = new RequestController();

    /** 관리자 전용 도서 메뉴를 출력하는 메서드*/
    public void manageBooksMenu(Scanner sc) throws SQLException {
        boolean managing = true;

        while (managing) {
            System.out.println("=== 도서 관리 메뉴 ===");
            System.out.println("1. 도서 추가");
            System.out.println("2. 도서 수정");
            System.out.println("3. 도서 삭제");
            System.out.println("4. 요청된 도서 확인 및 추가");  // 새로운 메뉴 추가
            System.out.println("0. 이전 메뉴로 돌아가기");

            System.out.println("\n원하시는 메뉴의 번호를 선택해주세요 : ");
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
                    System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
            }
        }
    }


        /** 요청된 도서 목록을 보여주고 선택적으로 도서 목록에 추가하는 메서드*/
        public void showRequestedBooks(Scanner sc) throws SQLException {
            List<RequestDTO> requestedBooks = requestController.getRequestedBooks();

            if (requestedBooks.isEmpty()) {
                System.out.println("\n현재 요청된 도서가 없습니다. 이전 메뉴로 돌아갑니다 !");
                return;
            }

            System.out.println("=== 요청된 도서 목록 ===");
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

            System.out.println("도서를 추가하시겠습니까? \n 1: 예, 2: 아니오");
            System.out.print("원하시는 메뉴의 번호를 입력해주세요 : ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("추가할 도서 번호를 입력해주세요:");
                int bookChoice = sc.nextInt();
                sc.nextLine();

                if (bookChoice > 0 && bookChoice <= requestedBooks.size()) {
                    RequestDTO selectedBook = requestedBooks.get(bookChoice - 1);

                    // 도서를 DB에 추가
                    addRequestedBook(selectedBook);

                    // 요청 목록에서 삭제
                    boolean isDeleted = requestController.deleteRequestedBook(selectedBook.getRequestId());
                    if (isDeleted) {
                        System.out.println("=========================");
                        System.out.println("요청된 도서 " + selectedBook.getBookTitle() + "이 목록에서 삭제되었습니다.");
                        System.out.println("=========================");
                    } else {
                        System.out.println("\n요청된 도서 삭제에 실패하였습니다. 다시 시도합니다!");
                    }
                } else {
                    System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
                }
            }
        }

        /** 요청된 도서를 추가하는 메서드*/
        private void insertBookIntoDB(BookDTO bookDTO) throws SQLException {
            Connection con = getConnection();
            int result = 0;

            try {
                result = bookDAO.insertBook(con, bookDTO);
            } finally {
                close(con);
            }

            if (result > 0) {
                System.out.println("\n도서 추가가 완료되었습니다 !");
            } else {
                System.out.println("\n도서 추가에 실패하였습니다! ");
            }
        }

        /** 요청된 도서를 추가하는 메서드*/
        public void addRequestedBook(RequestDTO requestedBook) throws SQLException {
            BookDTO newBook = new BookDTO();
            newBook.setBookTitle(requestedBook.getBookTitle());
            newBook.setBookAuthor(requestedBook.getBookAuthor());
            newBook.setBookPublisher(requestedBook.getBookPublisher());
            newBook.setBookGenre(requestedBook.getBookGenre() != null ? requestedBook.getBookGenre() : "미정");
            newBook.setBookStatus(false);

            // DB에 도서 추가
            insertBookIntoDB(newBook);
        }

}
