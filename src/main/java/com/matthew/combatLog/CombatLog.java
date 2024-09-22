package com.matthew.combatLog;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatLog extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Combat Log Enabled");
        Bukkit.getPluginManager().registerEvents(new Events(), this);

    }

//    @Override
//    public void onDisable() {
//        // Plugin shutdown logic
//    }
}
