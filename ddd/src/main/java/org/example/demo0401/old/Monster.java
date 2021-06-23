package org.example.demo0401.old;

import lombok.Data;

@Data
public abstract class Monster {

    private Long health;

    public Monster(String name, long health) {
    }

    public void receiveDamageBy(Weapon weapon, Player player) {
        this.health -= weapon.getDamage(); // 基础规则
    }
}
