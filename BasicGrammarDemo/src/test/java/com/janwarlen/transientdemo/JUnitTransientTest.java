package com.janwarlen.transientdemo;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JUnitTransientTest {

    @Test
    public void writeDown () {
        TransientDemo transientDemo = new TransientDemo("name_1","junit_test");
        System.out.println(transientDemo.toString());
        try {
            ObjectOutputStream o = new ObjectOutputStream(
                    new FileOutputStream("demo.out"));
            //writeObject 乱码是正常现象
            o.writeObject(transientDemo);
            o.close();
        } catch(Exception e) {
            //deal with exception
        }
    }

    @Test
    public void readUp() {
        //To read the object back, we can write
        try {
            ObjectInputStream in =new ObjectInputStream(
                    new FileInputStream("demo.out"));
            TransientDemo transientDemo = (TransientDemo)in.readObject();
            System.out.println(transientDemo.toString());
        } catch(Exception e) {
            //deal with exception
        }
    }

}
