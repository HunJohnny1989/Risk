package model.dto;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author orsi
 */
public class GameField {

    private List<Continent> continents;
    private List<Territory> territories;
    private Dimension dimension;
    //

    public class MyResolver implements EntityResolver {

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            InputStream myDtdRes = getClass().getResourceAsStream("/model/svg10.dtd");
            return new InputSource(myDtdRes);
        }
    }

    public GameField() {
        this.continents = new ArrayList<Continent>();
        this.territories = new ArrayList<>();
        dimension = new Dimension();
    }

    /**
     * @author Eszti
     */
    public GameField(String filePath) {
        this();

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            dBuilder.setEntityResolver(new MyResolver());
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            String[] viewBox = doc.getDocumentElement().getAttribute("viewBox").split(" ");
            dimension.setSize(Integer.valueOf(viewBox[2]) - Integer.valueOf(viewBox[0]),
                    Integer.valueOf(viewBox[3]) - Integer.valueOf(viewBox[1]));
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("path");
            String[] neighbours = new String[nList.getLength()];
            for (int territoryNo = 0; territoryNo < nList.getLength(); territoryNo++) {
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
                    //System.out.println(territory.getName() + " imported.");
                    createContinent(eElement, territory);
                }
            }
            for (int territoryNo = 0; territoryNo < neighbours.length; territoryNo++) {
                List<Territory> neighbourTerritories = new ArrayList<>();
                String[] neighbourIDs = neighbours[territoryNo].split(" ");
                for (String neighbourID : neighbourIDs) {
                    neighbourTerritories.add(territories.get(Integer.valueOf(neighbourID) - 1));
                }
                territories.get(territoryNo).setNeighbourTerritories(neighbourTerritories);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetTerritories() {
        for (Territory t : territories) {
            t.assignToPlayer(null);
            t.setTroopCount(0);
        }
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public Dimension getDimension() {
        return dimension;
    }

    /**
     *
     * @author orsi
     */
    private void createContinent(Element eElement, Territory territory) {
        String continent = eElement.getAttribute("continent");
        boolean hasAlready = false;

        for (Continent c : continents) {
            if (c.getName().equals(continent)) {
                hasAlready = true;
                c.addTerritory(territory);
                territory.setContinent(c);
                //System.out.println("For " + c.getName() + " " + territory.getName() + " added");
                break;
            }
        }

        if (!hasAlready) {
            Continent cont = Continent.parseContinent(continent);
            territory.setContinent(cont);
            continents.add(cont);
            cont.addTerritory(territory);
            //System.out.println(cont.getName() + " continent created and " + territory.getName() + " added");
        }
    }

    public List<Continent> getContinents() {
        return continents;
    }

}
