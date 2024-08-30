package com.ohgiraffers.manager.controller;

import java.util.Scanner;

public class ManagerController {
Scanner scr = new Scanner(System.in);

    public void displayManager(){

        System.out.println("사용자 관리 모드 입니다.");

        System.out.println("1. 회원들의 정보를 조회합니다.");
        System.out.println("2. 대여중인 책과 회원들을 조회합니다.");
        System.out.println("3. 현재 연체 중인 회원들을 조회합니다.");
        int num = scr.nextInt();
        switch (num){
            case 1: allMembersInfo(); break;
            case 2: findBookList(); break;
            case 3: memberHistoy(); break;

            default: break;

        }

    }

    public void allMembersInfo(){

    }

    public void findBookList(){

    }

    public void memberHistoy(){

    }


}
