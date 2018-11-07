/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.Socket;
import model.Model;
import model.dto.GameField;
import network.Client;
import network.Server;
import view.MainWindow;
import view.LobbyWindow;

/**
 *
 * @author Johnny
 */
public class Main {

    private static Model model;
    private static MainWindow mainWindow;
    private static GameField field;

    public static void main(String args[]) throws IOException {
        LobbyWindow lobbyWindow = new LobbyWindow();
        lobbyWindow.setVisible(true);
        while(lobbyWindow.isVisible());
        if(lobbyWindow.isNewGame())
        {
            serverm(lobbyWindow.getNumberOfPlayer());   
        }
        else
        {
            clientm(lobbyWindow.getName());
        }
    }

    private static Socket clientm(String name) throws IOException {

        Socket s = new Client(name).getSocket();

        return s;
    }

    private static void serverm(int players) throws IOException {
        Server s = new Server(players);
    }

}
