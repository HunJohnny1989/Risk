/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import model.*;
import model.dto.Color;

/**
 *
 * @author Johnny
 */
public class Controller {

    public static Model model;
    
    
    public static void main(String[] args){
        
        //TODO Kirajzolni Játékos adatait bekérő oldalt
        model = new Model();
        
    }

    //Rákattintunk az új játékos létrehozása gombra
    public void createNewPlayer(String name, Color color){
        model.addPlayer(new Player(name, color, Model.getMissionCard()));        
    }
    
    //Rákattintunk a játék kezdése gombra
    public void startGame(ActionEvent event){
        //TODO Kirajzolni a táblát, ekkor a játékos lerakhatja a kezdeti katonáit
        
        //ha elfogytak a "kézben" lévő katonák kezdődik a kör        
        
    }
    
    public void handleEvent(Event event) {

        /*Kölönböző esetek amiket le kell kezelni
        1. Támad
        2. Passzol
        3. Átcsoportosít
        4. Erősít
        */
        switch (event.id) {
            case Event.KEY_PRESS:
                break;
            case Event.KEY_ACTION:
                break;
            case Event.KEY_RELEASE:
                break;
            case Event.MOUSE_DOWN:
                break;
            case Event.MOUSE_UP:
                break;
            case Event.MOUSE_DRAG:
                break;
        }

    }

}
