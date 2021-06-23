package org.example.demo0401.ddd.domain.entity;

import lombok.Getter;
import org.example.demo0401.ddd.Transform;
import org.example.demo0401.ddd.Vector;
import org.example.demo0401.ddd.types.Health;
import org.example.demo0401.ddd.types.MonsterClass;
import org.example.demo0401.ddd.types.MonsterId;

public class Monster implements Movable {

    private MonsterId id;
    @Getter
    private MonsterClass monsterClass; // enum
    private Health health;
    private Transform position = Transform.ORIGIN;
    private Vector velocity = Vector.ZERO;

    @Override
    public Transform getPosition() {
        return null;
    }

    @Override
    public Vector getVelocity() {
        return null;
    }

    @Override
    public void moveTo(long x, long y) {

    }

    @Override
    public void startMove(long velX, long velY) {

    }

    @Override
    public void stopMove() {

    }

    @Override
    public boolean isMoving() {
        return false;
    }

    public void takeDamage(int damage){

    }
}
