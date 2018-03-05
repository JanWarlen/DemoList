package com.janwarlen.collectiondemo;

import com.janwarlen.Entity.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class JUnitArrayListTest {


    @Test
    public void testCreateInstanceByWay1() throws NoSuchFieldException, IllegalAccessException {
        ArrayListDemo.createInstanceByWay1();
    }

    @Test
    public void testCreateInstanceByWay2() throws NoSuchFieldException, IllegalAccessException {
        ArrayListDemo.createInstanceByWay2();
    }

    @Test
    public void testCreateInstanceByWay3() throws NoSuchFieldException, IllegalAccessException {
        ArrayListDemo.createInstanceByWay3();
    }
    /**---------------------------------------------------------------------------------------------------**/
    @Test
    public void testAddObjectByDefault() throws IllegalAccessException {
        ArrayListDemo.addObjectByDefault();
    }

    @Test
    public void testAddObjectByIndex() throws IllegalAccessException {
        ArrayListDemo.addObjectByIndex();
    }

    @Test
    public void testAddObjectByCollectionDefault() throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        demo.add(new Person("child", 3));
        demo.add(new Person("teenager", 13));
        demo.add(new Person("adult", 23));
        demo.add(new Person("old", 33));
        demo.add(new Person("dead", 43));
        demo.add(new Person("forgotten", 53));

        ArrayListDemo.addObjectByCollectionDefault(demo);
    }

    @Test
    public void testAddObjectByCollectionIndex() throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        demo.add(new Person("child", 3));
        demo.add(new Person("teenager", 13));
        demo.add(new Person("adult", 23));
        demo.add(new Person("old", 33));
        demo.add(new Person("dead", 43));
        demo.add(new Person("forgotten", 53));

        ArrayListDemo.addObjectByCollectionIndex(3, demo);
    }
    /**---------------------------------------------------------------------------------------------------**/
    @Test
    public void testRemoveFirstObjectByDefault() throws IllegalAccessException {
        ArrayListDemo.removeFirstObjectByDefault(new Person("demo1", 19));
    }

    @Test
    public void testRemoveObjectByIndex() throws IllegalAccessException {
        ArrayListDemo.removeObjectByIndex(5);
    }

    @Test
    public void testRemoveObjectByFilter() throws IllegalAccessException {
        Predicate filter = new Predicate() {
            @Override
            public boolean test(Object o) {
                Person person = (Person) o;
                return person.getAge() > 20;
            }
        };
        ArrayListDemo.removeObjectsByFilter(filter);
    }

    @Test
    public void testRemoveObjectsByCollection() throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
        }
        ArrayListDemo.removeObjectsByCollection(demo);
    }

    @Test
    public void testRetainObjectsByCollection() throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
        }
        ArrayListDemo.retainObjectsByCollection(demo);
    }

    @Test
    public void testClear() throws IllegalAccessException {
        ArrayListDemo.clear();
    }
    /**---------------------------------------------------------------------------------------------------**/
    @Test
    public void testQueryExists() throws IllegalAccessException {
        ArrayListDemo.queryExists(new Person("demo1", 19));
    }

    @Test
    public void testQueryLastIndexOfObject() throws IllegalAccessException {
        ArrayListDemo.queryLastIndexOfObject(new Person("demo1", 19));
    }

    @Test
    public void testGetElementByIndex() throws IllegalAccessException {
        ArrayListDemo.getElementByIndex(3);
    }
    /**---------------------------------------------------------------------------------------------------**/
    @Test
    public void testSetElementByIndex() throws IllegalAccessException {
        ArrayListDemo.setElementByIndex(3,new Person("replace",23));
    }

    @Test
    public void testClearUnusedRam() throws IllegalAccessException {
        ArrayListDemo.clearUnusedRam();
    }

    @Test
    public void testReplaceElementsByOperator() throws IllegalAccessException {
        UnaryOperator<Person> operator = new UnaryOperator<Person>() {
            @Override
            public Person apply(Person person) {
                if (person.getAge() > 21) {
                    person.setName(person.getName() + " ads");
                }
                return person;
            }
        };
        ArrayListDemo.replaceElementsByOperator(operator);
    }

    @Test
    public void testSortElementByComparator() throws IllegalAccessException {
        Comparator<Person> c = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                //返回正数o1>o2，负数o1<o2,0相等
                return o2.getAge() - o1.getAge();
            }
        };

        ArrayListDemo.sortElementByComparator(c);
    }
}
