package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class QueueMaster {
    private final ArrayDeque<Role> tankQueue;
    private final ArrayDeque<Role> healerQueue;
    private final ArrayDeque<Role> damageQueue;
    private StatusMonitor monitor;
    private Dungeon[] dungeonNetwork;
    private DungeonSemaphore semaphore;
    private int t1;
    private int t2;

    public QueueMaster(int n, int t, int h, int d, int t1, int t2) throws InterruptedException {
        this.t1 = t1;
        this.t2 = t2;

        this.tankQueue = new ArrayDeque<>(t);
        this.healerQueue = new ArrayDeque<>(h);
        this.damageQueue = new ArrayDeque<>(d);


        instantiatePlayers(t, h, d);
        instantiateDungeonsAndSemaphore(n);
        startQueuing();
        printDungeonStatistics();
    }

    public void instantiatePlayers(int T, int H, int D) {
        for(int i = 0; i < T; i++) tankQueue.addLast(Role.TANK);
        for(int i = 0; i < H; i++) healerQueue.addLast(Role.HEALER);
        for(int i = 0; i < D; i++) damageQueue.addLast(Role.DAMAGE);
    }

    public void instantiateDungeonsAndSemaphore(int n) {
        this.monitor = new StatusMonitor(n);

        dungeonNetwork = new Dungeon[n];
        for(int i = 0; i < n; i++){
            dungeonNetwork[i] = new Dungeon(i);
        }

        semaphore = new DungeonSemaphore(dungeonNetwork);
    }

    public void startQueuing() throws InterruptedException {
        ArrayList<Party> parties = new ArrayList<>();
        boolean canFormParty = true;
        int c = 0;
        while (canFormParty) {
            if (!tankQueue.isEmpty() && !healerQueue.isEmpty() && damageQueue.size() >= 3) {
                Role tank = tankQueue.removeFirst();
                Role healer = healerQueue.removeFirst();
                Role[] damage = new Role[3];

                for (int i = 0; i < 3; i++) {
                    damage[i] = damageQueue.removeFirst();
                }

                Party party = new Party(c, tank, healer, damage, semaphore, monitor, t1, t2);
                parties.add(party);

                System.out.println("Created Party " + c + ", now queuing!");
                c++;
                party.start();
            } else {
                canFormParty = false;
            }
        }

        for (Party party : parties) {
            party.join();
        }

    }

    public void printDungeonStatistics(){
        System.out.println("===== FINAL STATISTICS =====");
        for (Dungeon dungeon : dungeonNetwork){
            System.out.println(" ---- Dungeon " + dungeon.getID() + "----");
            System.out.println("Total Parties Served: " + dungeon.getTotalPartyCount());
            System.out.println("Total Time Active: " + dungeon.getTotalTimeElapsed() + "s");
        }
    }
}
