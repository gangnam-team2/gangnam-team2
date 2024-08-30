package com.ohgiraffers;

import com.ohgiraffers.book.user.UserController;
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

                    if ( userRole != null ) {
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
                System.out.println("3. 사용자 관리");  // 서현준
                System.out.println("4. 베스트셀러 관리");
                System.out.println("5. 회원탈퇴");
                System.out.println("0. 로그아웃");
            } else {
                System.out.println("\n== 사용자 메뉴 ==");
                System.out.println("1. 도서 검색");
                System.out.println("2. 대여 및 반납");
                System.out.println("3. 베스트셀러 목록");
                System.out.println("4. 도서 요청"); // 서현준
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
                        // 도서 관리 (추가, 수정, 삭제)
                        manageBooksMenu(sc, bookController);
                    } else {
                        // 도서 검색
                        bookController.searchBooksByTitle();
                    }
                    break;
                case 2:
                    if (userRole == UserRole.ADMIN) {
                        // 연체된 도서 목록 포함 검색
                        bookController.searchOverdueBooks();
                    } else {
                        // 대여 및 반납 기능 구현

                    }
                    break;
                case 3:
                    if (userRole  == UserRole.ADMIN) {
                        // (관리자 모드) 사용자 관리 / 1. 회원 목록 조회 / 2. 도서를 대여 중인 회원과 그 도서 목록 조회 / 3. 연체 이력이 있는 회원 목록 조회
                        ManagerController.displayManager();
                    } else {
                        // 베스트셀러 목록 조회
                        bestSellersController.showBestSellersByPeriod();
                    }
                    break;
                case 4:
                    if (userRole == UserRole.ADMIN) {
                        manageBestSellersMenu(sc, bestSellersController);
                    } else {
                        // (사용자 모드) 도서 요청 / 도서관에 없는 책을 요청 도서 목록에 인설트
                        RequestController.bookRequest();
                    }
                    break;
                case 5:
                    if (userRole == UserRole.USER) {
                        // 마이페이지

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

    // 관리자 베스트셀러 관리 메뉴
    private static void manageBestSellersMenu(Scanner sc, BestSellersController bestSellersController) {
        boolean managing = true;

        while (managing) {
            System.out.println("\n== 베스트셀러 관리 메뉴 ==");
            System.out.println("1. 베스트셀러 추가");
            System.out.println("2. 베스트셀러 목록 조회 (기간별)");
            System.out.println("3. 모든 베스트셀러 목록 조회");
            System.out.println("0. 이전 메뉴로 돌아가기");

            System.out.print("선택: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    bestSellersController.addBestSeller();
                    break;
                case 2:
                    bestSellersController.showBestSellersByPeriod();
                    break;
                case 3:
                    bestSellersController.showAllBestSellers();
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





