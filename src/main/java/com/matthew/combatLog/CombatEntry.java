package com.matthew.combatLog;

import java.util.UUID;

public class CombatEntry {
    private UUID playerUUID;
    private long combatStartTime;
    private UUID combatantUUID;
    private long lastHitTime;
    private int combatDuration;
    private boolean isInCombat;

    //Constructor
    public CombatEntry(UUID playerUUID, UUID combatantUUID, long startTime, int duration) {
        this.playerUUID = playerUUID;
        this.combatantUUID = combatantUUID;
        this.combatStartTime = startTime;
        this.lastHitTime = 0;
        this.combatDuration = duration;
        this.isInCombat = true;
    }


    // Methods to update combat time, check if combat is still active, etc.
    public void updateLastHitTime(long currentTime) {
        this.lastHitTime = currentTime;
    }

    public boolean isCombatActive(long currentTime) {
        return (currentTime - lastHitTime) <= combatDuration * 1000;
    }

    // Getter for isInCombat
    public boolean getIsInCombat() {
        return isInCombat;
    }


}
