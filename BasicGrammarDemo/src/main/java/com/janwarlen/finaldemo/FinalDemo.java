package com.janwarlen.finaldemo;

class Person {
    String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }
}

public class FinalDemo {

    final Person person = new Person();

    public Person getPerson() {
        return person;
    }

    public void setPerson(String name) {
        this.person.name = name;
    }

    /**
     * cannot assign a value to final variable 'person'
     */
    /*public void setPerson (Person name) {
        this.person = name;
    }*/
    public static void add(final int a, final int b) {
//        a = 3;
        System.out.println("res:" + (a + b));
    }
}
