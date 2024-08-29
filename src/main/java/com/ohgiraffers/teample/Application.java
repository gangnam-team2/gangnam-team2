package com.ohgiraffers.teample;

import java.util.Scanner;

import static com.ohgiraffers.teample.Div.div;
import static com.ohgiraffers.teample.Re.re;

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
            case '+' : break;
            case '-' : break;
            case '*' : break;
            case '/' :
                div(a,b);
                break;
            case '%' :
                re(a,b);
                break;
        }
    }
}

