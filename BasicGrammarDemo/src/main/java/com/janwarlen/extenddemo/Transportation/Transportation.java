package com.janwarlen.extenddemo.Transportation;

import java.math.BigDecimal;

/**
 * @ClassName: Transportation
 * @author: janwarlen
 * @Date: 2019/1/30 17:44
 * @Description: 交通工具
 */
public class Transportation {
    private String color;
    protected BigDecimal weight;
    public BigDecimal height;
    private String powerSource;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
