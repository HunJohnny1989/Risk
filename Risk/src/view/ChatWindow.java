/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author orsi1
 */

import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import network.Client;

public class ChatWindow extends JFrame{
    private JTextArea incoming;
    private JTextField outgoing;
    private Controller controller;
    private String name;

    public ChatWindow(Controller controller) {
        super("Chat Window");
        this.name = name;
        this.controller = controller;
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea(15, 50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        mainPanel.add(qScroller);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        this.getContentPane().add(BorderLayout.CENTER, mainPanel);
        this.setSize(650, 500);
    }
    
    public void setName(String name){
        this.name = name;
    }

    public void setIncoming(String message){
        this.incoming.append(message + "\n");
    }

    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            try {
                //System.out.println(outgoing.getText());
                controller.sendChatMessage(name + ": " + outgoing.getText());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }
}
