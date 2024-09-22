package com.matthew.combatLog;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CombatLogManager {
    World world = Bukkit.getWorld("world");

    long worldTime = world.getTime();

    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    private HashMap<UUID, CombatEntry> combatLog = new HashMap<>();

    public void startCombat(Player player, Player player2) {
        long currentTime = worldTime;  // Use system time in milliseconds
        CombatEntry combatEntry = new CombatEntry(player.getUniqueId(), player2.getUniqueId(), currentTime, 0);
        if (cooldown.asMap().containsKey(player.getUniqueId()) || cooldown.asMap().containsKey(player2.getUniqueId())) { // 5 Minute Cooldown
            player.sendMessage("You're already on the list");
            player2.sendMessage("You're already on the list");
        }
        else {
            combatLog.put(player.getUniqueId(), combatEntry);
            player.sendMessage("Combat has started");
            player2.sendMessage("Combat has started");
            cooldown.put(player.getUniqueId(), currentTime);
            cooldown.put(player2.getUniqueId(), currentTime);
        }
    }

    public boolean isInCombat(Player player) {
        CombatEntry entry = combatLog.get(player.getUniqueId());
        if (entry != null) {
            long currentTime = worldTime;
            if (entry.isCombatActive(currentTime)) {
                return true;
            } else {
                combatLog.remove(player.getUniqueId());  // Remove if combat time has expired
            }
        }
        return false;
    }

    public void endCombat(Player player) {
        combatLog.remove(player.getUniqueId());
    }
}