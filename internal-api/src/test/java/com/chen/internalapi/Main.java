package com.chen.internalapi;

import java.util.Scanner;

/**
 * @author danger
 * @date 2021/5/6
 */
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        int b = scan.nextInt();
        int max = 0;
        if(a > b) {
            max = a;
        } else
            max = b;
        System.out.println("max = " + max);
        scan.close();
    }
}
