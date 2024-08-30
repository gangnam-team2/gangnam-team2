package com.ohgiraffers.book.controller;

import com.ohgiraffers.book.dto.UserDTO;
import com.ohgiraffers.book.loginsignup.Login;
import com.ohgiraffers.book.loginsignup.Signup;

import java.util.Scanner;

public class UserController {

    public void signUp() {

        //  사용자 회원가입 입력받기

        Scanner sc = new Scanner(System.in);
        Signup signup = new Signup();

        System.out.println(" ----- 사용자 회원가입을 진행하겠습니다.-----");

        System.out.println(" 본인의 이름을 입력해주세요 : ");
        String name = sc.nextLine();
        System.out.println(" 원하시는 아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.println(" 원하시는 비밀번호를 입력 해주세요 : ");
        String pwd = sc.nextLine();

        String role = "USER";
        UserDTO userDTO = new UserDTO(id, name, pwd, role);

        signup.usersignup(userDTO);



    }

    public void login() {

        // 사용자 로그인 입력받기

        Scanner sc = new Scanner(System.in);
        Login login = new Login();

        System.out.println( " ----- 사용자 로그인을 진행하겠습니다 -----");
        System.out.println( " 본인의 이름을 입력해주세요 : ");
        String name = sc.nextLine();
        System.out.println( " 아이디를 입력해주세요 : ");
        String id = sc.nextLine();
        System.out.println( " 비밀번호를 입력 해주세요 : ");
        String pwd = sc.nextLine();

        String role = "USER";

        UserDTO userDTO = new UserDTO(id, name, pwd, role);
        login.userlogin(userDTO);





    }
}
