package model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author orsi
 */
public class Model {
    
    private static Model instance;
    
    private int selectedPlayerIndex;
    private List< Player > players;

    public static Model getInstance() {
        return instance;
    }
    
    /**
    * @author Sajti Tamás
    */
    public Model( List<Player> players ) {
        this.players = players;
        selectedPlayerIndex = 0;
        instance = this;
    }
    
    /**
    * @author Sajti Tamás
    */
    public Player getSelectedPlayer(){
        return players.get( selectedPlayerIndex );
    }
    
    /**
    * @author Sajti Tamás
    */
    public void playerLost( Player player ){
        players.remove( player );
        //controller.playerLost( player );
    }
    
    public void giveRiskCardIfNecessary(){
        
    }
    
    /**
    * @author Sajti Tamás
    */
    public void selectNextPlayer(){
        selectedPlayerIndex = ( selectedPlayerIndex + 1 ) % players.size();
    }
    
}
