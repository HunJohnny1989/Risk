package model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author orsi
 */
public class Missions {
    private static List<String> missions;
    static {
        missions = new ArrayList<>();
        
        missions.add("Kill a certain color: PINK"); 
        missions.add("Kill a certain color: GREEN");
        missions.add("Kill a certain color: YELLOW");
        missions.add("Kill a certain color: BLUE");
        missions.add("Kill a certain color: BLACK");
        missions.add("Kill a certain color: RED");
        
        missions.add("Conquer Asia and South America");
        missions.add("Conquer Asia and Africa");
        missions.add("Conquer North America and Africa");
        missions.add("Conquer North America and Australasia");
        missions.add("Conquer Europe and South America and a 3rd continent of your choice");
        missions.add("Conquer Europe and Australasia and a 3rd continent of your choice");

        missions.add("Occupy 18 territories with at least 2 armies in each territory");
       
        missions.add("Occupy 24 territories (no restriction to 2 or more armies in each)"); 
    }
    public static List<String> getMissions(){
        return missions;
    }
    
}
