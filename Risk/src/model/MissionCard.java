package model;

/**
 *
 * @author orsi
 */
public class MissionCard {  //feladatkartya
    private String mission; //kuldetes
    
    public MissionCard(String mission){
        this.mission = mission;
    }
    
    public String getMission(){
        return this.mission;
    }
}
