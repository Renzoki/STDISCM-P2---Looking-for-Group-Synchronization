package org.example;

public class Dungeon {
    private int ID;
    private int totalPartyCount;
    private long totalTimeElapsed;
    private Party party;
    private boolean isEmpty;

    public Dungeon(int ID){
        this.ID = ID;
        this.isEmpty = true;
        totalPartyCount = 0;
        totalTimeElapsed = 0;
    }

    public void enter(Party party){
        this.party = party;
        party.setDungeonID(ID);
        isEmpty = false;
    }

    public void clear(){
        totalPartyCount += 1;
        totalTimeElapsed += party.getDungeonClearTime();
        party = null;
        isEmpty = true;
    }

    public int getID(){
        return ID;
    }

    public boolean isEmpty(){
        return isEmpty;
    }

    public int getTotalPartyCount(){
        return totalPartyCount;
    }

    public long getTotalTimeElapsed(){
        return totalTimeElapsed;
    }
}
