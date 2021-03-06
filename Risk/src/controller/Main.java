/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Dialog;
import java.io.IOException;
import java.net.Socket;
import model.Model;
import model.dto.GameField;
import network.Client;
import network.Server;
import view.GameOver;
import view.MainWindow;
import view.LobbyWindow;

/**
 *
 * @author Johnny
 */
public class Main {

    public static void main(String args[]) throws IOException {
        LobbyWindow lobbyWindow = new LobbyWindow(null, true);
        lobbyWindow.setVisible(true);
        switch(lobbyWindow.getChosenOption())
        {
            case NEWSERVER:
                serverm(lobbyWindow.getNumberOfPlayer()); 
                break;
             
            case NEWCLIENT:
                clientm(lobbyWindow.getName(), lobbyWindow.getHostAddress());
                break;
                
            default:
                break;
        }
    }

    private static Socket clientm(String name, String ipAddress) throws IOException {

        Socket s = new Client(name, ipAddress).getSocket();

        return s;
    }

    private static void serverm(int players) throws IOException {
        Server s = new Server(players);
    }

}
