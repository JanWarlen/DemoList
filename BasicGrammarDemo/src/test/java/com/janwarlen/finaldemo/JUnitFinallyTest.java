package com.janwarlen.finaldemo;

import org.junit.Test;

public class JUnitFinallyTest {

    private User testReturn () {
        User test = new User();
        try {
            test.setId("1");
            return test;
        } finally {
            test.setId("2");
            return test;
        }
    }

    private User testReturn2 () {
        User test = new User();
        try {
            test.setId("1");
            return test;
        } finally {
            test = new User();
            test.setId("2");
            return test;
        }
    }

    private User testReturn1_2 () {
        User test = new User();
        try {
            return test.setId2("1");
        } finally {
            return test.setId2("2");
        }
    }

    private User testReturn2_2 () {
        User test = new User();
        try {
            return test.setId2("1");
        } finally {
            test = new User();
            return test.setId2("2");
        }
    }

    @Test
    public void test1 () {
        System.out.println(testReturn ().getId());
    }

    @Test
    public void test2 () {
        System.out.println(testReturn2 ().getId());
    }

    @Test
    public void test3 () {
        System.out.println(testReturn1_2 ().getId());
    }

    @Test
    public void test4 () {
        System.out.println(testReturn2_2 ().getId());
    }

    private static class User {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public User setId2(String id) {
            this.id = id;
            return this;
        }
    }

}
