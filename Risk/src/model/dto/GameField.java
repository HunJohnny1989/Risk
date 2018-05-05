package model.dto;

import java.io.File;
import java.io.FileNotFoundException;
import model.dto.Continent;
import java.util.ArrayList;
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
    //
    
    public GameField(){
        this.continents = new ArrayList<Continent>();
    }
    
    /**
    * @author Eszti
    */
    public GameField(String fileName)
    {
        try
        {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            territories = new ArrayList<>();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("path");
            for (int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String countryName = eElement.getAttribute("id");
                    String shape = eElement.getAttribute("d");
                    Territory territory = new Territory(countryName, shape);
                    territories.add(territory);
                    System.out.println(territory.getName() + " imported.");
                }
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
}
