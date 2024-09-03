package com.ohgiraffers.book.usersession;

import com.ohgiraffers.user.dto.UserDTO;

public class UserSession {
    private static UserDTO userDTO = new UserDTO();

    public static UserDTO getUserDTO() {
        return userDTO;
    }
}
