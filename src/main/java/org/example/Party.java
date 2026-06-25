package org.example;

import jdk.jshell.Snippet;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Party extends Thread {
    private int ID;
    private Role T;
    private Role H;
    private Role[] D;
    private final DungeonSemaphore S;
    private final StatusMonitor monitor;
    private final int t1;
    private final int t2;
    private int dungeonID;
    private long dungeonClearTime;

    public Party(int ID, Role T, Role H, Role[] D, DungeonSemaphore S,  StatusMonitor monitor, int t1, int t2){
        this.ID = ID;
        this.T = T;
        this.H = H;
        this.D = D;
        this.S = S;
        this.t1 = t1;
        this.t2 = t2;
        this.monitor = monitor;
    }

    @Override
    public void run(){
        try {
            S.acquire(this);
            dungeonClearTime = ThreadLocalRandom.current().nextInt(t1, t2 + 1);
            monitor.updateStatus(dungeonID, "active", ID, dungeonClearTime);
            Thread.sleep(dungeonClearTime * 1000L);

            //Exit Dungeon
            monitor.updateStatus(dungeonID, "empty", ID, dungeonClearTime);
            S.release(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDungeonID(int ID){
        this.dungeonID = ID;
    }

    public int getDungeonID(){
        return dungeonID;
    }

    public long getDungeonClearTime(){
        return dungeonClearTime;
    }

    private synchronized void logEntering() {
        System.out.println("\u001B[32mDungeon " + dungeonID + " is ACTIVE (Party " + ID + " has entered)\u001B[0m");
    }

    private synchronized void logExiting(long time) {
        System.out.println("\u001B[31mDungeon " + dungeonID + " is EMPTY (Party " + ID + " has exited after " + time + "s)\u001B[0m");
    }
}
