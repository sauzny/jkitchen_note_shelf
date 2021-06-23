package org.example.demo0401.ddd.domain.service.policy;

import org.example.demo0401.ddd.domain.entity.Monster;
import org.example.demo0401.ddd.domain.entity.Player;
import org.example.demo0401.ddd.domain.entity.Weapon;
import org.example.demo0401.ddd.domain.service.policy.DamagePolicy;
import org.example.demo0401.ddd.domain.service.policy.impl.DragoonPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DamageManager {
    //private static final List<DamagePolicy> POLICIES = new ArrayList<>();
    private static final ServiceLoader<DamagePolicy> POLICIES = ServiceLoader.load(DamagePolicy.class);
    static {
        //POLICIES.add(new DragoonPolicy());
        //POLICIES.add(new DragonImmunityPolicy());
        //POLICIES.add(new OrcResistancePolicy());
        //POLICIES.add(new ElfResistancePolicy());
        //POLICIES.add(new PhysicalDamagePolicy());
        //POLICIES.add(new DefaultDamagePolicy());
    }

    public int calculateDamage(Player player, Weapon weapon, Monster monster) {
        for (DamagePolicy policy : POLICIES) {
            if (!policy.canApply(player, weapon, monster)) {
                continue;
            }
            return policy.calculateDamage(player, weapon, monster);
        }
        return 0;
    }
}
