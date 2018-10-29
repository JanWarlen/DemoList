package com.janwarlen.regexdemo;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JUnitRegexTest {

    private final static Pattern testFilalStaticPattern = Pattern.compile("\\s*(.+?\\d)");

    @Test
    public void testRegexSample() {
        RegexDemo.regexSample();
    }

    @Test
    public void testRegexGroup() {
        RegexDemo.regexGroup();
    }

    @Test
    public void testFinalStaticPattern() {
        String content = "group1 group2 group3";
        String regex = "\\s*(.+?\\d)";
        Matcher matcher = testFilalStaticPattern.matcher(content);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

}
