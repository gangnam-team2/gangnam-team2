package com.ohgiraffers.user.dao;

import com.ohgiraffers.user.dto.UserDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class UserDAO {

    private Properties prop = new Properties();

    public UserDAO(String url) {
        try {
            prop.loadFromXML(new FileInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insertuser(Connection con, UserDTO userDTO) {
        // 회원가입 정보 insert / signup
        PreparedStatement ps = null;
        int result = 0;
        String query = prop.getProperty("insertuser");
        // 중복확인 해야댐 ....

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userDTO.getUserId());
            ps.setString(2, userDTO.getUserName());
            ps.setString(3, userDTO.getUserPwd());
            ps.setBoolean(4, userDTO.getUserRole());
            result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(" 잘못된 값이 입력되셨습니다. 다시 시도해주세요. ");
        }
        return false;
    }



    public boolean selectuser(Connection con, UserDTO userDTO){
        // 로그인 시 회원정보 select
        PreparedStatement ps = null;
        ResultSet result = null;
        String query = prop.getProperty("selectuser");
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userDTO.getUserId());
            ps.setString(2, userDTO.getUserPwd());
            result = ps.executeQuery();
            return result.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public boolean deleteuser(Connection con, UserDTO userDTO) {

        // 회원탈퇴 delete
        PreparedStatement ps = null;
        int result = 0;
        String query = prop.getProperty("deleteuser");

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userDTO.getUserId());
            ps.setString(2, userDTO.getUserPwd());
            result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println( " 회원 탈퇴 실패! 잘못된 아이디 또는 비밀번호를 입력하셨습니다. ");
        }
        return false;
    }


}

