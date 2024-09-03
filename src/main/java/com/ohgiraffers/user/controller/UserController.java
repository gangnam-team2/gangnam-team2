package com.ohgiraffers.user.controller;

import com.ohgiraffers.user.dao.UserDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class UserController {

    private static UserDAO userDAO = new UserDAO("src/main/resources/mapper/user-query.xml");

    public static void totalsignup() {
        // user, admin 회원가입
        Scanner sc = new Scanner(System.in);
        boolean isSigningUp = true;

        while (isSigningUp) {
            try {
                System.out.println("----- 회원가입을 진행하겠습니다. -----");
                System.out.println("1. 사용자로 회원가입");
                System.out.println("2. 관리자로 회원가입");
                System.out.println("3. 이전 메뉴로 돌아가기");
                System.out.println("0. 종료 ");
                System.out.print("원하는 서비스의 번호를 입력해주세요: ");
                int result = sc.nextInt();
                sc.nextLine();

                if (result == 3) {
                    System.out.println("1. 로그인 화면으로 이동하시겠습니까?");
                    System.out.println("2. 회원가입을 계속 진행하시겠습니까?");
                    System.out.print("선택: ");
                    int subChoice = sc.nextInt();
                    sc.nextLine();

                    if (subChoice == 1) {
                        return; // 로그인 화면으로 돌아가기
                    } else if (subChoice == 2) {
                        continue; // 회원가입을 계속 진행
                    } else {
                        System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                        continue; // 잘못된 입력이므로 다시 회원가입 화면으로 돌아가기
                    }
                }

                System.out.print("본인의 이름을 입력해주세요: ");
                String name = sc.nextLine();
                System.out.print("아이디를 입력해주세요: ");
                String id = sc.nextLine();
                System.out.print("비밀번호를 입력 해주세요: ");
                String pwd = sc.nextLine();

                switch (result) {
                    case 1: // 사용자로 회원가입
                        boolean role = false;
                        UserDTO newUser = new UserDTO(id, name, pwd, role);
                        if (userDAO.insertuser(getConnection(), newUser)) {
                            System.out.println("회원가입 성공!");
                            isSigningUp = false; // 회원가입 성공 후 이전 메뉴로 돌아가기
                        } else {
                            System.out.println("회원가입 실패! 아이디가 이미 존재합니다.");
                        }
                        break;

                    case 2: // 관리자로 회원가입
                        boolean role2 = true;
                        UserDTO newUser2 = new UserDTO(id, name, pwd, role2);
                        if (userDAO.insertuser(getConnection(), newUser2)) {
                            System.out.println("회원가입 성공!");
                            isSigningUp = false; // 회원가입 성공 후 이전 메뉴로 돌아가기
                        } else {
                            System.out.println("회원가입 실패! 아이디가 이미 존재합니다.");
                        }
                        break;
                    case 0:
                        System.out.println(" 프로그램을 종료하겠습니다. ");
                        isSigningUp = false;
                        break;

                    default:
                        System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                }
            }catch (InputMismatchException e) {
                    System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                    sc.nextLine();
            }
        }
    }

    public boolean totallogin() {
        Scanner sc = new Scanner(System.in);
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            try {

                System.out.println("----- 로그인을 진행하겠습니다. -----");
                System.out.println("1. 로그인");
                System.out.println("2. 회원탈퇴");
                System.out.println("3. 이전 메뉴로 돌아가기");
                System.out.println("0. 종료");
                System.out.print("원하는 서비스의 번호를 입력해주세요: ");
                int result = sc.nextInt();
                sc.nextLine();

                // 어차피 로그인 화면에 들어 왔으면 로그인 이전 메뉴라 해봤자 회원가입 로그인이니
                // 그냥 이전 메뉴를 선택하면 회원가입으로 아님 다시 로그인으로 선택지 줘버리기
                if (result == 3) {
                    System.out.println("1. 회원가입으로 이동하시겠습니까?");
                    System.out.println("2. 로그인 화면으로 돌아가시겠습니까?");
                    System.out.print("선택: ");
                    int subChoice = sc.nextInt();
                    sc.nextLine();

                    if (subChoice == 1) {
                        totalsignup();
                        return false; // 회원가입 후 이전 메뉴로 돌아감
                    } else if (subChoice == 2) {
                        continue; // 로그인 화면으로 돌아가기
                    } else {
                        System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                        continue; // 다시 로그인 화면으로 돌아가기
                    }
                }

                System.out.print("아이디를 입력해주세요: ");
                String id = sc.nextLine();
                System.out.print("비밀번호를 입력해주세요: ");
                String pwd = sc.nextLine();

                UserDTO loginUser = new UserDTO(id, pwd);

                switch (result) {
                    case 1:
                        if (userDAO.selectuser(getConnection(), loginUser)) {
                            if (loginUser.getUserRole()) {
                                System.out.println("관리자 로그인 성공!");
                                isLoggedIn = true;
                                return true; // 관리자 로그인 성공 시 true 반환
                            } else {
                                System.out.println("사용자 로그인 성공!");
                                isLoggedIn = true;
                                return false; // 일반 사용자 로그인 성공 시 false 반환
                            }
                        } else {
                            System.out.println("로그인 실패! 잘못된 아이디 또는 비밀번호입니다.");
                        }
                        break;

                    case 2:
                        // 회원탈퇴
                        if (userDAO.deleteuser(getConnection(), loginUser)) {
                            System.out.println("회원탈퇴 성공!");
                        } else {
                            System.out.println("회원탈퇴 실패! 아이디와 비밀번호를 확인해주세요.");
                        }
                        break;

                    case 3:
                        System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                        break;

                    case 0:
                        System.out.println(" 프로그램을 종료합니다. ");
                        isLoggedIn = false;

                        break;

                    default:
                        System.out.println(" 잘못된 선택 입니다. ");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                sc.nextLine();
            }
        } return false; // 기본적으로 실패로 반환
    }
}
