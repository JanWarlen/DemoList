package com.janwarlen.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Student {

    private String id;

    private String name;

    private int grade;

    private Date birth;

}
