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

    public static void main(String[] args) throws InterruptedException {
        UserController usercontroller = new UserController();

        Scanner sc = new Scanner(System.in);
        boolean running = true; // 반복문을 돌리기 위한 변수.
            // 메인 페이지
//            System.out.print("📕");
//            Thread.sleep(500);
//            System.out.print("📖");
//            Thread.sleep(500);
//            System.out.print("📕");
//            Thread.sleep(500);
//            System.out.print("📖");
//            Thread.sleep(500);
//            System.out.print("📕");
//            Thread.sleep(500);
//            System.out.print("📖");
//            Thread.sleep(500);
            while (running) {
                try {
                    // 서연님의 로그인, 회원가입 선택창을 시작으로 프로그램이 구현됩니다. 아주 막중!!!하였다!!!
                    System.out.print("\n=== 도서 대여 프로그램 ===\n");
                    System.out.println("1. 회원가입");
                    System.out.println("2. 로그인");
                    System.out.println("0. 종료");
                    System.out.print("원하시는 메뉴의 번호를 선택해주세요 (❁´◡`❁): ");
                    int choice = sc.nextInt();
                    sc.nextLine(); // 개행 받아 먹기 냠냠..

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
                            // 1은 관리자, 0은 일반 사용자, 1보다 큰 수는 로그인 실패.
                            if (userRole == 1 || userRole == 0) {
                                displayMenu(sc, userRole);
                            } else if (userRole == 2) {
                                System.out.println( " \n로그인 실패하셨습니다. (T_T) " );

                            }
                            break;
                        case 0:
                            System.out.println("\n === 프로그램을 종료합니다. ===");
                            System.out.println("\n도서대여 프로그램을 이용해주셔서 감사합니다. ^0^ ");
                            running = false;
                            break;
                        default:
                            System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
                            break;


                    }
                } catch (InputMismatchException e) {
                    System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
                    sc.nextLine();
                }
            }
    }

    // 역할에 따른 메뉴 출력 및 컨트롤러 호출
    private static void displayMenu(Scanner sc, int userRole) { // 로그인 이후 보여질 선택창 메소드, 관리자와 사용자로 나뉜다.
        BookController bookController = new BookController();
        BestSellersController bestSellersController = new BestSellersController();
        ManagerController managerController = new ManagerController();
        MypageController mypageController = new MypageController();
        BorrowRecordController borrowRecordController = new BorrowRecordController();
        UserController usercontroller = new UserController(); // 클래스를 객체로 선언 후 해당 클래스의 메소드를 사용할 준비를 마쳤다.

        boolean isRunning = true; // 반복문을 돌리기 위한 불린형 변수 선언 및 초기화
        String logind = UserSession.getUserDTO().getUserId(); // 현재 로그인한 회원의 아이디 DTO 정보를 담은 스트링 변수
        while (isRunning) { // 무한~ 루프~!🙌
            try {
                // 관리자와 사용자 메뉴를 나눔
                if (userRole == 1) {  // 관리자
                    System.out.println("\n|== 관리자 전용 메뉴 ==|");
                    System.out.println("| 1. 도서 관리        |");
                    System.out.println("| 2. 연체된 도서 목록  |");
                    System.out.println("| 3. 사용자 관리      |");
                    System.out.println("| 4. 베스트셀러 목록   |");
                    System.out.println("| 5. 회원탈퇴         |");
                    System.out.println("| 0. 로그아웃         |");
                    System.out.println("|===================|");

                } else if (userRole == 0) {  // 일반 사용자
                    System.out.println("\n|== 회원 전용 메뉴 ==|");
                    System.out.println("| 1. 도서 검색      |");
                    System.out.println("| 2. 대여 및 반납   |");
                    System.out.println("| 3. 베스트셀러 목록 |");
                    System.out.println("| 4. 도서 요청      |");
                    System.out.println("| 5. 마이페이지      |");
                    System.out.println("| 6. 회원탈퇴       |");
                    System.out.println("| 0. 로그아웃       |");
                    System.out.println("|==================|");
                }

                System.out.print("원하시는 메뉴의 번호를 선택해주세요(❁´◡`❁): ");
                if (!sc.hasNextInt()) {  // 숫자 입력이 아닌 경우 예외 처리
                    System.out.println("\n잘못된 메뉴를 선택하셨습니다. 다시 시도합니다. ");
                    sc.next();  // 잘못된 입력을 버퍼에서 제거
                    continue;  // 메뉴 다시 표시
                }

                int choice = sc.nextInt();
                sc.nextLine();  // 남아있는 입력 버퍼 비우기

                switch (choice) {
                    case 1:
                        if (userRole == 1) {// 동규 팀장님의 도서 관리 메뉴
                            System.out.println("\n관리자 " + logind + "님! 도서 관리 메뉴를 선택하셨습니다.^0^");
                            bookController.manageBooksMenu(sc);
                        } else { // 동규 팀장님의 도서 검색 메뉴
                            System.out.println("\n사용자 " + logind + "님! 도서 검색 메뉴를 선택하셨습니다.^0^");
                            bookController.searchBookMenu(sc);
                        }
                        break;

                    case 2:
                        if (userRole == 1) {
                            System.out.println("\n관리자 " + logind + "님! 연체 도서 목록 메뉴를 선택하셨습니다.^0^");
                            borrowRecordController.overDueBooks();
                        } else {
                            System.out.println("\n사용자 " + logind + "님! 대여 및 반납 기능 메뉴를 선택하셨습니다.^0^");
                            System.out.println("\n=== 대여 및 반납 메뉴 ===");
                            System.out.println("1. 대여   2. 반납");
                            System.out.print("원하시는 메뉴의 번호를 선택해주세요(❁´◡`❁): ");
                            if (!sc.hasNextInt()) {
                                System.out.println("잘못된 입력입니다. 다시 시도해주세요!");
                                sc.next();  // 잘못된 입력을 처리
                                continue;  // 메뉴로 돌아가기
                            }
                            int choice2 = sc.nextInt();
                            sc.nextLine();  // 남은 입력 제거
                            switch (choice2) {
                                case 1:
                                    borrowRecordController.rentBook();
                                    break;
                                case 2:
                                    borrowRecordController.returnBook();
                                    break;
                                default:
                                    System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
                            }
                        }
                        break;

                    case 3:
                        if (userRole == 1) {
                            System.out.println("\n관리자 " + logind + "님! 사용자 관리 메뉴를 선택하셨습니다.^0^");
                            managerController.displayManager();
                        } else {
                            System.out.println("\n사용자 " + logind + "님! 베스트셀러 목록 메뉴를 선택하셨습니다.^0^");
                            bestSellersController.showBestSellers(sc);
                        }
                        break;

                    case 4:
                        if (userRole == 1) {
                            System.out.println("\n관리자 " + logind + "님! 베스트셀러 목록 메뉴를 선택하셨습니다.^0^");
                            bestSellersController.showBestSellers(sc);
                        } else {
                            System.out.println("\n사용자" + logind + "님! 도서 요청 선택 메뉴를 선택하셨습니다.^0^");
                            RequestController.insertRequestedBook();
                        }
                        break;

                    case 5:
                        if (userRole == 0) {
                            System.out.println("\n사용자"+logind + "님! 마이페이지 메뉴를 선택하셨습니다.^0^");
                            System.out.println("1. 대여중인 도서 목록");
                            System.out.println("2. 도서 대여 기록");
                            System.out.println("3. 대여 취소");
                            System.out.println("4. 연체 목록 조회");
                            System.out.println("5. 비밀번호 변경");
                            System.out.println("0. 이전으로 돌아가기");
                            System.out.print("선택하실 메뉴 : ");

                            if (!sc.hasNextInt()) {
                                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                                sc.next();  // 잘못된 입력을 처리
                                continue;  // 메뉴로 돌아가기
                            }

                            int choice3 = sc.nextInt();
                            sc.nextLine();  // 남은 입력 제거
                            switch (choice3) {
                                case 1:
                                    mypageController.currentBorrowBookList();
                                    break;
                                case 2:
                                    mypageController.allBorrowBookList();
                                    break;
//                                case 3:
//                                    mypageController.updateRequestBook();
//                                    break;
                                case 3:
                                    mypageController.myOverDueBooks();
                                    break;
                                case 4:
                                    mypageController.pwdUpdate();
                                    break;
                                case 0:
                                    continue;
                                default:
                                    System.out.println("다시 시도해 주세요.");
                            }
                        } else if (userRole == 1) {
                            System.out.println("관리자 " + logind + "님 회원탈퇴 기능 선택");
                            usercontroller.deleteuser();
                            isRunning = false;
                        }
                        break;

                    case 6:
                        if (userRole == 0) {
                            System.out.println(logind + "님 회원탈퇴 기능 선택");
                            usercontroller.deleteuser();
                            isRunning = false;
                        } else {
                            System.out.println("잘못된 선택입니다. 다시 시도해 주세요.");
                        }
                        break;

                    case 0:
                        System.out.println("로그아웃 합니다.");
                        isRunning = false;
                        break;

                    default:
                        System.out.println("잘못된 선택입니다.");
                        break;
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

