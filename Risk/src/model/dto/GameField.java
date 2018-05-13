package model.dto;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import model.dto.Continent;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 *
 * @author orsi
 */
public class GameField {
    private List<Continent> continents;
    private List<Territory> territories;
    private Dimension dimension;
    //
    
    public GameField(){
        this.continents = new ArrayList<Continent>();
        this.territories = new ArrayList<>();
        dimension = new Dimension();
    }
    
    /**
    * @author Eszti
    */
    public GameField(String fileName)
    {
        this();
        
        try
        {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            String[] viewBox = doc.getDocumentElement().getAttribute("viewBox").split(" ");
            dimension.setSize(Integer.valueOf(viewBox[2])- Integer.valueOf(viewBox[0]),
                    Integer.valueOf(viewBox[3])- Integer.valueOf(viewBox[1]));
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("path");
            String[] neighbours = new String[nList.getLength()];
            for (int territoryNo = 0; territoryNo < nList.getLength(); territoryNo++){
                Node nNode = nList.item(territoryNo);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //String id = eElement.getAttribute("id");
                    String countryName = eElement.getAttribute("name");
                    String centerPointX = eElement.getAttribute("x");
                    String centerPointY = eElement.getAttribute("y");
                    Point2D.Float centerPoint = new Point2D.Float(
                            Float.valueOf(centerPointX), Float.valueOf(centerPointY));
                    neighbours[territoryNo] = eElement.getAttribute("neighbours");
                    String shape = eElement.getAttribute("d");
                    Territory territory = new Territory(countryName, shape, centerPoint);
                    territories.add(territory);
                    System.out.println(territory.getName() + " imported.");
                }
            }
            for(int territoryNo = 0; territoryNo < neighbours.length; territoryNo++){
                List<Territory> neighbourTerritories = new ArrayList<>();
                String[] neighbourIDs = neighbours[territoryNo].split(" ");
                for(String neighbourID:neighbourIDs)
                {
                    neighbourTerritories.add(territories.get(Integer.valueOf(neighbourID)-1));
                }
                territories.get(territoryNo).setNeighbourTerritories(neighbourTerritories);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public List<Territory> getTerritories(){
        return territories;
    }
    
    public Dimension getDimension()
    {
        return dimension;
    }
}