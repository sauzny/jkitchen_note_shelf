package com.sauzny.javalin.controller;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/***************************************************************************
 *
 * @时间: 2019/7/26 - 13:07
 *
 * @描述: TODO
 *
 ***************************************************************************/
@Slf4j
public class ProductController implements CrudHandler {

    @Override
    public void create(@NotNull Context context) {
        log.info("ProductController::create::{}", context.path());
    }

    @Override
    public void delete(@NotNull Context context, @NotNull String id) {
        log.info("ProductController::delete::{}", context.path());
        log.info("ProductController::delete::{}", id);
    }

    @Override
    public void getAll(@NotNull Context context) {
        log.info("ProductController::getAll::{}", context.path());
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String id) {
        log.info("ProductController::getOne::{}", context.path());
        log.info("ProductController::getOne::{}", id);
    }

    @Override
    public void update(@NotNull Context context, @NotNull String id) {
        log.info("ProductController::update::{}", context.path());
        log.info("ProductController::update::{}", id);
    }
}
