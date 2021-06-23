package org.example.demo0401.old;

import lombok.Data;

@Data
public abstract class Player {

    private Weapon weapon;

    public Player(String name) {

    }

    public void attack(Monster monster) {
        monster.receiveDamageBy(weapon, this);
    }
}
