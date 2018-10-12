package model.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import model.MissionAgent;
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
            missions.add( new MissionCard( String.format( "Kill %s ( %s )", p.getName(), p.getColor() ),
                                           ( agent -> agent.hasKilledPlayer( p.getColor() ) ) ) ); 
        }
        
        
          missions.add(new MissionCard( String.format("Conquer Asia and South America"), 
                       agent -> hasAgentOccupiedContinents( agent, Continent.ASIA, Continent.SOUTH_AMERICA) ));
//          missions.add(new MissionCard( String.format("Conquer Asia and Africa"),
//                                            ( agent -> agent.hasOccupiedContinent()));                                           
//          missions.add(new MissionCard( String.format("Conquer North America and Africa"),
//                                            ( agent -> agent.hasOccupiedContinent()));   /*                                    
          missions.add(new MissionCard( String.format("Conquer North America and Australasia"),
                       ( agent -> hasAgentOccupiedContinents( agent, Continent.NORTH_AMERICA, Continent.AUSTRALIA, Continent.ASIA) )));   
//          missions.add(new MissionCard( String.format("Conquer Europe and South America and a 3rd continent of your choice"),
//                                            ( agent -> agent.hasOccupiedContinent()));   
//          missions.add(new MissionCard( String.format("Conquer Europe and Australasia and a 3rd continent of your choice"),
//                                            ( agent -> agent.hasOccupiedContinent()));   
//          missions.add(new MissionCard( String.format("Occupy 18 territories with at least 2 armies in each territory"),
//                                            ( agent -> agent.hasOccupiedContinent()));   
//       
//          missions.add("Occupy 24 territories (no restriction to 2 or more armies in each)"); 
          
    }

    private static boolean hasAgentOccupiedContinents(MissionAgent agent, Continent... continents ) {
        return Stream.of( continents ).collect( () -> false,
                (b, continent) -> agent.hasOccupiedContinent( continent ),
                Boolean::logicalOr) ;
    }
    
    
    public static List< MissionCard > getMissions(){
        return missions;
    }

    public static MissionCard getRandomMissionCard(Player player) {
        MissionCard card;
        //do{
            card = missions.get( random.nextInt( missions.size() ) );
       // }while(card.getPlayer().equals(player));
        missions.remove( card );
        return card;
    }

}
