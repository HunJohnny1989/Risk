package model;

import model.dto.Continent;
import model.dto.Color;

/**
 *
 * @author orsi
 */
public interface MissionAgent {
    public int getOccupiedTerritoryCount();
    public int getTerritoryCountWithAtLeastTwoTroops();
    public boolean hasKilledPlayer(Color color);
    public boolean hasOccupiedContinent(Continent continent);
}
