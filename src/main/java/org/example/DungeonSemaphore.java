package org.example;

public class DungeonSemaphore {

    private final Dungeon[] dungeonNetwork;

    public DungeonSemaphore(Dungeon[] dungeonNetwork){
        this.dungeonNetwork = dungeonNetwork;
    }

    public synchronized void acquire(Party party) throws InterruptedException {
        while(true){
            for (Dungeon dungeon : dungeonNetwork)
                if (dungeon.isEmpty()) {
                    dungeon.enter(party);
                    return;
                }

            wait();
        }
    }

    public synchronized void release(Party party) {
        dungeonNetwork[party.getDungeonID()].clear();
        notifyAll();
    }


}
