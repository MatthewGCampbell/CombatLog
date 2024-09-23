package com.matthew.combatLog;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

    // Combat log manager object
    CombatLogManager combatLogManager = new CombatLogManager();

    // Damage Event
    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player && !(e.getDamager() instanceof Player)) {
            Player enity = (Player) e.getEntity();
            Entity damager = (Entity) e.getDamager();
            enity.getPlayer().sendMessage(ChatColor.RED + "You haven't been combat logged as you were attacked by a " + damager.getName());
        }
        if(e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)) {
            Player damager = (Player) e.getDamager();
            Entity enity = (Entity) e.getEntity();
            damager.getPlayer().sendMessage(ChatColor.RED + "You haven't been combat logged as you attacked a " + enity.getName());
        }
        if (e.getEntity() instanceof Player entity && e.getDamager() instanceof Player damager) {
            // entity.sendMessage(ChatColor.RED + "You have been combat logged as you were attacked by " + damager.getName());
            // damager.sendMessage(ChatColor.RED + "You have been combat logged as you attacked " + entity.getName());

            combatLogManager.startCombat(damager);
            combatLogManager.startCombat(entity);
        }
    }

//    @EventHandler
//    public void onJoin(PlayerJoinEvent e) {
//        Player p = e.getPlayer();
//        if(combatLogManager.isInCombat(p)) {
//            p.sendMessage("You've been in combat!!! and unfortunately you need to die!!");
//            combatLogManager.endCombat(p);
//            p.setHealth(0.0);
//        }
//    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(combatLogManager.isInCombat(p)) {
            combatLogManager.endCombat(p);
            p.setHealth(0.0);
        }
    }
}
