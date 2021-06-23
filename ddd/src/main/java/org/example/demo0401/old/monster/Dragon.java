package org.example.demo0401.old.monster;

import org.example.demo0401.old.Monster;
import org.example.demo0401.old.Player;
import org.example.demo0401.old.Weapon;
import org.example.demo0401.old.player.Dragoon;

public class Dragon extends Monster {


    public Dragon(String name, long health) {
        super(name, health);
    }

    @Override
    public void receiveDamageBy(Weapon weapon, Player player) {
        if (player instanceof Dragoon) {
            this.setHealth(this.getHealth() - weapon.getDamage() * 2); // 龙骑伤害规则
        }
        // else no damage, 龙免疫力规则
    }
}
