package com.ohgiraffers.book.loginsignup;

import com.ohgiraffers.book.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Signup { // 회원가입


    private Map<String, String> userData = new HashMap<>();

    public boolean usersignup(UserDTO user) {
        // 사용자 회원가입

            if (userData.containsKey(user.getUserId())) {
                return false; // 사용자 이름이 이미 존재
            }
            userData.put(user.getUserId(), user.getUserPwd());
            return true;

    }


    public boolean adminsignup(UserDTO user) {
        // 관리자 회원가입

        if (userData.containsKey(user.getUserId())) {
            return false; // 사용자 이름이 이미 존재
        }
        userData.put(user.getUserId(), user.getUserPwd());
        return true;

    }
}
