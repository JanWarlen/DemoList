package com.janwarlen.regexdemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {

    public static void regexSample() {
        String content = "hello regex";

        String regex = "hello.*";

        boolean matches = Pattern.matches(regex, content);
        System.out.println(matches);
    }

    public static void regexGroup() {
        String content = "group1 group2 group3";
        String regex = "\\s*(.+?\\d)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    public static void regexMode() {
        String content = "group1 group2 group3";
        String regex = "\\s*(.+?\\d)";
        Pattern pattern = Pattern.compile(regex,Pattern.CANON_EQ);
    }

}
