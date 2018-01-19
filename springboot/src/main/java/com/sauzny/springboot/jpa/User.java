package com.sauzny.springboot.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
// SDJ -- Spring Data JPA
// SDJ 使用 @Entity，NoSQL 使用 @Document。(暂未验证)
@Entity
public class User {
    // 声明主键。
    @Id
    // 声明由程序控制主键生成策略。
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // 约束、不可为空、长度16。
    @Column(unique = true, nullable = false, length = 16)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    // 设定默认值。
    private Double balance = 0.0;
    // 不允许更新。
    @Column(updatable = false)
    private Date createTime = new Date();

    // 为 SDJ 提供一个默认的构造方法。
    public User() {}

    // 提供一个不可为 null 的属性的构造方法以防止出错。
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // 省略一大堆 Getter、Setter 和 ToString 方法。也可以通过 Lombok 插件以注解的方式大幅度简化，各大 IDE 均提供！
}
