package com.ohgiraffers.user.controller;

import com.ohgiraffers.user.dao.UserDAO;
import com.ohgiraffers.user.dto.UserDTO;

import static com.ohgiraffers.common.JDBCTemplate.*;

import java.util.Scanner;

public class UserController {


    private UserDAO userDAO = new UserDAO("src/main/resources/mapper/user-query.xml");


    public void totalsignup() {
        // user, admin 회원가입


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
                boolean role1 = true; // 사용자(true)
                UserDTO newuser1 = new UserDTO(id, name, pwd, role1);
                if (userDAO.insertuser(getConnection(),newuser1)) {
                    System.out.println(" 회원가입 성공 ! ");
                } else {
                    System.out.println(" 회원가입 실패! 아이디가 이미 존재합니다.");
                }
                break;


            case 2: // 관리자로 회원가입
                boolean role2 = false; //role -> 관리자(false)로 일단 줌
                UserDTO newuser2 = new UserDTO(id, name, pwd, role2);
                if (userDAO.insertuser(getConnection(),newuser2)) {
                    System.out.println(" 회원가입 성공 !");
                } else {
                    System.out.println(" 회원가입 실패 ! 아이디가 이미 존재합니다.");
                }
                break;
            default:
                break;
        }
    }


    public boolean totallogin() {
        // 사용자, 관리자 로그인

        System.out.println(" ----- 로그인을 진행하겠습니다.-----");
        Scanner sc = new Scanner(System.in);
        System.out.println(" 1. 사용자로 로그인 ");
        System.out.println(" 2. 관리자로 로그인 ");
        System.out.println(" 3. 회원탈퇴 ");
        System.out.print(" 원하는 서비스의 번호를 입력해주세요 : ");
        int result = sc.nextInt();
        sc.nextLine();
        System.out.print(" 본인의 이름을 입력해주세요 : ");
        String name = sc.nextLine();
        System.out.print(" 아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.print(" 비밀번호를 입력 해주세요 : ");
        String pwd = sc.nextLine();


        UserDTO loginUser = new UserDTO(id, pwd);


        switch (result) {
            case 1:
                // 사용자로 로그인
                if (userDAO.selectuser(getConnection(), loginUser)) {
                    // 성공이면 true, 실패면 false
                    return true; // true -> 사용자
                    // useradmin (role 지정하는 값이 될꺼임) 에
                    // false 를 담아둔다
                } else {
                    System.out.println(" 로그인 실패! 잘못된 아이디 또는 비밀번호 입니다. ");
                }
                break;

            case 2:
                // 관리자로 로그인
                if (userDAO.selectuser(getConnection(), loginUser)) {
                    // 성공이면 true, 실패면 false
                    // false -> 관리자
                    return false;
                    // useradmin (role 지정하는 값이 될꺼임) 에
                    // false 를 담아둔다
                } else {
                    System.out.println(" 로그인 실패! 잘못된 아이디 또는 비밀번호 입니다. ");
                }
                break;

            case 3:
                // 회원탈퇴
                if (userDAO.deleteuser(getConnection(), loginUser)) {
                    System.out.println(" 회원탈퇴 성공! ");
                } else {
                    System.out.println(" 회원탈퇴 실패! 아이디와 비밀번호를 확인해주세요. ");
                }

                break;
            default:
                break;
        }

        return false;
    }

}



