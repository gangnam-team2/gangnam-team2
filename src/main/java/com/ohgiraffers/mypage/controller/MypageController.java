package com.ohgiraffers.mypage.controller;
import com.ohgiraffers.book.dto.UserDTO;
import com.ohgiraffers.mypage.dao.MypageDAO;
import com.ohgiraffers.book.dto.BorrowRecordDTO;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class MypageController {
    MypageDAO mypageDAO = new MypageDAO();

    public void updateRequestBook() {
        Scanner sc = new Scanner(System.in);
        BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();


        List<BorrowRecordDTO> currentBorrowBookList =  mypageDAO.currentBorrowBooks(getConnection());
        System.out.println("현재 대여 중인 채은 " + currentBorrowBookList + " 입니다.");

       System.out.println("대여 신청한 책의 북코드를 입력해주세요.");
        borrowRecordDTO.setBookCode(sc.nextInt());
        System.out.println("변경 사항을 골라주세요.");
        System.out.println("1. 대여일 변경  2. 반납일 변경   3.대여 취소");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.println("대여일 변경 날짜를 입력해주세요.");
               Date newborrowDate = Date.valueOf(sc.next());
              borrowRecordDTO.setBorrowDate(newborrowDate);
               int result = mypageDAO.updateRequest1(getConnection(), borrowRecordDTO);
                if (result > 0) {
                    System.out.println("도서 대여일 수정 완료");
                } else {
                    System.out.println("대여일 변경에 실패했습니다.");
                }
                break;
            case 2:
                System.out.println("반납일 변경 날짜를 입력해주세요.");
                Date newDueDate = Date.valueOf(sc.next());
                borrowRecordDTO.setDueDate(newDueDate);
                int result1 = mypageDAO.updateRequest2(getConnection(), borrowRecordDTO);
                if (result1 > 0) {
                    System.out.println("도서 반납일 수정 완료");
                } else {
                    System.out.println("반납일 변경에 실패했습니다.");
                }
                break;
            case 3:
                borrowRecordDTO.setBookStatus(true);
                break;
            default:
                System.out.println("잘못 된 번호입니다. 다시 선택해주세요.");
                break;
        }

    }

    public void allBorrowBookList(){
        List<BorrowRecordDTO> allBorrowBookList =  mypageDAO.currentBorrowBooks(getConnection());
        System.out.println("현재 대여 중인 책은 " + allBorrowBookList + " 입니다.");
    }

    public void pwdUpdate(){

        Scanner sc = new Scanner(System.in);
        UserDTO userDTO = new UserDTO();
        MypageDAO mypageDAO = new MypageDAO();
        System.out.println("비밀번호를 입력해주세요.");
        userDTO.setUserPwd(sc.nextLine());
        System.out.println("변경할 비밀번호를 입력해주세요.");
        String changePwd = sc.nextLine();


        int result = mypageDAO.pwdUpdate(getConnection().changePwd);


        int result = mypageDAO.pwdUpdate(getConnection(),userDTO, changePwd);
        if (result > 0) {
            System.out.println("비밀번호가 변경 되었습니다.");
        }else{
            System.out.println("비밀번호 변경에 실패했습니다.");
        }

    }

}