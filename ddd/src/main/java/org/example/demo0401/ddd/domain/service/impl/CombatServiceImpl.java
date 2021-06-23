package org.example.demo0401.ddd.domain.service.impl;

import org.example.demo0401.ddd.domain.acl.repository.WeaponRepository;
import org.example.demo0401.ddd.domain.entity.Monster;
import org.example.demo0401.ddd.domain.entity.Player;
import org.example.demo0401.ddd.domain.entity.Weapon;
import org.example.demo0401.ddd.domain.service.CombatService;
import org.example.demo0401.ddd.domain.service.policy.DamageManager;

public class CombatServiceImpl implements CombatService {
    private WeaponRepository weaponRepository;
    private DamageManager damageManager;

    @Override
    public void performAttack(Player player, Monster monster) {
        Weapon weapon = weaponRepository.find(player.getWeaponId());
        int damage = damageManager.calculateDamage(player, weapon, monster);
        if (damage > 0) {
            monster.takeDamage(damage); // （Note 1）在领域服务里变更Monster
        }
        // 省略掉Player和Weapon可能受到的影响
    }
}
