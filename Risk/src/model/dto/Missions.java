package model.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import model.MissionAgent;
import model.Player;
import static model.dto.Continent.*;

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
            missions.add( new MissionCard(0, p.getName(), String.format( "Kill %s ( %s )", p.getName(), p.getColor() ),
                                           ( agent -> agent.hasKilledPlayer( p.getColor() ) ) ) ); 
        }
        
        missions.add(new MissionCard(1, String.format("Conquer Asia and South America"), 
                     agent -> agent.hasOccupiedContinents( ASIA, SOUTH_AMERICA) ));
        missions.add(new MissionCard(1, String.format("Conquer Asia and Africa"),
                     agent -> agent.hasOccupiedContinents( ASIA, AFRICA) ));                                           
        missions.add(new MissionCard(1, String.format("Conquer North America and Africa"),
                     agent -> agent.hasOccupiedContinents( NORTH_AMERICA, AFRICA) ));                                       
        missions.add(new MissionCard(1, String.format("Conquer North America and Australasia"),
                     agent -> agent.hasOccupiedContinents( NORTH_AMERICA, AUSTRALIA, ASIA) ));   
        missions.add(new MissionCard(1, String.format("Conquer Europe and South America and a 3rd continent of your choice"),
                     agent -> agent.hasOccupiedContinents( EUROPE, SOUTH_AMERICA) ));   
        missions.add(new MissionCard(1, String.format("Conquer Europe and Australasia and a 3rd continent of your choice"),
                     agent -> agent.hasOccupiedContinents( EUROPE, AUSTRALIA, ASIA) ));   
        missions.add(new MissionCard(1, String.format("Occupy 18 territories with at least 2 armies in each territory"),
                     agent -> agent.getTerritoryCountWithAtLeastTwoTroops() >= 18 ));   
        missions.add(new MissionCard(1, String.format("Occupy 24 territories (no restriction to 2 or more armies in each)"),
                     agent -> agent.getOccupiedTerritoryCount() >= 24 )); 

    }
    
    public static List< MissionCard > getMissions(){
        return missions;
    }

    public static MissionCard getRandomMissionCard(Player player) {
        MissionCard card;
        do{
            card = missions.get( random.nextInt( missions.size() ) );
        }while(card.getType() == 0 && card.getPlayerName().equals(player.getName()));
        
        missions.remove( card );
        return card;
    }

}
