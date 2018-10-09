package model.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import model.Player;

/**
 *
 * @author orsi, Sajti Tamás
 */
public class Missions {
    private static List< MissionCard > missions;
    private static Random random = new Random();
    
    public static void init( List<Player> players ) {
        missions = new LinkedList<>();
        
        // adding kill-a-color MissionCards for each player in the game
        for( Player p: players ) {
            missions.add( new MissionCard( String.format( "Kill %s", p.getName() ),
                                           ( (agent) -> agent.hasKilledPlayer( p.getColor() ) ) ) ); 
        }
        
        //todo:
//        missions.add("Conquer Asia and South America");
//        missions.add("Conquer Asia and Africa");
//        missions.add("Conquer North America and Africa");
//        missions.add("Conquer North America and Australasia");
//        missions.add("Conquer Europe and South America and a 3rd continent of your choice");
//        missions.add("Conquer Europe and Australasia and a 3rd continent of your choice");
//
//        missions.add("Occupy 18 territories with at least 2 armies in each territory");
//       
//        missions.add("Occupy 24 territories (no restriction to 2 or more armies in each)"); 
    }
    
    public static List< MissionCard > getMissions(){
        return missions;
    }

    public static MissionCard getRandomMissionCard() {
        MissionCard card = missions.get( random.nextInt( missions.size() ) );
        missions.remove( card );
        return card;
    }

}
