package com.ohgiraffers.request.dao;

import com.ohgiraffers.request.dto.RequestDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;


public class RequestDAO
{
    private Properties prop = new Properties();

    public RequestDAO(String url)
    {
        try
        {
            prop.loadFromXML(new FileInputStream(url));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int insertRequestedBook(Connection con, RequestDTO requestDTO)
    {
        PreparedStatement pstmt = null;
        int result = 0;

        String query = prop.getProperty("insertRequestedBook");

        try
        {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, requestDTO.getUserId()); // 유저 아이디
            pstmt.setString(2, requestDTO.getBookTitle()); // 책 이름
            pstmt.setString(3, requestDTO.getBookAuthor()); // 작가
            pstmt.setString(4, requestDTO.getBookPublisher()); // 출판사

            result = pstmt.executeUpdate();

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }finally
        {
            close(con);
            close(pstmt);
        }
        return result;
    }





}
