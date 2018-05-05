package model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author orsi
 */
public class Missions {
    private List<String> missions;
    
    public Missions(){
        this.missions = new ArrayList<>();
        
        this.missions.add("Kill a certain color: PINK"); 
        this.missions.add("Kill a certain color: GREEN");
        this.missions.add("Kill a certain color: YELLOW");
        this.missions.add("Kill a certain color: BLUE");
        this.missions.add("Kill a certain color: BLACK");
        this.missions.add("Kill a certain color: RED");
        
        this.missions.add("Conquer Asia and South America");
        this.missions.add("Conquer Asia and Africa");
        this.missions.add("Conquer North America and Africa");
        this.missions.add("Conquer North America and Australasia");
        this.missions.add("Conquer Europe and South America and a 3rd continent of your choice");
        this.missions.add("Conquer Europe and Australasia and a 3rd continent of your choice");

        this.missions.add("Occupy 18 territories with at least 2 armies in each territory");
       
        this.missions.add("Occupy 24 territories (no restriction to 2 or more armies in each)");        
    }    
    
    public List<String> getMissions(){
        return missions;
    }
    
}
