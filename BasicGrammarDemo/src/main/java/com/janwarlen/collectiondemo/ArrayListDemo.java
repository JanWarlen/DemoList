package com.janwarlen.collectiondemo;

import com.janwarlen.Entity.Person;
import org.springframework.expression.spel.ast.Operator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ArrayListDemo {

    static Field elementData;

    static {
        try {
            elementData = ArrayList.class.getDeclaredField("elementData");
            elementData.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ArrayListDemo() throws NoSuchFieldException {
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     **/
    public static void createInstanceByWay2() throws NoSuchFieldException, IllegalAccessException {
        List<Person> demo = new ArrayList<>(0);
        List<Person> demo2 = new ArrayList<>(10);

        Object[] o = (Object[]) elementData.get(demo);
        Object[] o2 = (Object[]) elementData.get(demo2);
        System.out.println(o.length);
        System.out.println(o2.length);
    }

    public static void createInstanceByWay3() throws NoSuchFieldException, IllegalAccessException {
        ArrayList<Person> objects = new ArrayList<>(10);
        objects.add(new Person());

        List<Person> demo = new ArrayList<>(objects);

        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length);
    }

    public static void createInstanceByWay1() throws NoSuchFieldException, IllegalAccessException {
        List<Person> demo = new ArrayList<>();

        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length);
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     **/
    public static void addObjectByDefault() throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
    }

    public static void addObjectByIndex() throws IllegalAccessException {
        List<Person> demo = new ArrayList<>(5);
        for (int i = 0; i < 11; i++) {
            demo.add(i, new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
    }

    public static void addObjectByCollectionDefault(Collection elements) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }

        System.out.println("-----------------------------------------------------");
        demo.addAll(elements);

        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void addObjectByCollectionIndex(int index, Collection elements) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }

        System.out.println("-----------------------------------------------------");
        demo.addAll(index, elements);

        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     **/

    public static void removeFirstObjectByDefault(Object obj) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.remove(obj);

        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void removeObjectByIndex(int index) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.remove(index);
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void removeObjectsByFilter(Predicate filter) throws IllegalAccessException {
        ArrayList<Person> demo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.removeIf(filter);
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void removeObjectsByCollection(Collection elements) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.removeAll(elements);
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void retainObjectsByCollection(Collection elements) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.retainAll(elements);
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void clear() throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.clear();
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     **/

    public static void queryExists(Object obj) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>(3);
        for (int i = 0; i < 11; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        System.out.println(demo.contains(obj));
    }

    public static void queryLastIndexOfObject(Object obj) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        System.out.println(demo.lastIndexOf(obj));
    }

    public static void getElementByIndex(int index) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>(3);
        for (int i = 0; i < 11; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        System.out.println(demo.get(index));
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     **/

    public static void setElementByIndex(int index, Person obj) throws IllegalAccessException {
        List<Person> demo = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.set(index, obj);
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void clearUnusedRam() throws IllegalAccessException {
        ArrayList<Person> demo = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.trimToSize();
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void replaceElementsByOperator(UnaryOperator<Person> operator) throws IllegalAccessException {
        ArrayList<Person> demo = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.replaceAll(operator);
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }

    public static void sortElementByComparator(Comparator<Person> c) throws IllegalAccessException {
        ArrayList<Person> demo = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            demo.add(new Person("demo" + i, 18 + i));
            Object[] o = (Object[]) elementData.get(demo);
            System.out.println(o.length + "|" + demo.toString());
        }
        System.out.println("-----------------------------------------------------");
        demo.sort(c);
        Object[] o = (Object[]) elementData.get(demo);
        System.out.println(o.length + "|" + demo.toString());
    }
    /**---------------------------------------------------------------------------------------------------**/
    /**---------------------------------------------------------------------------------------------------**/

}
