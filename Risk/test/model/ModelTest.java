/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import model.dto.Color;
import model.dto.Continent;
import model.dto.Phase;
import model.dto.Territory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Sajti Tamás
 * 
 * For testing the model layer
 */
public class ModelTest {
    
    Model model;
    
    public ModelTest() {
        model = new Model("Eszti", "Orsi", "John", "Tomi");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isPlayerDead method, of class Model.
     */
    @Test
    public void testIsPlayerDead() {
        System.out.println("isPlayerDead");
        
        assertFalse(model.isPlayerDead(Color.GREEN));
        assertFalse(model.isPlayerDead(Color.BLUE));
        assertTrue(model.isPlayerDead(Color.BLACK));
        assertTrue(model.isPlayerDead(Color.RED));
        
        // blue loses
        model.playerLost(model.getPlayers().stream().filter( p -> p.getColor() == Color.BLUE ).findFirst().get());
        assertTrue(model.isPlayerDead(Color.BLUE));
    }
    
    /**
     * Test of parseContinent method, of class Continent.
     */
    @Test
    public void testParseContinent() {
        System.out.println("testParseContinent");
        
        String continentName = "South America";
        Continent continent = Continent.parseContinent(continentName);
        
        assertEquals( continent.getName(), continentName );
        assertEquals( continent.getTroopBonusCount(), 2 );
    }
    
}
