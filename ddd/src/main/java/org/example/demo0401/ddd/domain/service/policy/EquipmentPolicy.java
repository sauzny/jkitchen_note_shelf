package org.example.demo0401.ddd.domain.service.policy;

import org.example.demo0401.ddd.domain.entity.Player;
import org.example.demo0401.ddd.domain.entity.Weapon;

public interface EquipmentPolicy {

    boolean canApply(Player player, Weapon weapon);

    boolean canEquip(Player player, Weapon weapon);
}
