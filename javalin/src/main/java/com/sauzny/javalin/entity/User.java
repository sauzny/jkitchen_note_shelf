package com.sauzny.javalin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/***************************************************************************
 *
 * @时间: 2019/7/26 - 14:24
 *
 * @描述: TODO
 *
 ***************************************************************************/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    private String name;

    private String address;
}
