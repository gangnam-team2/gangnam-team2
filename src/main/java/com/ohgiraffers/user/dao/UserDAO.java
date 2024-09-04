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

    /** 회원가입 정보를 추가하는 메서드*/
    public boolean insertuser(Connection con, UserDTO userDTO) {
        // 회원가입 정보 insert / signup
        PreparedStatement ps = null;
        int result = 0;
        String query = prop.getProperty("insertuser");

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userDTO.getUserId());
            ps.setString(2, userDTO.getUserName());
            ps.setString(3, userDTO.getUserPwd());
            ps.setBoolean(4, userDTO.getUserRole());
            result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println( " ... ");
        }
        return false;
    }



    /** 로그인하기 위해 사용자 정보를 가져오는 메서드*/
    public boolean selectuser(Connection con, UserDTO userDTO) {
        PreparedStatement ps = null;
        ResultSet result = null;
        String query = prop.getProperty("selectuser");
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, userDTO.getUserId());
            ps.setString(2, userDTO.getUserPwd());
            result = ps.executeQuery();
            if (result.next()) {

                int role = result.getInt("user_role");
                userDTO.setUserRole(role == 1);  // 1이면 사용자, 0이면 관리자
                return true; // 로그인 성공
            } else {
                return false; // 로그인 실패
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (result != null)
                try {
                    result.close();
                } catch (SQLException e) {
                    System.out.println("SQL 오류");
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("SQL 오류");
                }
        }
    }


    /** 회원탈퇴 메서드*/
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
            return false;
        }
    }

}

