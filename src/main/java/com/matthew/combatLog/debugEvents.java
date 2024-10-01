package com.matthew.combatLog;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.structure.Structure;

public class debugEvents implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Spawn players to the same spot
        Player player = event.getPlayer();
        World world = player.getWorld();

        Location nearestVillage = (Location) world.locateNearestStructure(
                player.getLocation(), Structure.VILLAGE_PLAINS, 1000, false);

        assert nearestVillage != null;
        player.teleport(nearestVillage);

    }
}
