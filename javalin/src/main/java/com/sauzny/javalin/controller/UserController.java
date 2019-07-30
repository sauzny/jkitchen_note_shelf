package com.sauzny.javalin.controller;

import com.google.common.collect.Lists;
import com.sauzny.javalin.entity.User;
import io.javalin.http.Context;
import io.javalin.websocket.WsHandler;
import lombok.extern.slf4j.Slf4j;

/***************************************************************************
 *
 * @时间: 2019/7/26 - 10:30
 *
 * @描述: TODO
 *
 ***************************************************************************/
@Slf4j
public class UserController {
    public static void createUser(Context ctx){
        log.info("UserController::createUser::{}", ctx.path());
    }

    public static void getAllUsers(Context ctx){
        log.info("UserController::getAllUsers::{}", ctx.path());

        ctx.json(Lists.newArrayList(
                new User(1, "胖虎", "小王庄"),
                new User(2, "蜘蛛侠", "美国"),
                new User(3, "和二", "中国")
        ));
    }

    public static void getUser(Context ctx){
        log.info("UserController::getUser::{}", ctx.path());
        log.info("UserController::getUser::{}", ctx.pathParam("id"));
    }

    public static void updateUser(Context ctx){
        log.info("UserController::updateUser::{}", ctx.path());
        log.info("UserController::updateUser::{}", ctx.pathParam("id"));
    }

    public static void deleteUser(Context ctx){
        log.info("UserController::deleteUser::{}", ctx.path());
        log.info("UserController::deleteUser::{}", ctx.pathParam("id"));
    }

    public static void webSocketEvents(WsHandler wsHandler){
        log.info("UserController::webSocketEvents");
    }

}
