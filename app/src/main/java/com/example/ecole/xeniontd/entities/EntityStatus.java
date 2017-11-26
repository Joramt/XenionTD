package com.example.ecole.xeniontd.entities;


public class EntityStatus {

    private String entityType;
    private String entityState;
    private int monsterID;
    private int entityCode;
    private int towerID;

    public EntityStatus(String entityType, String entityState, int entityCode){
        this.entityType = entityType;
        this.entityState = entityState;
        this.entityCode = entityCode;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityState() {
        return entityState;
    }

    public void setEntityState(String entityState) {
        this.entityState = entityState;
    }

    public int getMonsterID() {
        return monsterID;
    }

    public void setMonsterID(int monsterID) {
        this.monsterID = monsterID;
    }

    public int getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(int entityCode) {
        this.entityCode = entityCode;
    }

    public int getTowerID() {
        return towerID;
    }

    public void setTowerID(int towerID) {
        this.towerID = towerID;
    }


}
