package org.example.demo0401.old;

import lombok.Data;

@Data
public abstract class Weapon {

    public Weapon(String name, int damage) {
    }

    private int damage;
    private int damageType; // 0 - physical, 1 - fire, 2 - ice etc.
}
