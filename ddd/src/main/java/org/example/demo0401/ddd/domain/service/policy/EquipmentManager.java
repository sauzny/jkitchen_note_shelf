package org.example.demo0401.ddd.domain.service.policy;

import org.example.demo0401.ddd.domain.entity.Player;
import org.example.demo0401.ddd.domain.entity.Weapon;
import org.example.demo0401.ddd.domain.service.policy.EquipmentPolicy;
import org.example.demo0401.ddd.domain.service.policy.impl.FighterEquipmentPolicy;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {
    private static final List<EquipmentPolicy> POLICIES = new ArrayList<>();
    static {
        POLICIES.add(new FighterEquipmentPolicy());
        //POLICIES.add(new MageEquipmentPolicy());
        //POLICIES.add(new DragoonEquipmentPolicy());
        //POLICIES.add(new DefaultEquipmentPolicy());
    }

    public boolean canEquip(Player player, Weapon weapon) {
        for (EquipmentPolicy policy : POLICIES) {
            if (!policy.canApply(player, weapon)) {
                continue;
            }
            return policy.canEquip(player, weapon);
        }
        return false;
    }
}
