package com.ohgiraffers.teample;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner scr = new Scanner(System.in);
        System.out.println("계산기 만들기");

        System.out.println("첫번째 숫자");
        int a = scr.nextInt();
        System.out.println("두번째 숫자");
        int b = scr.nextInt();
        System.out.println("연산자?");
        scr.nextLine();
        char c = scr.nextLine().charAt(0);

        switch (c){
            case '+' :; break;
            case '-' :  minus.minus(a, b);break;
            case '*' : break;
            case '/' :
                Div.div(a,b);
                break;
            case '%' :
                Re.re(a,b);
                break;
        }

    }
}

