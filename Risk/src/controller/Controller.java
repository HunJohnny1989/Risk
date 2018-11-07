/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import model.*;
import model.dto.BattleResult;
import model.dto.GameField;
import model.dto.Phase;
import model.dto.Territory;
import network.Client;
import network.MessageDTO;
import network.PlayerDTO;
import network.TerritoryDTO;
import network.converter.PlayerConverter;
import view.MainWindow;

/**
 *
 * @author Johnny
 */
public class Controller implements ControllerInterface {

    private int clientPlayerId;
    private static Model model;
    private static MainWindow mainWindow;
    private static GameField field;
    private Client client;

    public Controller() {
    }

    public Controller(Client client) {
        this.client = client;
    }

    @Override
    public int getPlayerRemainingTroopCount() {
        return model.getCurrentPlayer().getRemainingPlaceableTroopCount();
    }

    @Override
    public void addTroopToTerritory(Territory territory, int troopCount) {
        Player currentPlayer = model.getCurrentPlayer();
        currentPlayer.setRemainingPlaceableTroopCount(currentPlayer.getRemainingPlaceableTroopCount() - troopCount);
        if (currentPlayer.getRemainingPlaceableTroopCount() == 0) {
            model.getCurrentPlayer().setRemainingPlaceableTroopCount(0);
            model.selectNextPlayer();
            if (model.allTroopPlaced()) {
                model.nextPhase();
                mainWindow.setGamePhase(model.getCurrentPhase());
            }
            mainWindow.setPLayer(model.getCurrentPlayer());
            //mainWindow.setMissionString(model.searchPlayer(clientPlayerId).getMissionCard().getMission());
        }
        territory.addTroops(troopCount);
        //Broadcast action
        MessageDTO msg = new MessageDTO();
        msg.setAction("addTroopToTerritory");
        msg.setCurrentPlayerId(model.getCurrentPlayerId());
        msg.setCurrentPhase(model.getCurrentPhase());
        List<TerritoryDTO> ts = new ArrayList<TerritoryDTO>();
        TerritoryDTO tdto = new TerritoryDTO();
        tdto.setName(territory.getName());
        tdto.setTroopCount(territory.getTroopCount());
        ts.add(tdto);
        msg.setTerritories(ts);
        List<PlayerDTO> pls = new ArrayList<PlayerDTO>();
        for (Player p : model.getPlayers()) {
            PlayerDTO pdto = PlayerConverter.getDTO(p);
            pls.add(pdto);
        }
        msg.setPlayers(pls);
        client.sendMessage(msg);
    }

    @Override
    public Player getCurrentPlayer() {
        return model.getCurrentPlayer();
    }

    @Override
    public Phase getCurrentPhase() {
        return model.getCurrentPhase();
    }

    @Override
    public BattleResult attackTerritory(Territory from, Territory to, int troopCount) {
        BattleResult result = model.getCurrentPlayer().attack(from, to, troopCount, model);
        from.removeTroops(result.getAttackerTroopLossCount());
        to.removeTroops(result.getDefenderTroopLossCount());
        //Broadcast action
        MessageDTO msg = new MessageDTO();
        msg.setAction("setTerritory");
        List<TerritoryDTO> ts = new ArrayList<TerritoryDTO>();
        TerritoryDTO fromDTO = new TerritoryDTO();
        fromDTO.setName(from.getName());
        fromDTO.setTroopCount(from.getTroopCount());
        fromDTO.setOccupierPlayerId(from.getOccupierPlayerId());
        ts.add(fromDTO);
        TerritoryDTO toDTO = new TerritoryDTO();
        toDTO.setName(to.getName());
        toDTO.setTroopCount(to.getTroopCount());
        toDTO.setOccupierPlayerId(to.getOccupierPlayerId());
        ts.add(toDTO);
        msg.setTerritories(ts);
        client.sendMessage(msg);
        return result;
    }

    @Override
    public void transfer(Territory from, Territory to, int troopCount) {
        model.getCurrentPlayer().transfer(from, to, troopCount);
        //Broadcast action
        MessageDTO msg = new MessageDTO();
        msg.setAction("setTerritory");
        List<TerritoryDTO> ts = new ArrayList<TerritoryDTO>();
        TerritoryDTO fromDTO = new TerritoryDTO();
        fromDTO.setName(from.getName());
        fromDTO.setTroopCount(from.getTroopCount());
        fromDTO.setOccupierPlayerId(from.getOccupierPlayerId());
        ts.add(fromDTO);
        TerritoryDTO toDTO = new TerritoryDTO();
        toDTO.setName(to.getName());
        toDTO.setTroopCount(to.getTroopCount());
        toDTO.setOccupierPlayerId(to.getOccupierPlayerId());
        ts.add(toDTO);
        msg.setTerritories(ts);
        client.sendMessage(msg);
    }

    @Override
    public void finishAttack() {
        model.finishAttack();
        mainWindow.setPLayer(model.getCurrentPlayer());
        mainWindow.setGamePhase(model.getCurrentPhase());
        //mainWindow.setMissionString(model.searchPlayer(clientPlayerId).getMissionCard().getMission());
        //Broadcast action
        client.sendMessage(finishAction());
    }

    private MessageDTO finishAction() {
        MessageDTO msg = new MessageDTO();
        msg.setAction("finishAction");
        msg.setCurrentPhase(model.getCurrentPhase());
        msg.setCurrentPlayerId(model.getCurrentPlayerId());
        msg.setPlayers(new ArrayList<>());
        for (Player p : model.getPlayers()) {
            PlayerDTO pdto = PlayerConverter.getDTO(p);
            msg.getPlayers().add(pdto);
        }
        return msg;
    }

    @Override
    public void finishRegroup() {
        model.finisRegroup();
        mainWindow.setPLayer(model.getCurrentPlayer());
        mainWindow.setGamePhase(model.getCurrentPhase());
        //mainWindow.setMissionString(model.searchPlayer(clientPlayerId).getMissionCard().getMission());
        //Broadcast action
        client.sendMessage(finishAction());
    }

    //author Eszti
    @Override
    public Player getWinner() {
        for (Player p : model.getPlayers()) {
            if (!p.hasKilledPlayer(p.getColor()) && p.isMissionCompleted()) {
                return p;
            }
        }

        return null;
    }

    public Model getModel() {
        return model;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public GameField getField() {
        return field;
    }

    //author Eszti
    public void startNewGame(String... playerNameList) {
        //TODO Kirajzolni Játékos adatait bekérő oldalt
        model = new Model(playerNameList);
        field = new GameField("/model/MapShape.xml");
        mainWindow = new MainWindow(this);
        field.resetTerritories();
        model.divideRandomTerritories(field.getTerritories());
        mainWindow.refreshRiskCards(model.getCurrentPlayer());
        mainWindow.setRiskCardsCanBeExchanged(false);
    }

    public static void setModel(Model model) {
        Controller.model = model;
    }

    public static void setMainWindow(MainWindow mainWindow) {
        Controller.mainWindow = mainWindow;
    }

    public static void setField(GameField field) {
        Controller.field = field;
    }

    public int getClientPlayerId() {
        return clientPlayerId;
    }

    public void setClientPlayerId(int clientPlayerId) {
        this.clientPlayerId = clientPlayerId;
    }

}
