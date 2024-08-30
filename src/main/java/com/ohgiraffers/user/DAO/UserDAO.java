package com.ohgiraffers.user.DAO;

import com.ohgiraffers.user.DTO.UserDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class UserDAO {

    private Properties prop = new Properties();

    public UserDAO(String url) {
        try {
            prop.loadFromXML(new FileInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } // user_id, user_name, user_pwd, user_role
    }

    public int deleteUser(String username) {
        // 이제 해야함
    }



        public int insertuser(Connection con, UserDTO userDTO) {
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

            } catch (SQLException e) {
                System.out.println(" 잘못된 값이 입력되셨습니다. 다시 시도해주세요. ");
            } finally {
                close(con);
                close(ps);
            }
            return result;
        }
    }

