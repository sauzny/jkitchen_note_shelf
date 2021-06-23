package org.example.demo0401.ddd.domain.entity;

import org.example.demo0401.ddd.Transform;
import org.example.demo0401.ddd.Vector;

public interface Movable {
    // 相当于组件
    Transform getPosition();
    Vector getVelocity();

    // 行为
    void moveTo(long x, long y);
    void startMove(long velX, long velY);
    void stopMove();
    boolean isMoving();
}
