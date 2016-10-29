/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phucdk.emailsender.utils;

/**
 *
 * @author Administrator
 */
public class StringUtils {

    public static boolean isNumeric(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }
        int sz = str.length();
        char dot = '.';
        int countDot = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i)) && dot != str.charAt(i)) {
                return false;
            }
        }
        for (int i = 0; i < sz; i++) {
            if (dot == str.charAt(i)) {
                countDot++;
            }
        }
        if (countDot > 1) {
            return false;
        }
        return true;
    }

}
