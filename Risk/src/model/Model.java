package model;

import java.util.ArrayList;
import model.dto.Missions;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import model.dto.Color;
import model.dto.MissionCard;
import model.dto.RiskCard;

/**
 *
 * @author orsi
 */
public class Model {

    private static Model instance;

    private int currentPlayerIndex;
    private List< Player > players;
    private static Random random = new Random();

    public static Model getInstance() {
        return instance;
    }

    /**
     * @author Sajti Tamás
     */
    public Model( String... playerNameList ) {
        currentPlayerIndex = 0;
        players = new ArrayList<>( playerNameList.length );
        for( int i = 0; i < playerNameList.length; i++ )
            players.add( new Player( playerNameList[ i ], Color.values()[ i ], randomMissionCard()) );
        instance = this;
    }

    /**
     * @author Sajti Tamás
     */
    public PlayerInterface getCurrentPlayer() {
        return getCurrentPlayerInternal();
    }

    /**
     * @author Sajti Tamás
     */
    public void playerLost( Player player ) {
        players.remove(player);
    }

    /**
     * @author Sajti Tamás
     */
    public void giveRiskCardIfNecessary() {
        if( getCurrentPlayerInternal().hasOccupiedTerritoryThisRound() ) 
            getCurrentPlayerInternal().addRiskCard( randomRiskCard() );
    }

    /**
     * @author Sajti Tamás
     */
    public void selectNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void addPlayer(Player player) {
        if(players == null) players = new ArrayList<>();
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @author Sajti Tamás
     */
    private Player getCurrentPlayerInternal() {
        return players.get(currentPlayerIndex);
    }

    private MissionCard randomMissionCard() {
        return new MissionCard( Missions.getMissions().get( random.nextInt( Missions.getMissions().size() ) ) );
    }

    private RiskCard randomRiskCard() {   // todo
//        random.nextInt( )
        return new RiskCard("");
    }

}
