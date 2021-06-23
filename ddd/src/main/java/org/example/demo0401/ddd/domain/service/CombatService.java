package org.example.demo0401.ddd.domain.service;

import org.example.demo0401.ddd.domain.entity.Monster;
import org.example.demo0401.ddd.domain.entity.Player;

public interface CombatService {
    void performAttack(Player player, Monster monster);
}
