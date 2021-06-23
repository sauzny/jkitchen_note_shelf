package org.example.demo0401.ddd.domain.service;

import org.example.demo0401.ddd.domain.entity.Player;
import org.example.demo0401.ddd.domain.entity.Weapon;

public interface EquipmentService {
    boolean canEquip(Player player, Weapon weapon);
}
