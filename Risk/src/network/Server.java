/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import controller.Controller;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Player;
import model.dto.Territory;
import network.converter.PlayerConverter;

/**
 *
 * @author Johnny + orsi
 */
public class Server {

    private ServerSocket serverSocket;
    private ArrayList<ClientThread> clients;
    private int playerCount;
    private List<String> playerNames;
    private Controller controller;    
    private static final int PORT = 32123; 

    public Server(int playerCount) throws IOException {
        serverSocket = new ServerSocket(PORT);
        clients = new ArrayList<>();
        playerNames = new ArrayList<>();
        this.playerCount = playerCount;

        startServer();
        controller = new Controller();
        controller.startNewGame(playerNames.toArray(new String[playerNames.size()]));
        System.out.println("Game started.");
        //ObjectOutputStream outToClient = new ObjectOutputStream(sockets.get(0).getOutputStream());
        //outToClient.writeObject();
        broadcast(buildStartMessage(controller));
    }

    public void startServer() {
        try {
            System.out.println("Server started. Waiting for " + playerCount + " player.");
            int i = 0;
            while (i < playerCount) {
                Socket socket = serverSocket.accept();
                ClientThread ct = new ClientThread(socket, i + 1);
                clients.add(ct);
                ct.start();
                i++;
            }
            System.out.println("All players joined.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MessageDTO buildStartMessage(Controller controller) {
        MessageDTO msg = new MessageDTO();
        msg.setAction("gameStart");
        msg.setPlayers(new ArrayList<>());
        msg.setCurrentPhase(controller.getCurrentPhase());
        msg.setCurrentPlayerId(controller.getModel().getCurrentPlayer().getPlayerId());
        for (Player p : controller.getModel().getPlayers()) {
            PlayerDTO pdto = PlayerConverter.getDTO(p);
            //msg.getPlayers().add(pdto);
            msg.addPlayer(pdto);
        }
        msg.setTerritories(new ArrayList<>());
        for (Territory t : controller.getField().getTerritories()) {
            TerritoryDTO tdto = new TerritoryDTO();
            tdto.setName(t.getName());
            tdto.setOccupierPlayerId(t.getOccupierPlayerId());
            tdto.setTroopCount(t.getTroopCount());
            //msg.getTerritories().add(tdto);
            msg.addTerritory(tdto);
        }
        return msg;
    }

    private void broadcast(MessageDTO message) {
        for (int i = clients.size(); --i >= 0;) {
            ClientThread ct = clients.get(i);
            if (!ct.writeMsg(message)) {
                clients.remove(i);
                System.out.println("Disconnected Client " + ct.playerName + " removed from list.");
            }
        }
    }

    class ClientThread extends Thread {

        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;

        String playerName;

        MessageDTO msg;
        boolean alive = true;

        ClientThread(Socket socket, int clientNumber) {

            this.socket = socket;
            try {

                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                playerName = (String) sInput.readObject();
                playerNames.add(playerName);
                System.out.println("Connection accepted from: " + playerName);
                sOutput.writeObject(msg);
                sOutput.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            while (alive) {
                try {
                    System.out.println("várás ");
                    msg = (MessageDTO) sInput.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                    break;
                }

                if( msg != null )
                    switch (msg.getAction()) {
                        case "addTroopToTerritory":
                            broadcast(msg);
                            break;
                        case "setTerritory":
                            broadcast(msg);
                            break;
                        case "finishAction":
                            broadcast(msg);
                            break;
                        case "chatMessage":
                            broadcast(msg);
                            //System.out.println(msg.getChatMessage());
                            break;
                        case "closeConnection":
                        default:
                            closeClientConnection(msg);
                            break;
                    }
            }
            // remove myself from the arrayList containing the list of the
            // connected Clients
            //remove(id);
            //close();
        }
        
        /**
        *
        * @author orsi1
        */
        private void closeClientConnection(MessageDTO msg) {
            System.out.println("client close connection: " + msg.getPlayerName());
            int removslCtIndex = 0;
            boolean foundCt = false;
            for(ClientThread ct : clients){
                if(ct.playerName.equals(msg.getPlayerName())){
                    foundCt = true;
                    ct.alive = false;
                    removslCtIndex = clients.indexOf(ct);
                }
            }
            
            if(foundCt){
                ClientThread ct = clients.get(removslCtIndex);
                clients.remove(ct);
                System.out.println("Disconnected Client " + msg.getPlayerName() + " removed from list.");
                ct.closeClient();
                closeServerSocket();
            }
            
        }
        
        private void closeServerSocket(){
            if(clients.isEmpty()){
                try {
                    
                    System.out.println("Server closed connection");
                    serverSocket.close();
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private synchronized void closeClient() {
            try {
                if (sOutput != null) {
                    sOutput.close();
                }
            } catch (Exception e) {
            }
            try {
                if (sInput != null) {
                    sInput.close();
                }
            } catch (Exception e) {
            };
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
            }
        }

        private synchronized boolean writeMsg(MessageDTO msg) {
            if (!socket.isConnected()) {
                closeClient();
                return false;
            }
            try {
                msg.setPlayerName(playerName);
                sOutput.writeObject(msg);
                sOutput.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
