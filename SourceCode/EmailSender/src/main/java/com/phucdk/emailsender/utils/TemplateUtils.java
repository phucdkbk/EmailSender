/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phucdk.emailsender.utils;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class TemplateUtils {

    public static String getContent(String templateFile, HashMap<String, String> beans) throws FileNotFoundException {
        String content = FileUtils.getStringContentFromFile(templateFile);
        for (String key : beans.keySet()) {
            content = content.replaceAll(key, beans.get(key));
        }
        return content;
    }

}
