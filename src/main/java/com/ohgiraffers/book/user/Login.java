package com.ohgiraffers.book.user;

import java.util.*;

public class Login {

    private List<UserDTO> userlist = new ArrayList<>();

    public boolean userlogin(UserDTO user) {
        // 사용자 로그인

        for (UserDTO userDTO : userlist) {
            if (userDTO.getUserId().equals(user.getUserId())) {
                if (userDTO.getUserPwd().equals(user.getUserPwd())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean adminlogin(UserDTO user) {
        for (UserDTO userDTO : userlist) {
            if (userDTO.getUserId().equals(user.getUserId())) {
                if (userDTO.getUserPwd().equals(user.getUserPwd())) {
                    return true;
                }
            }
        }
        return false;
    }
}







