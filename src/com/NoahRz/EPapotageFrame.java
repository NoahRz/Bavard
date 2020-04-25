package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

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
        ArrayList nb_rows = new ArrayList(concierge.getRecentDiscussion().keySet());
        msgFeed.setLayout(new GridLayout(nb_rows.size(),1));

        for (PapotageListener pl: concierge.getRecentDiscussion().keySet()){ // pl is a Bavard
            JPanelClickable recentDiscussionPanel = new JPanelClickable(); // it's the container of the login and the last message sent to this bavard
            recentDiscussionPanel.setLayout(new GridLayout(2,1));
            recentDiscussionPanel.addMouseListener();

            // the login of the current bavard
            JLabel recentContactLabel = new JLabel(pl.getLogin()); // the login of the current bavard

            // the body of the last message sent to this current bavard
            ArrayList<PapotageEvent> listOfPapotageEventWithPl = concierge.getRecentDiscussion().get(pl);
            JLabel lastMessageLabel = new JLabel(listOfPapotageEventWithPl.get(listOfPapotageEventWithPl.size()-1).getMessages().getBody());

            recentDiscussionPanel.add(recentContactLabel);
            recentDiscussionPanel.add(lastMessageLabel);
        }
        // n is the number of contact of the current user

        // Il faudra fixer la taille des messages dans le msgFeed

        /* left panel : messaging panel */
        JPanel messagingPanel = new JPanel();
        messagingPanel.setSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new GridLayout(2,1));

        /*Area where the user can see previous message sent*/
        JPanel messageView = new JPanel();
        messageView.setSize(new Dimension(messagingPanel.getWidth(), messagingPanel.getHeight()*4/5));
        messageView.setLayout(new GridLayout(m,1));
        messagingPanel.add(messageView);
        // m is the number of messages in the conversation between the current user and the current addressee

        /* area where the user can write messages and send them*/
        JPanel messageFieldPanel = new JPanel();
        messageFieldPanel.setSize(new Dimension(messagingPanel.getWidth(), messagingPanel.getHeight()/5));
        messageFieldPanel.setLayout(new GridLayout(1,2));
        messagingPanel.add(messageFieldPanel);




        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
