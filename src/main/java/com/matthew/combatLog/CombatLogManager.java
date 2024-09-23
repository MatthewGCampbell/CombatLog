package com.matthew.combatLog;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
//import org.bukkit.Bukkit;
//import org.bukkit.World;
import org.bukkit.entity.Player;

public class CombatLogManager {
    // Debating using the world time instead of system time
//    World world = Bukkit.getWorld("world");
//
//    long worldTime = world.getTime();

    // New cooldown object
    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    // HashMap
    private HashMap<UUID, CombatEntry> combatLog = new HashMap<>();

    /**
     * Starts combat for a player
     *
     * @param player the player to start combat for
     */
    public void startCombat(Player player) {
        long currentTime = System.currentTimeMillis();  // Use system time in milliseconds
        CombatEntry combatEntry = new CombatEntry(player.getUniqueId(), currentTime, 0); // New combat entry object
        if (cooldown.asMap().containsKey(player.getUniqueId()) && combatLog.containsKey(player.getUniqueId())) { // 5 Minute Cooldown
            // Debug info
            player.sendMessage("You're already on the list");
        }
        else {
            // Adds the combat to the HashMap
            combatLog.put(player.getUniqueId(), combatEntry);
            // Debug info
            player.sendMessage("Combat has started");
            // Adds them to the cooldown so that there is a 5 Minute cooldown
            cooldown.put(player.getUniqueId(), currentTime);
        }
    }

    /**
     * Checks to see if the player is in active combat
     *
     * @param player the player to check and see if they are in combat
     */
    public boolean isInCombat(Player player) {
        UUID playerUUID = player.getUniqueId();

        // First, check if the player's UUID is in the combatLog map
        if (combatLog.containsKey(playerUUID)) {
            return true;
        }

        // Then, check if the player's UUID is in the cooldown map
        if (cooldown.asMap().containsKey(playerUUID)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Removes the player from the combat list and ends the cooldown
     *
     * @param player the player to end combat for
     */
    public void endCombat(Player player) {
        combatLog.remove(player.getUniqueId());
        cooldown.invalidate(player.getUniqueId());
    }
}