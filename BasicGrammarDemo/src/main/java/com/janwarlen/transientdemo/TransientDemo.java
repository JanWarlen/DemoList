package com.janwarlen.transientdemo;

import java.io.Serializable;
import java.util.Date;

/**
 * 1）一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
 * 2）transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。
 * 3）被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。
 * ------------------------------------------------------------------------------------------------------------------------------------------------
 * 若实现的是Serializable接口，则所有的序列化将会自动进行，
 * 若实现的是Externalizable接口，则没有任何东西可以自动序列化，
 * 需要在writeExternal方法中进行手工指定所要序列化的变量，这与是否被transient修饰无关。
 */
public class TransientDemo implements Serializable {

    private Date recordDate = new Date();

    private String name;

    private transient String remark;

    TransientDemo(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TransientDemo{" +
                "recordDate=" + recordDate +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
