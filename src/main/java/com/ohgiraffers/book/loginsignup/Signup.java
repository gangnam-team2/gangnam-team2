package com.ohgiraffers.book.loginsignup;

import com.ohgiraffers.book.dto.UserDTO;

import java.util.Scanner;

public class Signup { // 회원가입

    public void usersignup(UserDTO user) {

        // 사용자 회원가입

        if (userDTO.containsKey(username)) {
            System.out.println("이미 등록된 아이디입니다.");
        } else {
            users.put(username, new UserDTO(username, password));
            System.out.println("회원가입 성공!");
        }

    }


    public void adminsignup(UserDTO user) {

        // 관리자 회원가입 진행

        if (userDTO.containsKey(username)) {
            System.out.println("이미 등록된 아이디입니다.");
        } else {
            users.put(username, new UserDTO(username, password));
            System.out.println("회원가입 성공!");
        }

    }
}
