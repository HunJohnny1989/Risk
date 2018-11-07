/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;

/**
 *
 * @author Johnny
 */
public class TerritoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int troopCount;
    private int occupierPlayerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTroopCount() {
        return troopCount;
    }

    public void setTroopCount(int troopCount) {
        this.troopCount = troopCount;
    }

    public int getOccupierPlayerId() {
        return occupierPlayerId;
    }

    public void setOccupierPlayerId(int occupierPlayerId) {
        this.occupierPlayerId = occupierPlayerId;
    }

}
