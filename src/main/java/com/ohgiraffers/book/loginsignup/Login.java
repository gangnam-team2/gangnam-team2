package com.ohgiraffers.book.loginsignup;

import com.ohgiraffers.book.dto.UserDTO;

import java.util.Scanner;

public class Login {


    public void userlogin(UserDTO userDTO) {
        // 사용자 로그인


        UserDTO user = UserDTO.getUserId(userId);
        if (user != null && user.getUserPwd().equals(pwd) {
            System.out.println("로그인 성공!");
        } else {
            System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }



        public void adminligin (UserDTO userDTO) {

            // 관리자 로그인
            if (userDTO.containsKey(username)) {
                System.out.println("이미 등록된 아이디입니다.");
            } else {
                users.put(username, new UserDTO(username, password));
                System.out.println("회원가입 성공!");
            }


        }
    }

