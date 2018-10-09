package model.dto;

import java.util.function.Function;
import model.MissionAgent;
import model.Player;

/**
 *
 * @author orsi, Sajti Tamás
 */
public class MissionCard {  //feladatkartya
    private String mission; //kuldetes
    private Function< MissionAgent, Boolean > condition;
    
    public MissionCard( String mission, Function< MissionAgent, Boolean > condition ){
        this.mission = mission;
        this.condition = condition;
    }
    
    public String getMission(){
        return this.mission;
    }
    
    public boolean isCompleted( MissionAgent missionAgent ) {
        return condition.apply( missionAgent );
    }
}
