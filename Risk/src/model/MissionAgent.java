package model;

import model.dto.Continent;
import model.dto.Color;

/**
 *
 * @author orsi
 */
public interface MissionAgent {
    int getOccupiedTerritoryCount();
    int getTerritoryCountWithAtLeastTwoTroops();
    boolean hasKilledPlayer(Color color);
    boolean hasOccupiedContinents(Continent... continents);
}
