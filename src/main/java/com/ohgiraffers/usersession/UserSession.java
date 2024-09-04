package com.ohgiraffers.usersession;

import com.ohgiraffers.user.dto.UserDTO;

/** 현재 로그인한 사용자의 정보를 담는 세션*/
public class UserSession {

    private static UserDTO userDTO = new UserDTO();

    public static UserDTO getUserDTO() {
        return userDTO;
    }

    public static void setUserDTO(UserDTO userDTO) {
        UserSession.userDTO = userDTO;
    }
}
