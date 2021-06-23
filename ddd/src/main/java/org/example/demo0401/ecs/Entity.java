package org.example.demo0401.ecs;

public class Entity {
    public Vector position; // 此处Vector是一个Component, 指向的是MovementSystem.list里的一个

    public Entity(Vector position) {
        this.position = position;
    }
}
