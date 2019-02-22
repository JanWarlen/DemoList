package com.janwarlen.model;

/**
 * @ClassName: Person
 * @author: janwarlen
 * @Date: 2019/1/30 17:39
 * @Description:
 */
public class Person {
    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String sayHi(String msg) {
        System.out.println("hello");
        return "hi," + msg + ",my name is "+name+", i'm "+age+" years old now.";
    }
}
