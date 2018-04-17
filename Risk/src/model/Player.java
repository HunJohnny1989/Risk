package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author orsi
 */
public class Player {
    private String name;
    private String color;
    private MissionCard missionCard;
    private List<RiskCard> riskCards;
    
    public Player(String name, String color, MissionCard missionCard){
        this.name = name;
        this.color = color;
        this.missionCard = missionCard;
        this.riskCards = new ArrayList<RiskCard>();
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getColor(){
        return this.color;
    }
    
    public MissionCard getMissionCard(){
        return this.missionCard;
    }
    public List<RiskCard> getRiskCards(){
        return this.riskCards;
    }
    
}
