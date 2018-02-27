package com.janwarlen.loopdemo;

import java.util.ArrayList;
import java.util.List;

class Person {
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}

public class ForEachDemo {

    public static void assignmentString() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        for (String str : list) {
            str = str + ".";
        }

        System.out.println(list.toString());
    }

    public static void assignmentInteger() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        for (Integer integer : list) {
            integer = 1;
        }

        System.out.println(list.toString());
    }

    public static void assignmentPerson() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Tom"));
        list.add(new Person("Jack"));

        for (Person person : list) {
            person.setName("already changed.");
        }

        System.out.println(list.toString());
    }

}
