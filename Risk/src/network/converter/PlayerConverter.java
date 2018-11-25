/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import model.Model;
import model.Player;
import model.dto.Continent;
import model.dto.GameField;
import model.dto.Territory;
import network.PlayerDTO;

/**
 *
 * @author Johnny
 */
public class PlayerConverter {

    public static PlayerDTO getDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setMissionCard(player.getMissionCard());
        dto.setColor(player.getColor());
        dto.setName(player.getName());
        dto.setNumOfArtillery(player.getNumOfArtillery());
        dto.setNumOfCavalry(player.getNumOfCavalry());
        dto.setNumOfInfantry(player.getNumOfInfantry());
        dto.setPlayerId(player.getPlayerId());
        dto.setTroopCount(player.getTroopCount());
        dto.setRemainingPlaceableTroopCount(player.getRemainingPlaceableTroopCount());
        dto.setFinishedAttack(player.isFinishedAttack());
        dto.setFinishedRegroup(player.isFinishedRegroup());
        dto.setOccupiedContinents(player.getOccupiedContinents());
        return dto;
    }

    public static Player getPlayer(PlayerDTO dto, GameField field) {
        Player player = new Player(dto.getPlayerId(), dto.getName(), dto.getColor());
        player.setMissionCard( dto.getMissionCard() );
        List<Territory> occupiedTerritories = new LinkedList<Territory>();
        for (Territory t : field.getTerritories()) {
            if (t.getOccupierPlayerId() == dto.getPlayerId()) {
                occupiedTerritories.add(t);
            }
        }
        player.setOccupiedTerritories(occupiedTerritories);        
        player.setOccupiedContinents(dto.getOccupiedContinents());        
        player.setNumOfArtillery(dto.getNumOfArtillery());
        player.setNumOfCavalry(dto.getNumOfCavalry());
        player.setNumOfInfantry(dto.getNumOfInfantry());
        player.setTroopCount(dto.getTroopCount());
        player.setRemainingPlaceableTroopCount(dto.getRemainingPlaceableTroopCount());
        player.setFinishedAttack(dto.isFinishedAttack());
        player.setFinishedRegroup(dto.isFinishedRegroup());
        return player;
    }

}
