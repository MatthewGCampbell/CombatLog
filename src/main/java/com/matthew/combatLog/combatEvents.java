package com.matthew.combatLog;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class combatEvents implements Listener {

    private CombatLogManager combatLogManager = CombatLogManager.INSTANCE;

    /**
     * Player takes damage, add damager and receiver to log
     * @param e EntityDamageByEntityEvent
     */
    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player receiver && e.getDamager() instanceof Player damager) {
            combatLogManager.startCombat(damager);
            combatLogManager.startCombat(receiver);
        }
    }

    /**
     * If player quits during combat log, kill the player
     * @param e PlayerQuitEvent
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if(combatLogManager.isInCombat(player)) {
            combatLogManager.endCombat(player);
            player.setHealth(0.0);
        }
    }
}
