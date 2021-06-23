package org.example.demo0401.ddd.domain.acl.repository;

import org.example.demo0401.ddd.domain.entity.Weapon;
import org.example.demo0401.ddd.types.WeaponId;

public interface WeaponRepository {

    Weapon find(WeaponId weaponId);
}
