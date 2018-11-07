/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;
import java.util.List;
import model.dto.Phase;

/**
 *
 * @author Johnny
 */
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String playerName;
    private String action;
    private int currentPlayerId;
    private Phase currentPhase;
    private List<PlayerDTO> players;
    private List<TerritoryDTO> territories;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<TerritoryDTO> getTerritories() {
        return territories;
    }

    public void setTerritories(List<TerritoryDTO> territories) {
        this.territories = territories;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
