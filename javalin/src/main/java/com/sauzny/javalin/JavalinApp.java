package com.sauzny.javalin;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.vue.VueComponent;


/**
 * Hello world!
 */
public class JavalinApp {

    public static void main(String[] args) {
        var app2 = Javalin.create().start(8000);
        app2.get("/", ctx -> ctx.result("Hello Javalin app2"));

        var app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
            config.addStaticFiles("/vue");
        }).start(7000);

        app.get("/", ctx -> ctx.result("Hello Javalin app1"));
        // 初始化 route
        new Routes(app);

        app.get("/users", new VueComponent("<users-component></users-component>"));

    }
}
