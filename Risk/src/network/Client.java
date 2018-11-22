/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import controller.Controller;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import model.Model;
import model.Player;
import model.dto.GameField;
import model.dto.Territory;
import network.converter.PlayerConverter;
import view.ChatWindow;
import view.MainWindow;

/**
 *
 * @author Johnny
 */
public class Client {

    private Socket socket;
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Controller controller;
    private ChatWindow chatWindow;
    private boolean windowIsClosed = false;

    public Client(String name) {
        System.out.println("Connecting");
        windowIsClosed = false;
        try {

            socket = new Socket("localhost", 7777);
            System.out.println("Connection succesfull");

            sOutput = new ObjectOutputStream(socket.getOutputStream());
            sInput = new ObjectInputStream(socket.getInputStream());

            sOutput.writeObject(name);

            buildGui();
            startClient();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void buildGui() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }

    /**
     * Thread waiting for server messages.
     */
    private void startClient() throws IOException {

        new ListenFromServer().start();
    }

    class ListenFromServer extends Thread {

        public void run() {
            while (!windowIsClosed) {
                try {
                    MessageDTO msg = (MessageDTO) sInput.readObject();
                    handleEvent(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (ClassNotFoundException e2) {
                }
            }
        }
    }

    private void handleEvent(MessageDTO msg) {
        if (msg == null) {
            return;
        }
        switch (msg.getAction()) {
            case "gameStart":
                initGame(msg);
                break;
            case "addTroopToTerritory":
                addTroopToTerritory(msg);
                break;
            case "setTerritory":
                setTerritories(msg);
                break;
            case "finishAction":
                finishAction(msg);
                break;
            case "chatMessage":
                readChatMessage(msg);
                break;
        }
    }

    /**
    *
    * @author orsi1
    */
    private void readChatMessage(MessageDTO msg){
        chatWindow.setIncoming(msg.getChatMessage());
    }
    
    private void finishAction(MessageDTO msg) {
        setPlayers(msg);
        controller.getModel().setCurrentPlayerId(msg.getCurrentPlayerId());
        controller.getModel().setCurrentPhase(msg.getCurrentPhase());
        controller.getMainWindow().setGamePhase(msg.getCurrentPhase());
        controller.getMainWindow().setCurrentPlayer(controller.getModel().getCurrentPlayer());
        controller.getMainWindow().setClientPlayer(controller.getModel().searchPlayer(controller.getClientPlayerId()));
        controller.getMainWindow().repaint();
    }

    private void setTerritories(MessageDTO msg) {
        for (TerritoryDTO tdto : msg.getTerritories()) {
            for (Territory t : controller.getField().getTerritories()) {
                if (tdto.getName().equals(t.getName())) {
                    t.setTroopCount(tdto.getTroopCount());
                    t.setOccupierPlayerId(tdto.getOccupierPlayerId());
                }
            }
        }
        controller.getMainWindow().repaint();
    }

    private void setPlayers(MessageDTO msg) {
        List<Player> pls = new ArrayList<Player>();
        for (PlayerDTO pdto : msg.getPlayers()) {
            Player p = PlayerConverter.getPlayer(pdto, controller.getField());
            pls.add(p);
        }
        controller.getModel().setPlayers(pls);
        controller.getMainWindow().repaint();
    }

    private void addTroopToTerritory(MessageDTO msg) {
        controller.getModel().setCurrentPlayerId(msg.getCurrentPlayerId());
        controller.getModel().setCurrentPhase(msg.getCurrentPhase());
        controller.getMainWindow().setGamePhase(msg.getCurrentPhase());
        controller.getMainWindow().setCurrentPlayer(controller.getModel().getCurrentPlayer());
        controller.getMainWindow().setMissionString(controller.getModel().searchPlayer(controller.getClientPlayerId()).getMissionCard().getMission());
        for (TerritoryDTO tdto : msg.getTerritories()) {
            for (Territory t : controller.getField().getTerritories()) {
                if (tdto.getName().equals(t.getName())) {
                    t.setTroopCount(tdto.getTroopCount());
                }
            }
        }
        setPlayers(msg);
        controller.getMainWindow().repaint();
    }

    private void initGame(MessageDTO msg) {
        controller = new Controller(this);
        MainWindow mainWindow = new MainWindow(controller);
        controller.setMainWindow(mainWindow);
        mainWindow.setVisible(true);
        closeWindowListener(mainWindow);
        
        chatWindow = new ChatWindow(controller);
        chatWindow.setName(msg.getPlayerName());
        chatWindow.setVisible(true);

        GameField field = new GameField("/model/MapShape.xml");
        controller.setField(field);

        Model model = new Model();
        controller.setModel(model);
        model.setCurrentPhase(msg.getCurrentPhase());
        model.setCurrentPlayerId(msg.getCurrentPlayerId());
        for (PlayerDTO pdto : msg.getPlayers()) {
            if(pdto.getName().equalsIgnoreCase(msg.getPlayerName()))
            {
                mainWindow.setTitle(String.format("Risk - %s (%s)", msg.getPlayerName(), pdto.getColor()));
            }
            Player p = PlayerConverter.getPlayer(pdto, controller.getField());
            model.getPlayers().add(p);
        }
        controller.setClientPlayerId(controller.getModel().searchPlayerId(msg.getPlayerName()));

        field.resetTerritories();
        for (TerritoryDTO tdto : msg.getTerritories()) {
            for (Territory t : field.getTerritories()) {
                if (t.getName().equals(tdto.getName())) {
                    t.setOccupierPlayerId(tdto.getOccupierPlayerId());
                    t.setTroopCount(tdto.getTroopCount());
                }
            }
        }
        mainWindow.setCurrentPlayer(controller.getModel().getCurrentPlayer());
        mainWindow.setClientPlayer(controller.getModel().searchPlayer(controller.getClientPlayerId()));
        mainWindow.setGamePhase(msg.getCurrentPhase());

        mainWindow.setGameField(field);
    }

    public Socket getSocket() {
        return socket;
    }

    public void sendMessage(MessageDTO msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void closeWindowListener(JFrame frame){
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to close this window?", "Close Window?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.out.println("The window is closed");
                    closeConnection();
                }
            }
        });
    }
    
    public void closeConnection(){
        windowIsClosed = true;
        MessageDTO closeMsg = new MessageDTO();
        closeMsg.setCurrentPlayerId(controller.getClientPlayerId());
        closeMsg.setPlayerName(controller.getModel().searchPlayer(controller.getClientPlayerId()).getName());
        closeMsg.setAction("closeConnection");
        sendMessage(closeMsg);
    }
}
