package model.dto;

/**
 *
 * @author orsi
 */
public class RiskCard { //nem vegleges
    private String type;
    
    public RiskCard(String type){
        this.type = type;
    }
    
    public String getType(){
        return this.type;
    }    
}
