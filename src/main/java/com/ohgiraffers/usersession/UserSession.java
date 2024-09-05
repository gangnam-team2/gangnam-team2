package com.ohgiraffers.usersession;

import com.ohgiraffers.user.dto.UserDTO;

/** 현재 로그인한 사용자의 정보를 담는 세션*/
public class UserSession { // 유저의 현재 정보를 받고 있는 클래스이다.

    private static UserDTO userDTO = new UserDTO(); // UserDTO 클래스 객체를 선언!

    public static UserDTO getUserDTO() {
        return userDTO;
    } // 유저 정보를 뱉는 개터 메소드

    public static void setUserDTO(UserDTO userDTO) {
        UserSession.userDTO = userDTO;
    } // 유저 정보를 받는 세터 메소드
}
