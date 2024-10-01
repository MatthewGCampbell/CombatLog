package com.matthew.combatLog;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
//import org.bukkit.Bukkit;
//import org.bukkit.World;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CombatLogManager {
    // Debating using the world time instead of system time
//    World world = Bukkit.getWorld("world");
//
//    long worldTime = world.getTime();

    // Reference the plugin instance

    // New cooldown object
    private Cache<Player, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();

    /**
     * Starts combat for a player
     *
     * @param player the player to start combat for
     */
    public void startCombat(Player player) {
        long currentTime = System.currentTimeMillis();  // Use system time in milliseconds
        CombatEntry combatEntry = new CombatEntry(player, currentTime); // New combat entry object

        if (cooldown.asMap().containsKey(player)) { // 5 Minute Cooldown
            // Debug info
            player.sendMessage(ChatColor.GOLD + "You've already been in combat");
            cooldown.put(player, currentTime);
        }

        else {
            // Debug info
            player.sendMessage(ChatColor.GOLD + "Combat has started");
            // Adds them to the cooldown so that there is a 5-Minute cooldown
            cooldown.put(player, currentTime);
        }
    }
    /**
     * Checks to see if the player is in active combat
     *
     * @param player the player to check and see if they are in combat
     */
    public boolean isInCombat(Player player) {

        // Then, check if the player's UUID is in the cooldown map
        return cooldown.asMap().containsKey(player);
    }

    /**
     * Remove player from the cooldown
     *
     * @param player the player to check and see if they are in combat
     */
    public void endCombat(Player player) {
        // Remove from cooldown
        cooldown.invalidate(player);
    }


    public Cache<Player, Long> getCooldown() {
        return cooldown;
    }
}