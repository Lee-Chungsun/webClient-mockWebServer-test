package com.example.springboottest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class Employee {
    private Long id;
    private String name;
    private String job;
    private Long age;

    public Employee(long l, String adam, String student, long l1) {
        this.id = l;
        this.name = adam;
        this.job = student;
        this.age = l1;
    }
}
