package org.example.demo0401.ddd.domain.entity;

import lombok.Getter;
import org.example.demo0401.ddd.Transform;
import org.example.demo0401.ddd.Vector;
import org.example.demo0401.ddd.domain.service.EquipmentService;
import org.example.demo0401.ddd.types.PlayerClass;
import org.example.demo0401.ddd.types.PlayerId;
import org.example.demo0401.ddd.types.WeaponId;

public class Player implements Movable {

    private PlayerId id;
    private String name;
    @Getter
    private PlayerClass playerClass; // enum
    @Getter
    private WeaponId weaponId; // （Note 1）
    private Transform position = Transform.ORIGIN;
    private Vector velocity = Vector.ZERO;

    public void equip(Weapon weapon, EquipmentService equipmentService) {
        if (equipmentService.canEquip(this, weapon)) {
            this.weaponId = weapon.getId();
        } else {
            throw new IllegalArgumentException("Cannot Equip: " + weapon);
        }
    }

    @Override
    public Transform getPosition() {
        return null;
    }

    @Override
    public Vector getVelocity() {
        return null;
    }

    public void moveTo(long x, long y) {
        this.position = new Transform(x, y);
    }

    public void startMove(long velocityX, long velocityY) {
        this.velocity = new Vector(velocityX, velocityY);
    }

    public void stopMove() {
        this.velocity = Vector.ZERO;
    }

    @Override
    public boolean isMoving() {
        return this.velocity.getX() != 0 || this.velocity.getY() != 0;
    }
}
