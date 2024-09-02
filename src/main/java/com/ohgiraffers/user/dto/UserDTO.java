package com.ohgiraffers.user.dto;

import java.util.Date;

public class UserDTO {

    private String userId;             // 사용자 아이디
    private String userName;           // 사용자 이름
    private String userPwd;            // 사용자 비밀번호
    private boolean userRole;           // 사용자 역할 (true= ADMIN, false= USER)
    private Date userCreatedAt;        // 사용자 생성일
    private Date userUpdatedAt;        // 사용자 업데이트일
    private UserDTO userInfo;

    public UserDTO() {
    }

    public UserDTO(String userId, String userName, String userPwd,
                   boolean userRole, Date userCreatedAt, Date userUpdatedAt) {
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userRole = userRole;
        this.userCreatedAt = userCreatedAt;
        this.userUpdatedAt = userUpdatedAt;
    }

    public UserDTO(String userId, String userName, String userPwd, boolean userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userRole = userRole;
    }

    public UserDTO(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

    public UserDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserDTO userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public boolean getUserRole() {
        return userRole;
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    public Date getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(Date userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }

    public Date getUserUpdatedAt() {
        return userUpdatedAt;
    }

    public void setUserUpdatedAt(Date userUpdatedAt) {
        this.userUpdatedAt = userUpdatedAt;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userCreatedAt=" + userCreatedAt +
                ", userUpdatedAt=" + userUpdatedAt +
                '}';
    }
}