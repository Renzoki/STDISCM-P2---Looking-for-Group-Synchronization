package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StatusMonitor {
    private final String[] statusRegistry;
    private final int totalDungeons;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public StatusMonitor(int totalDungeons) {
        this.totalDungeons = totalDungeons;
        this.statusRegistry = new String[totalDungeons];

        for (int i = 0; i < totalDungeons; i++) {
            statusRegistry[i] = "\u001B[32mDungeon " + i + ": EMPTY\u001B[0m";
        }
    }

    public synchronized void updateStatus(int dungeonID, String status, int partyID, long time) {
        if ("active".equalsIgnoreCase(status)) {
            statusRegistry[dungeonID] = String.format("\u001B[31mDungeon %d: ACTIVE (Party %d inside)\u001B[0m", dungeonID, partyID);
        } else {
            statusRegistry[dungeonID] = String.format("\u001B[32mDungeon %d: EMPTY\u001B[0m", dungeonID);
        }

        refreshConsole();
    }

    private void refreshConsole() {
        // Print a clean, bounded update block that stands out as it streams down
        System.out.println("\n=============================================");
        System.out.println(" LIVE DUNGEON STATUS UPDATE [" + LocalTime.now().format(timeFormatter) + "]");
        System.out.println("=============================================");
        for (int i = 0; i < totalDungeons; i++) {
            System.out.println(statusRegistry[i]);
        }
        System.out.println("=============================================");
    }
}