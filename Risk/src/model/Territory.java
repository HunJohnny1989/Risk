package model;

import java.util.List;

/**
 *
 * @author orsi
 */
public class Territory {
    private String name;
    private boolean occupied; //elfoglalt-e
    private List<Territory> neighbours; //szomszedos korzetek
    
    public Territory(String name, List<Territory> neighbours){
        this.name = name;
        this.occupied = false;
        this.neighbours = neighbours;
    }
    
    public String getName(){
        return this.name;
    }
    
    public boolean isOccupied(){
        return this.occupied;
    }
    
    public List<Territory> getNeighbours(){
        return this.neighbours;
    }
    
}
