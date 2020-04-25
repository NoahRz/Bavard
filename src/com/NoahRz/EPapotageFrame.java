package com.NoahRz;

import javax.swing.*;
import java.awt.*;

public class EPapotageFrame extends JFrame {
    /**
     * This class is the frame where the Bavard can send, receive message and the concierge can create Bavard and send and receive message*/
    private PapotageListener papotageListenerLogged;
    private Concierge concierge;

    public EPapotageFrame(PapotageListener papotageListenerLogged, Concierge concierge){
        this.papotageListenerLogged = papotageListenerLogged;
        this.concierge = concierge;
        this.setTitle("Bavard page");
        this.setSize(new Dimension(1000,1000));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public EPapotageFrame(Concierge concierge) {
        this.concierge=concierge;
        this.setTitle("Concierge page");
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(1000,1000));
        JPanel pan = new JPanel();
        this.setContentPane(pan);

        this.setLayout( new GridLayout(1,2));
        /* right panel : msg Feed*/
        JPanel msgFeed = new JPanel();
        msgFeed.setSize(new Dimension(this.getWidth()/4, this.getHeight()));
        msgFeed.setLayout(new GridLayout(n,1));
        // n is the number of contact of the current user

        // Il faudra fixer la taille des messages dans le msgFeed

        /* left panel : messaging panel */
        JPanel messagingPanel = new JPanel();
        messagingPanel.setSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new GridLayout(2,1));

        JPanel messageView = new JPanel();
        messageView.setSize(new Dimension(messagingPanel.getWidth(), messagingPanel.getHeight()*4/5));
        messageView.setLayout(new GridLayout(m,1));
        messagingPanel.add(messageView);
        // m is the number of messages in the conversation between the current user and the current addressee

        JPanel messageFieldPanel = new JPanel();
        messageFieldPanel.setSize(new Dimension(messagingPanel.getWidth(), messagingPanel.getHeight()/5));
        messageFieldPanel.setLayout(new GridLayout(1,2));




        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
