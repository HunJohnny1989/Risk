/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.dto.Color;

/**
 *
 * @author Johnny
 */
public class PlayerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int playerId;
    private String name;
    private int remainingPlaceableTroopCount = 0;
    private Color color;
    private int numOfInfantry = 0;
    private int numOfCavalry = 0;
    private int numOfArtillery = 0;
    private int troopCount = 0;
    private List<String> occupiedTerritoryNames;
    private List<String> occupiedContinentNames;
    private boolean finishedAttack;
    private boolean finishedRegroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainingPlaceableTroopCount() {
        return remainingPlaceableTroopCount;
    }

    public void setRemainingPlaceableTroopCount(int remainingPlaceableTroopCount) {
        this.remainingPlaceableTroopCount = remainingPlaceableTroopCount;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getNumOfInfantry() {
        return numOfInfantry;
    }

    public void setNumOfInfantry(int numOfInfantry) {
        this.numOfInfantry = numOfInfantry;
    }

    public int getNumOfCavalry() {
        return numOfCavalry;
    }

    public void setNumOfCavalry(int numOfCavalry) {
        this.numOfCavalry = numOfCavalry;
    }

    public int getNumOfArtillery() {
        return numOfArtillery;
    }

    public void setNumOfArtillery(int numOfArtillery) {
        this.numOfArtillery = numOfArtillery;
    }

    public int getTroopCount() {
        return troopCount;
    }

    public void setTroopCount(int troopCount) {
        this.troopCount = troopCount;
    }

    public List<String> getOccupiedTerritoryNames() {
        return occupiedTerritoryNames;
    }

    public void setOccupiedTerritoryNames(List<String> occupiedTerritoryNames) {
        this.occupiedTerritoryNames = occupiedTerritoryNames;
    }

    public List<String> getOccupiedContinentNames() {
        return occupiedContinentNames;
    }

    public void setOccupiedContinentNames(List<String> occupiedContinentNames) {
        this.occupiedContinentNames = occupiedContinentNames;
    }

    public boolean isFinishedAttack() {
        return finishedAttack;
    }

    public void setFinishedAttack(boolean finishedAttack) {
        this.finishedAttack = finishedAttack;
    }

    public boolean isFinishedRegroup() {
        return finishedRegroup;
    }

    public void setFinishedRegroup(boolean finishedRegroup) {
        this.finishedRegroup = finishedRegroup;
    }

}
