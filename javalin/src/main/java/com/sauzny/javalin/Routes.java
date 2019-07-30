package com.sauzny.javalin;

import com.sauzny.javalin.controller.ProductController;
import com.sauzny.javalin.controller.UserController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.ws;

/***************************************************************************
 *
 * @时间: 2019/7/26 - 10:35
 *
 * @描述: TODO
 *
 ***************************************************************************/
public class Routes {

    public Routes(Javalin app){
        app.routes(() -> {
            path("users", () -> {
                get(UserController::getAllUsers);
                post(UserController::createUser);
                path(":id", () -> {
                    get(UserController::getUser);
                    patch(UserController::updateUser);
                    delete(UserController::deleteUser);
                });
                ws("events", UserController::webSocketEvents);
            });
        });

        app.routes(() -> {
            crud("products/:id", new ProductController());

        });
    }
}
