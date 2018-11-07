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

/**
 *
 * @author Johnny
 */
public class Main {

    private static Model model;
    private static MainWindow mainWindow;
    private static GameField field;

    public static void main(String args[]) throws IOException {
        //serverm();

        clientm();
    }

    private static Socket clientm() throws IOException {

        Socket s = new Client("Józsi1").getSocket();

        return s;
    }

    private static void serverm() throws IOException {
        Server s = new Server(2);
    }

}
