package model;

import java.util.ArrayList;
import model.dto.Missions;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import model.dto.MissionCard;

/**
 *
 * @author orsi
 */
public class Model {

    private static Model instance;

    private int selectedPlayerIndex;
    private List< Player> players;

    public static MissionCard getMissionCard() {
        Missions missions = new Missions();
        Random r = new Random();
        return new MissionCard(missions.getMissions().get(r.nextInt(missions.getMissions().size())));
    }

    public static Model getInstance() {
        return instance;
    }

    /**
     * @author Sajti Tamás
     */
    public Model() {
        selectedPlayerIndex = 0;
        instance = this;
    }

    /**
     * @author Sajti Tamás
     */
    public Player getSelectedPlayer() {
        return players.get(selectedPlayerIndex);
    }

    /**
     * @author Sajti Tamás
     */
    public void playerLost(Player player) {
        players.remove(player);
        //controller.playerLost( player );
    }

    public void giveRiskCardIfNecessary() {

    }

    /**
     * @author Sajti Tamás
     */
    public void selectNextPlayer() {
        selectedPlayerIndex = (selectedPlayerIndex + 1) % players.size();
    }

    public void addPlayer(Player player) {
        if(players == null) players = new ArrayList<>();
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
