package model.dto;

import java.awt.geom.*;
import model.dto.Continent;
import java.util.List;
import java.util.Scanner;
import model.Player;

/**
 *
 * @author orsi
 */
public class Territory {
    private String name;
    private int troopCount;
    private List<Territory> neighbourTerritories; //szomszedos korzetek
    private Player occupierPlayer;
    private Continent continent;
    private GeneralPath shape;
    private Point2D.Float centerPoint;
    
    public Territory(String name, Continent continent, List<Territory> neighbourTerritories){
        this.name = name;
        this.continent = continent;
        this.troopCount = 0; //nem vegleges
        this.neighbourTerritories = neighbourTerritories;
    }
    
    /**
    * @author Eszti
    */
    public Territory(String name, String shape)
    {
        this.troopCount = 0;
        this.name = name;
        createShape(shape);
    }

    /**
    *
    * @author orsi
    */
    public Continent getContinent() {
        return continent;
    }

    public int getTroopCount() {
        return troopCount;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Player getOccupierPlayer(){
        return this.occupierPlayer;
    }
   
    public List<Territory> getNeighbourTerritories(){
        return this.neighbourTerritories;
    }
    
    /**
    * @author Sajti Tamás
    */
    public void assignToPlayer( Player player ){
        occupierPlayer = player;
    }

    /**
    * @author Sajti Tamás
    */
    public void addTroops( int troopCount ) {
        this.troopCount += troopCount;
    }
    
    /**
    * @author Sajti Tamás
    */
    public void removeTroops(int troopCount) {
        this.troopCount -= troopCount;
    }
    
    /**
    * @author Eszti
    */
    private Point2D getPointFromScanner(Scanner scanner)
    {
        return new Point2D.Double(scanner.nextDouble(), scanner.nextDouble());
    }
    
    private void createShape(String shapeString)
    {
        shape = new GeneralPath();
        Scanner scanner = new Scanner(shapeString);
        scanner.useLocale(java.util.Locale.US);
        scanner.useDelimiter("\\n|,|\\s* \\s*");
        while (scanner.hasNext()) {
            String nextToken = scanner.next();
            switch(nextToken)
            {
                case "M":
                    Point2D point = getPointFromScanner(scanner);
                    shape.moveTo(point.getX(), point.getY());
                    centerPoint = new Point2D.Float((float)point.getX(), (float)point.getY());
                    break;
                case "C":
                    do{
                    Point2D controlPoint1 = getPointFromScanner(scanner);
                    Point2D controlPoint2 = getPointFromScanner(scanner);
                    Point2D endPoint = getPointFromScanner(scanner);
                    shape.curveTo(controlPoint1.getX(), controlPoint1.getY(), controlPoint2.getX(), controlPoint2.getY(), endPoint.getX(), endPoint.getY());
                    }while(scanner.hasNextDouble());
                    break;
                case "Z":
                    shape.closePath();
                    break;
            }
        }
        scanner.close();
    }

    public GeneralPath getShape()
    {
        return shape;
    }
    
    public Point2D.Float getCenterPoint(){
        return centerPoint;
    }
}
