package com.ohgiraffers.user;

import com.ohgiraffers.user.controller.UserController;
import com.ohgiraffers.user.dto.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class Signup { // 회원가입


    private Map<String, String> userData = new HashMap<>();
    UserController userController = new UserController();

    public boolean usersignup(UserDTO user) {
        // 사용자 회원가입

            if (userData.containsKey(user.getUserId())) {
                return false;
            }
            userData.put(user.getUserId(), user.getUserPwd());
            return true;

    }


    public boolean adminsignup(UserDTO user) {
        // 관리자 회원가입

        int result = userController.userinsert();

        if (userData.containsKey(user.getUserId())) {
            if(result == 1){ // 성공 ... 맞나 ..?
                return true;
            }else{
                return false;
            }
        }
        userData.put(user.getUserId(), user.getUserPwd());
        return true;

    }
}
