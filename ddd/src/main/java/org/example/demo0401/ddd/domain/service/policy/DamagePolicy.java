package org.example.demo0401.ddd.domain.service.policy;

import org.example.demo0401.ddd.domain.entity.Monster;
import org.example.demo0401.ddd.domain.entity.Player;
import org.example.demo0401.ddd.domain.entity.Weapon;

public interface DamagePolicy {

    int calculateDamage(Player player, Weapon weapon, Monster monster);

    boolean canApply(Player player, Weapon weapon, Monster monster);
}
