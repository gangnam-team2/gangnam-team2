package com.ohgiraffers.book.user;

import static com.ohgiraffers.common.JDBCTemplate.*;


import java.sql.Connection;
import java.util.Scanner;

public class UserController {


    public void totalsignup() { // user, admin 회원가입 진행

        Signup signup = new Signup();


        System.out.println(" ----- 회원가입을 진행하겠습니다.-----");
        Scanner sc = new Scanner(System.in);
        System.out.println(" 1. 사용자로 회원가입 ");
        System.out.println(" 2. 관리자로 회원가입 ");
        System.out.println(" 원하는 서비스의 번호를 입력해주세요 : ");
        int result = sc.nextInt();

        System.out.println(" 본인의 이름을 입력해주세요 : ");
        String name = sc.nextLine();
        System.out.println(" 아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.println(" 비밀번호를 입력 해주세요 : ");
        String pwd = sc.nextLine();

        boolean singupuser = false;

        switch (result) {

            case 1: // 사용자로 회원가입
                boolean role1 = true;
                UserDTO newuser1 = new UserDTO(id, name, pwd, role1);
                singupuser = signup.usersignup(newuser1);

                break;
            case 2: // 관리자로 회원가입
                boolean role2 = false;
                UserDTO newuser2 = new UserDTO(id, name, pwd, role2);
                singupuser = signup.adminsignup(newuser2);
                break;
            default:
                break;
        }

        if (singupuser) {
            System.out.println("회원가입 성공!");
        } else {
            System.out.println("회원가입 실패: 사용자 이름이 이미 존재합니다.");
        }
    }


    public void totallogin() {
        // 사용자, 관리자 로그인

        Login login = new Login();
        System.out.println(" ----- 로그인을 진행하겠습니다.-----");
        Scanner sc = new Scanner(System.in);
        System.out.println(" 1. 사용자로 로그인 ");
        System.out.println(" 2. 관리자로 로그인 ");
        System.out.println(" 원하는 서비스의 번호를 입력해주세요 : ");
        int result = sc.nextInt();

        System.out.println(" 본인의 이름을 입력해주세요 : ");
        String name = sc.nextLine();
        System.out.println(" 아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.println(" 비밀번호를 입력 해주세요 : ");
        String pwd = sc.nextLine();


        UserDTO loginUser = new UserDTO(id, pwd);
        boolean ulogin = false;

        switch (result) {
            case 1:
                login.userlogin(loginUser);
                break;

            case 2:
                login.adminlogin(loginUser);
                break;
            default:
                break;
        }

        if (ulogin) {
            System.out.println("로그인 성공!");
        } else {
            System.out.println("로그인 실패: 사용자 이름 또는 비밀번호가 잘못되었습니다.");
        }

    }
    Connection con = getConnection();

    private UserDAO menuDAO = new UserDAO("src/main/resources/mapper/user-query.xml");


    public int userinsert(){

        int result = menuDAO.insertuser(getConnection());
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }
}

