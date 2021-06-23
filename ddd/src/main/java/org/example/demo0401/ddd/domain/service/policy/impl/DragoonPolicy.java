package org.example.demo0401.ddd.domain.service.policy.impl;

import org.example.demo0401.ddd.domain.entity.Monster;
import org.example.demo0401.ddd.domain.entity.Player;
import org.example.demo0401.ddd.domain.entity.Weapon;
import org.example.demo0401.ddd.domain.service.policy.DamagePolicy;
import org.example.demo0401.ddd.types.MonsterClass;
import org.example.demo0401.ddd.types.PlayerClass;

public class DragoonPolicy implements DamagePolicy {
    public int calculateDamage(Player player, Weapon weapon, Monster monster) {
        return weapon.getDamage() * 2;
    }
    @Override
    public boolean canApply(Player player, Weapon weapon, Monster monster) {
        return player.getPlayerClass() == PlayerClass.Dragoon &&
                monster.getMonsterClass() == MonsterClass.Dragon;
    }
}