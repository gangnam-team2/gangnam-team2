package com.ohgiraffers.user.controller;

import com.ohgiraffers.Application;
import com.ohgiraffers.usersession.UserSession;
import com.ohgiraffers.user.dao.UserDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class UserController {

    private static UserDAO userDAO = new UserDAO("src/main/resources/mapper/user-query.xml");


    /**
     * 사용자, 관리자로 나뉘어 회원가입을 진행하는 메서드
     */
    public static void totalsignup() {
        // user, admin 회원가입
        Scanner sc = new Scanner(System.in);
        boolean isSigningUp = true;

        while (isSigningUp) {
            try {
                System.out.println("=== 회원가입을 진행하겠습니다. ===");
                System.out.println("1. 사용자로 회원가입");
                System.out.println("2. 관리자로 회원가입");
                System.out.println("0. 이전 메뉴로 돌아가기");
                System.out.print("원하시는 메뉴의 번호를 선택해주세요 : ");
                int result = sc.nextInt();
                sc.nextLine();

                if (result == 0) {
                    System.out.println("=== 이전 메뉴로 돌아갑니다 ===");
                    System.out.println("1. 로그인 화면으로 이동하시겠습니까?");
                    System.out.println("2. 회원가입을 계속 진행하시겠습니까?");
                    System.out.print("원하시는 메뉴의 번호를 선택해주세요 :  ");
                    int subChoice = sc.nextInt();
                    sc.nextLine();

                    if (subChoice == 1) {
                        return; // 로그인 화면으로 돌아가기
                    } else if (subChoice == 2) {
                        continue; // 회원가입을 계속 진행
                    } else {
                        System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
                        // 잘못된 입력이므로 다시 회원가입 화면으로 돌아가기
                    }
                }

                switch (result) {
                    case 1:
                        // 사용자로 회원가입

                        boolean role = false;

                        // 특수문자 입력 못하게 정규식사용
                        String regex = "^[a-zA-Z0-9\\s]+$";


                        // 이름 입력
                        System.out.print("본인의 이름을 입력해주세요: ");
                        String name = sc.nextLine().trim();

                        if (name.isEmpty()) {
                            System.out.println("\n이름은 비워둘 수 없습니다. 다시 시도합니다!");
                            continue;
                        }

                        // 이름 공백문자 입력 못하게 하는 조건문
                        if (name.trim().isEmpty() || name.contains(" ")) {
                            System.out.println("\n공백 없이 입력해주세요. 다시 시도합니다!");
                            continue;
                        }

                        // 아이디 입력
                        System.out.print("아이디를 입력해주세요: ");
                        String id = sc.nextLine().trim();

                        if (id.isEmpty()) {
                            System.out.println("\n아이디 비워둘 수 없습니다. 다시 시도합니다! ");
                            continue;
                        } else if (!id.matches(regex)) {
                            System.out.println("\n아이디에 특수문자는 사용할 수 없습니다. 다시 시도합니다!");
                            continue;
                        }

                        // 아이디에 공백 입력 못하게 하는 조건문
                        if (id.trim().isEmpty() || id.contains(" ")) {
                            System.out.println("\n공백 없이 입력해주세요. 다시 시도합니다!");
                            continue;
                        }

                        // 비밀번호 입력
                        System.out.print("비밀번호를 입력 해주세요: ");
                        String pwd = sc.nextLine();
                        if (pwd.isEmpty()) {
                            System.out.println(" 비밀번호를 비워둘 수 없습니다. 다시 시도합니다!");
                            continue;
                        }
                        // 비밀번호에 공백 입력 못하게 하는 조건문
                        if (pwd.trim().isEmpty() || pwd.contains(" ")) {
                            System.out.println("\n공백 없이 입력해주세요. 다시 시도합니다!");
                            continue;
                        }


                        UserDTO newUser = new UserDTO(id, name, pwd, role);
                        if (userDAO.insertuser(getConnection(), newUser)) {
                            System.out.println("\n 회원가입 성공! ");
                            isSigningUp = false; // 회원가입 성공 후 이전 메뉴로 돌아가기
                        } else {
                            System.out.println("\n회원가입 실패! (T_T) 아이디가 이미 존재합니다.\n");
                            System.out.println( "다른 아이디를 입력해주세요. 다시 시도합니다 !");
                        }
                        break;

                    case 2: // 관리자로 회원가입
                        boolean role2 = true;
                        // 특수문자 입력 못하게 정규식사용
                        String regex1 = "^[a-zA-Z0-9\\s]+$";

                        // 이름 입력
                        System.out.print("\n본인의 이름을 입력해주세요: ");
                        String name1 = sc.nextLine().trim();

                        if (name1.isEmpty()) {
                            System.out.println("\n이름은 비워둘 수 없습니다. 다시 시도합니다 !");
                            continue;
                        }

                        // 이름에 공백 입력 못하게 하는 조건문
                        if (name1.trim().isEmpty() || name1.contains(" ")) {
                            System.out.println("\n공백 없이 입력해주세요. 다시 시도합니다 !");
                            continue;
                        }

                        // 아이디 입력
                        System.out.print("아이디를 입력해주세요: ");
                        String id1 = sc.nextLine();

                        if (id1.isEmpty()) {
                            System.out.println("\n아이디는 비워둘 수 없습니다. 다시 시도합니다 !");
                            continue;
                        } else if (!id1.matches(regex1)) {
                            System.out.println("\n아이디에 특수문자는 사용할 수 없습니다. 다시 시도합니다 !");
                            continue;
                        }

                        // 아이디에 공백 입력 못하게 하는 조건문
                        if (id1.trim().isEmpty() || id1.contains(" ")) {
                            System.out.println("\n공백 없이 입력해주세요. 다시 시도합니다 !");
                            continue;
                        }

                        // 비밀번호 입력
                        System.out.print("비밀번호를 입력 해주세요: ");
                        String pwd1 = sc.nextLine();

                        if (pwd1.isEmpty()) {
                            System.out.println("\n비밀번호는 비워둘 수 없습니다. 다시 시도합니다 !");
                            continue;
                        }

                        // 비밀번호에 공백 입력 못하게 하는 조건문
                        if (pwd1.trim().isEmpty() || pwd1.contains(" ")) {
                            System.out.println("\n공백 없이 입력해주세요. 다시 시도합니다 !");
                            continue;
                        }

                        UserDTO newUser2 = new UserDTO(id1, name1, pwd1, role2);
                        if (userDAO.insertuser(getConnection(), newUser2)) {
                            System.out.println("\n 회원가입 성공! ");
                            isSigningUp = false; // 회원가입 성공 후 이전 메뉴로 돌아가기
                            break;
                        } else {
                            System.out.println("\n회원가입 실패! (T_T) 아이디가 이미 존재합니다.\n");
                            System.out.println( "다른 아이디를 입력해주세요. 다시 시도합니다 !");
                            continue;
                        }

                    default:
                        System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
                }

            } catch (InputMismatchException e) {
                System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
                sc.nextLine();
            }
        }
    }


public int totallogin() {
    Scanner sc = new Scanner(System.in);
    boolean isLoggedIn = false;
    /**
     * 관리자, 사용자로 나뉘어 로그인하는 메서드
     */
        while (!isLoggedIn) {
            try {
                System.out.println("=== 로그인을 진행하겠습니다. ===");
                System.out.println("1. 로그인");
                System.out.println("0. 이전 메뉴로 돌아가기");
                System.out.print("원하시는 메뉴의 번호를 선택해주세요 : ");
                int result = sc.nextInt();
                sc.nextLine();

                if (result == 0) { // 0. 이전 메뉴로 돌아가기를 선택했을 경우
                    System.out.println("=== 이전 메뉴로 돌아가기 ===");
                    System.out.println("1. 회원가입으로 이동하시겠습니까?");
                    System.out.println("2. 로그인 화면으로 돌아가시겠습니까?");
                    System.out.print("원하시는 메뉴의 번호를 선택해주세요 : ");
                    int subChoice = sc.nextInt();
                    sc.nextLine();

                    if (subChoice == 1) {
                        totalsignup();
                    } else if (subChoice == 2) {
                        continue; // 로그인 화면으로 돌아가기
                    } else {
                        System.out.println("\n보기의 메뉴를 선택해주세요. 다시 시도합니다! ");
                        continue; // 다시 로그인 화면으로 돌아가기
                    }
                }

                switch (result) {
                    case 1:
                        System.out.println("=== 로그인 ===");
                        System.out.print("아이디를 입력해주세요: ");
                        String id = sc.nextLine();
                        System.out.print("비밀번호를 입력해주세요: ");
                        String pwd = sc.nextLine();

                        // 로그인 세션 저장
                        int a = login(id, pwd);
                        if (a == 0) {
                            System.out.println("\n다시 시도합니다. ");
                            break;
                        }

                        // 이후 로직 (예: 로그인 성공 시 isLoggedIn 변경)
                        if (UserSession.getUserDTO().getUserRole()) {
                            isLoggedIn = true;
                            return 1; // 관리자 로그인 성공 시 true 반환
                        } else {
                            isLoggedIn = true;
                            return 0; // 일반 사용자 로그인 성공 시 false 반환
                        }

                    default:
                        System.out.println("\n잘못된 입력입니다. 다시 시도해주세요.");
                }

            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                sc.nextLine();
            }
        }// 기본적으로 실패로 반환
        return 2;
    }

    /**
     * 로그인 세션에 저장하기 위해 필요한 메서드
     */
    public int login(String Id, String password) {

        UserDTO loginUser = new UserDTO(Id, password);
        UserSession userSession = new UserSession();

        if (userDAO.selectuser(getConnection(), loginUser)) {
            userSession.setUserDTO(loginUser);          // 여기서 로그인된 사용자 정보를 세션에 저장함
            System.out.println("\n환영합니다 " + loginUser.getUserId() + "님 !");
            return 1;
        } else {
            System.out.println("\n로그인에 실패하였습니다. 아이디나 비밀번호를 확인해주세요.");

        }
        return 0;
    }

    public void deleteuser() {
        UserSession userSession = new UserSession();
        Scanner sc = new Scanner(System.in);
        boolean isLoggedIn = false;

        // 회원탈퇴 로직
        while (!isLoggedIn) {
            System.out.println("===  회원 탈퇴를 진행하겠습니다. ===");
            System.out.println(" 아이디 비밀번호를 다시 확인하겠습니다. ");


            System.out.print("아이디를 입력해주세요: ");
            String id1 = sc.nextLine();
            System.out.print("비밀번호를 입력해주세요: ");
            String pwd1 = sc.nextLine();
            System.out.println("\n정말로 탈퇴 하시겠습니까 ? ");
            System.out.println(" 1. 네  \n 2. 아니요 ");
            System.out.print("원하시는 메뉴의 번호를 선택해주세요 : ");

            int subChoice = sc.nextInt();
            sc.nextLine();

            UserDTO loginUser1 = new UserDTO(id1, pwd1);

            if (subChoice == 1) {
                if (userDAO.deleteuser(getConnection(), loginUser1)) {
                    System.out.println("\n회원탈퇴 성공!");
                    System.out.println("도서대여 프로그램을 이용해주셔서 감사합니다 😊 ");
                    break;

                } else {
                    System.out.println("\n회원탈퇴 실패 ! 아이디와 비밀번호를 확인해주세요. \n다시 시도합니다 ! ");
                    isLoggedIn = false;
                }
            } else if (subChoice == 2) {
                System.out.println("\n회원탈퇴 취소 ! 회원탈퇴를 종료합니다.\n 감사합니다 !  ");
                break;

            } else {
                System.out.println("\n회원탈퇴 실패! 아이디와 비밀번호를 확인해주세요.\n다시 시도합니다 !");
                isLoggedIn = false;
            }
        }
    }
}









