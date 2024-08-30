package com.ohgiraffers;

import com.ohgiraffers.book.controller.BestSellersController;
import com.ohgiraffers.user.controller.UserController;
import com.ohgiraffers.book.controller.BookController;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        UserController usercontroller = new UserController();

        while (running) {
            System.out.println("\n== 도서 대여 프로그램 ==");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("0. 종료");
            System.out.print("선택: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    usercontroller.totalsignup();
                    break;
                case 2:
                    // 로그인 및 해당 역할에 따른 메뉴 표시
                    boolean userRole = usercontroller.totallogin();

                    if (userRole != null) {
                        displayMenu(sc, userRole);
                    }
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    running = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }

    // 역할에 따른 메뉴 출력 및 컨트롤러 호출
    private static void displayMenu(Scanner sc, UserRole userRole) {
        BookController bookController = new BookController();
        BestSellersController bestSellersController = new BestSellersController();
        boolean isRunning = true;

        while (isRunning) {
            if (userRole == UserRole.ADMIN) {
                System.out.println("\n== 관리자 메뉴 ==");
                System.out.println("1. 도서 관리");
                System.out.println("2. 도서 검색 (연체된 도서 목록 포함)");
                System.out.println("3. 사용자 관리");
                System.out.println("4. 베스트셀러 목록");
                System.out.println("5. 회원탈퇴");
                System.out.println("0. 로그아웃");
            } else {
                System.out.println("\n== 사용자 메뉴 ==");
                System.out.println("1. 도서 검색");
                System.out.println("2. 대여 및 반납");
                System.out.println("3. 베스트셀러 목록");
                System.out.println("4. 도서 요청");
                System.out.println("5. 마이페이지");
                System.out.println("6. 회원탈퇴");
                System.out.println("0. 로그아웃");
            }

            System.out.print("선택: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    if (userRole == UserRole.ADMIN) {
                        manageBooksMenu(sc, bookController);
                    } else {
                        bookController.searchBooksByTitle();
                    }
                    break;
                case 2:
                    if (userRole == UserRole.ADMIN) {
                        bookController.searchOverdueBooks();
                    } else {
                        // 대여 및 반납 기능 구현
                    }
                    break;
                case 3:
                    if (userRole == UserRole.ADMIN) {
                        ManagerController.displayManager();
                    } else {
                        bestSellersController.showBestSellers();
                    }
                    break;
                case 4:
                    if (userRole == UserRole.ADMIN) {
                        // 베스트셀러 관리 선택지를 관리자에게 보이지 않도록 제거했습니다.
                        bestSellersController.showBestSellers();
                    } else {
                        RequestController.bookRequest();
                    }
                    break;
                case 5:
                    if (userRole == UserRole.USER) {
                        // 마이페이지 기능
                    } else {
                        // 관리자 회원탈퇴
                    }
                    break;
                case 6:
                    if (userRole == UserRole.USER) {
                        // 사용자 회원탈퇴
                    } else {
                        System.out.println("잘못된 선택입니다.");
                    }
                    break;
                case 0:
                    System.out.println("로그아웃 합니다.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    // 관리자 도서 관리 메뉴
    private static void manageBooksMenu(Scanner sc, BookController bookController) {
        boolean managing = true;

        while (managing) {
            System.out.println("\n== 도서 관리 메뉴 ==");
            System.out.println("1. 도서 추가");
            System.out.println("2. 도서 수정");
            System.out.println("3. 도서 삭제");
            System.out.println("0. 이전 메뉴로 돌아가기");

            System.out.print("선택: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    bookController.insertBook();
                    break;
                case 2:
                    bookController.updateBook();
                    break;
                case 3:
                    bookController.deleteBook();
                    break;
                case 0:
                    managing = false;
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }
}




