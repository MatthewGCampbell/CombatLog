package com.matthew.combatLog;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public enum CombatLogManager {
    INSTANCE;

    private Cache<Player, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.SECONDS).build();
    private HashMap<Player, BukkitTask> cooldownIndicator = new HashMap<>();

    /**
     * Starts combat for a player
     * @param player the player to start combat for
     */
    public void startCombat(Player player) {
        // ONLY RUNS INITIALLY
        BukkitTask task;
        if (!cooldown.asMap().containsKey(player)) {
            // Initial Combat since you aren't on the cooldown
            player.sendMessage(ChatColor.RED + "You are now in combat.");
            task = Bukkit.getScheduler().runTaskLater(CombatLog.getInstance(), () -> {
                player.sendMessage(ChatColor.GREEN + "You are no longer in combat.");
            }, 15 * 20);
            cooldownIndicator.put(player, task);
        }
        else {
            task = cooldownIndicator.get(player);
            task.cancel();
            task = Bukkit.getScheduler().runTaskLater(CombatLog.getInstance(), () -> {
                player.sendMessage(ChatColor.GREEN + "You are no longer in combat.");
            }, 15 * 20);
            cooldownIndicator.put(player, task);
        }
        cooldown.put(player, System.currentTimeMillis() + (15 * 20));
    }
    /**
     * Checks to see if the player is in active combat
     * @param player the player to check and see if they are in combat
     */
    public boolean isInCombat(Player player) {
        return cooldown.asMap().containsKey(player);
    }

    /**
     * Remove player from the cooldown
     * @param player the player to check and see if they are in combat
     */
    public void endCombat(Player player) {
        cooldown.invalidate(player);
        cooldownIndicator.remove(player);
    }
}