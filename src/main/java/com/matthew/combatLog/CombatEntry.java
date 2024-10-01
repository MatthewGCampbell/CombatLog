package com.matthew.combatLog;

import java.util.UUID;

import org.bukkit.entity.Player;

public class CombatEntry {
    private String playerName;
    private Long combatTime;


    public CombatEntry(Player player, Long combatTime) {
        this.playerName = player.getName();
        this.combatTime = combatTime;
    }

}
