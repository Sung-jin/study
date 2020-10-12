package com.example.com.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Member {
    @Id
    private Long id;
    private String name;
    private Integer age;
}
