package org.example.demo0401.ddd.domain.service.impl;

import org.example.demo0401.ddd.domain.entity.Player;
import org.example.demo0401.ddd.domain.entity.Weapon;
import org.example.demo0401.ddd.domain.service.EquipmentService;
import org.example.demo0401.ddd.domain.service.policy.EquipmentManager;

public class EquipmentServiceImpl implements EquipmentService {
    private EquipmentManager equipmentManager;

    @Override
    public boolean canEquip(Player player, Weapon weapon) {
        return equipmentManager.canEquip(player, weapon);
    }
}
