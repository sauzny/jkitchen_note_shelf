package org.example.demo0401.ddd.domain.entity;

import lombok.Value;
import org.example.demo0401.ddd.types.WeaponId;
import org.example.demo0401.ddd.types.WeaponType;

@Value
public class Weapon {

    private WeaponId id;
    private String name;
    private WeaponType weaponType; // enum
    private int damage;
    private int damageType; // 0 - physical, 1 - fire, 2 - ice
}
