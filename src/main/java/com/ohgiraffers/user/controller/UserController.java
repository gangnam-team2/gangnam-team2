package com.ohgiraffers.user.controller;
import com.ohgiraffers.user.dao.UserDAO;
import com.ohgiraffers.user.dto.UserDTO;

import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class UserController {


    private static UserDAO userDAO = new UserDAO("src/main/resources/mapper/user-query.xml");


    public static void totalsignup() {
        // user, admin 회원가입

        loop: while (true) {


            System.out.println(" ----- 회원가입을 진행하겠습니다.-----");
            Scanner sc = new Scanner(System.in);
            System.out.println(" 1. 사용자로 회원가입 ");
            System.out.println(" 2. 관리자로 회원가입 ");
            System.out.print(" 원하는 서비스의 번호를 입력해주세요 : ");
            int result = sc.nextInt();
            sc.nextLine();

            System.out.print(" 본인의 이름을 입력해주세요 : ");
            String name = sc.nextLine();
            System.out.print(" 아이디를 입력해주세요 : ");
            String id = sc.nextLine();
            System.out.print(" 비밀번호를 입력 해주세요 : ");
            String pwd = sc.nextLine();

            boolean singupuser = false;


            switch (result) {

                case 1: // 사용자로 회원가입
                    boolean role = true; // 사용자(true)
                    UserDTO newuser = new UserDTO(id, name, pwd, role);
                    if (userDAO.insertuser(getConnection(), newuser)) {
                        System.out.println(" 회원가입 성공 ! ");
                        break loop;
                    } else {
                        System.out.println(" 회원가입 실패! 아이디가 이미 존재합니다.");
                    }
                    break ;


                case 2: // 관리자로 회원가입
                    boolean role2 = false; //role -> 관리자(false)로 일단 줌
                    UserDTO newuser2 = new UserDTO(id, name, pwd, role2);
                    if (userDAO.insertuser(getConnection(), newuser2)) {
                        System.out.println(" 회원가입 성공 !");
                    } else {
                        System.out.println(" 회원가입 실패 ! 아이디가 이미 존재합니다.");
                    }
                    break;

                default:
                    break;
            }
        }
    }

    public boolean totallogin() {
        // 사용자, 관리자 로그인 및 회원탈퇴

        while (true) {

        System.out.println("----- 로그인을 진행하겠습니다. -----");
        Scanner sc = new Scanner(System.in);
        System.out.println("1. 사용자로 로그인");
        System.out.println("2. 관리자로 로그인");
        System.out.println("3. 회원탈퇴");
        System.out.print("원하는 서비스의 번호를 입력해주세요: ");
        int result = sc.nextInt();
        sc.nextLine();
        System.out.print("아이디를 입력해주세요: ");
        String id = sc.nextLine();
        System.out.print("비밀번호를 입력해주세요: ");
       
        String pwd = sc.nextLine();

        UserDTO loginUser = new UserDTO(id, pwd);
        UserDAO userDAO = new UserDAO("src/main/resources/mapper/user-query.xml"); // UserDAO 인스턴스 생성



            switch (result) {
                case 1:
                    // 사용자로 로그인
                    loginUser.setUserRole(false);  // 사용자로 로그인 시 false로 설정
                    if (userDAO.selectuser(getConnection(), loginUser)) {
                        System.out.println("사용자 로그인 성공!");
                        return true;// 사용자 로그인 성공
                    } else {
                        System.out.println("로그인 실패! 잘못된 아이디 또는 비밀번호입니다.");
                        totallogin();
                    }
                    break;

                case 2:
                    // 관리자로 로그인
                    loginUser.setUserRole(true);  // 관리자로 로그인 시 true로 설정
                    if (userDAO.selectuser(getConnection(), loginUser)) {
                        System.out.println("관리자 로그인 성공!");
                        return true;  // 관리자 로그인 성공
                    } else {
                        System.out.println("로그인 실패! 잘못된 아이디 또는 비밀번호입니다.");
                    }
                    break;

                case 3:
                    // 회원탈퇴
                    if (userDAO.deleteuser(getConnection(), loginUser)) {
                        System.out.println("회원탈퇴 성공!");
                    } else {
                        System.out.println("회원탈퇴 실패! 아이디와 비밀번호를 확인해주세요.");
                    }
                    break;

                default:
                    System.out.println("잘못된 입력입니다. 프로그램을 종료합니다.");
                    break;
            }

            return false;  // 기본적으로 실패로 반환
        }
    }
}