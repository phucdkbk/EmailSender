/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phucdk.emailsender.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class FileUtils {

    public static String getStringContentFromFile(String filePath) throws FileNotFoundException {
        String content = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
        return content;
    }

}
