package com.ohgiraffers;

import com.ohgiraffers.book.controller.BestSellersController;
import com.ohgiraffers.book.controller.BookController;
import com.ohgiraffers.borrowrecord.controller.BorrowRecordController;
import com.ohgiraffers.manager.controller.ManagerController;
import com.ohgiraffers.mypage.controller.MypageController;
import com.ohgiraffers.request.controller.RequestController;
import com.ohgiraffers.user.controller.UserController;
import com.ohgiraffers.usersession.UserSession;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        UserController usercontroller = new UserController();

        Scanner sc = new Scanner(System.in);
        boolean running = true;

            // 메인 페이지
            while (running) {
                try {
                    System.out.println("\n== 도서 대여 프로그램 ==");
                    System.out.println("1. 회원가입");
                    System.out.println("2. 로그인");
                    System.out.println("0. 종료");
                    System.out.print("선택: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            // 사용자, 관리자 회원가입
                            // insert
                            usercontroller.totalsignup();
                            continue;


                        case 2:
                            // 로그인 및 해당 역할에 따른 메뉴 표시
                            // select
                            int userRole = usercontroller.totallogin();
                            if (userRole == 1 || userRole == 0) {
                                displayMenu(sc, userRole);
                            } else if (userRole == 2) {
                                System.out.println( " 로그인 실패 !" );

                            }
                            break;
                        case 0:
                            System.out.println("프로그램을 종료합니다.");
                            running = false;
                            break;
                        default:
                            System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                            break;


                    }
                } catch (InputMismatchException e) {
                    System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                    sc.nextLine();
                }
            }
    }

    // 역할에 따른 메뉴 출력 및 컨트롤러 호출
    private static void displayMenu(Scanner sc, int userRole) {
        BookController bookController = new BookController();
        BestSellersController bestSellersController = new BestSellersController();
        ManagerController managerController = new ManagerController();
        MypageController mypageController   = new MypageController();
        BorrowRecordController borrowRecordController = new BorrowRecordController();
        UserController usercontroller = new UserController();
        boolean isRunning = true;
        String logind = UserSession.getUserDTO().getUserId();

        while (isRunning) {
            try {
                if (userRole == 1) {                                     // 관리자
                    System.out.println("\n== 관리자 메뉴 ==");
                    System.out.println("1. 도서 관리");
                    System.out.println("2. 연체된 도서 목록");
                    System.out.println("3. 사용자 관리");
                    System.out.println("4. 베스트셀러 목록");
                    System.out.println("5. 회원탈퇴");
                    System.out.println("0. 로그아웃");
                } else if(userRole == 0) {                                            // 일반 사용자
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
                        if (userRole == 1) {
                            System.out.println("관리자 " + logind + "님 도서 관리 메뉴 선택");
                            bookController.manageBooksMenu(sc);
                        } else if (userRole == 0) {
                            System.out.println(logind +"님 도서 검색 메뉴 선택");
                            bookController.searchBookMenu(sc);
                        }
                        break;
                    case 2:
                        if (userRole == 1) {
                            System.out.println("관리자 " + logind + "님 연체 도서 목록 선택");
                           // bookController.searchOverdueBooks();  // 연체 도서 목록은 관리자 기능
                            borrowRecordController.overDueBooks();
                        } else {
                            System.out.println("사용자: 대여 및 반납 기능 선택");
                            System.out.println("1. 대여   2. 반납");
                            System.out.print("메뉴를 선택해주세요 : ");
                            int choice2 = sc.nextInt();
                            switch (choice2) {
                                case 1:
                                    borrowRecordController.rentBook();
                                    break;
                                case 2:
                                    borrowRecordController.returnBook();
                                    break;
                                default:
                                    System.out.println("잘못 선택하셨습니다. 다시 시도해 주세요.");
                            }
                        }
                        break;
                    case 3:
                        if (userRole == 1) {
                            System.out.println("관리자 " + logind + "님 사용자 관리 메뉴 선택");
                            managerController.displayManager();
                        } else if(userRole == 0){
                            System.out.println(logind +"님 베스트셀러 목록 선택");
                            bestSellersController.showBestSellers();
                        }
                        break;
                    case 4:
                        if (userRole == 1) {
                            System.out.println("관리자 " + logind + "님 베스트셀러 목록 선택");
                            bestSellersController.showBestSellers();  // 베스트셀러 목록은 관리자도 볼 수 있음
                        } else if(userRole == 0) {
                            System.out.println(logind +"님 도서 요청 선택");
                            RequestController.insertRequestedBook();
                        }
                        break;
                    case 5:

                        if (userRole == 0) {
                            System.out.println(logind + "님 마이페이지 선택");
                            System.out.println("1. 현재 대여 신청 책 목록\n" + "2. 전체 대여 목록\n" + "3. 대여 취소\n" + "4. 연체 목록 조회\n"+ "5. 비밀번호 변경");
                            int choice3 = sc.nextInt();
                            switch (choice3) {
                                case 1: mypageController.currentBorrowBookList(); break;
                                case 2: mypageController.allBorrowBookList(); break;
                                case 3: mypageController.updateRequestBook(); break;
                                case 4: mypageController.myOverDueBooks(); break;
                                case 5: mypageController.pwdUpdate(); break;
                                default:
                                    System.out.println("다시 시도해 주세요.");
                            }
                        } else {
                            System.out.println("관리자 " + logind + "님 회원탈퇴 기능 선택");
                            usercontroller.deleteuser();
                            isRunning = false;
                            break;

                        }
                        break;
                    case 6:
                        if (userRole == 0) {
                            System.out.println(logind +"님 회원탈퇴 기능 선택");
                            usercontroller.deleteuser();
                            isRunning = false;
                            break;

                            // 사용자 회원탈퇴 기능 구현
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
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 올바른 메뉴를 선택해주세요.");
                sc.nextLine();  // 잘못된 입력 비워버리기

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
