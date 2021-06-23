package org.example.demo0401.old.monster;

import org.example.demo0401.old.Monster;
import org.example.demo0401.old.Player;
import org.example.demo0401.old.Weapon;

public class Orc extends Monster {

    public Orc(String name, long health) {
        super(name, health);
    }

    @Override
    public void receiveDamageBy(Weapon weapon, Player player) {
        if (weapon.getDamageType() == 0) {
            this.setHealth(this.getHealth() - weapon.getDamage() / 2); // Orc的物理防御规则
        } else {
            super.receiveDamageBy(weapon, player);
        }
    }
}
