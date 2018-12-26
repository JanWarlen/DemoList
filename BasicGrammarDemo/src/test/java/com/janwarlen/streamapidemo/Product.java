package com.janwarlen.streamapidemo;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/24 15:40
 * @Description:
 */
public class Product {

    public Product(int price, String name) {
        this.price = price;
        this.name = name;
    }

    private int price;

    private String name;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
