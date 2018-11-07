package model.dto;

import java.io.Serializable;
import java.util.function.Function;
import model.MissionAgent;
import model.Player;

/**
 *
 * @author orsi, Sajti Tamás
 */
public class MissionCard  implements Serializable{  //feladatkartya
    
    private static final long serialVersionUID = 1L;
    private int type;
    private String mission; //kuldetes
    private Function< MissionAgent, Boolean > condition;
    private String playerName = "";
    
    public MissionCard(int type, String mission, Function< MissionAgent, Boolean > condition ){
        this.type = type;
        this.mission = mission;
        this.condition = condition;
    }
    
    public MissionCard(int type, String playerName, String mission, Function< MissionAgent, Boolean > condition ){
        this(type, mission, condition);
        this.playerName = playerName;
    }
    
    
    public int getType(){
        return this.type;
    }
    
    public String getMission(){
        return this.mission;
    }
    
    public boolean isCompleted( MissionAgent missionAgent ) {
        return condition.apply( missionAgent );
    }
    
    public String getPlayerName(){
        return this.playerName;
    }
}
