package com.NoahRz;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        /* ************************ */
        /* right panel : msg Feed   */
        /* ************************ */

        JPanel msgFeed = new JPanel();
        msgFeed.setBackground(Color.BLUE);
        msgFeed.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
        ArrayList recentContact = new ArrayList(concierge.getRecentDiscussion().keySet()); // recentContact is a list of contact we had a discussion with
        msgFeed.setLayout(new GridLayout(recentContact.size(),1));
        int i=0;
        for (PapotageListener pl: concierge.getRecentDiscussion().keySet()){ // pl is a Bavard
            JPanelClickable recentDiscussionPanel = new JPanelClickable(i); // it's the container of the login and the last message sent to this bavard
            recentDiscussionPanel.setLayout(new GridLayout(2,1));
            recentDiscussionPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    recentDiscussionPanel.setIsClicked(true);
                }
            });

            // the login of the current bavard
            JLabel recentContactLabel = new JLabel(pl.getLogin()); // the login of the current bavard (contact)

            // the body of the last message sent to this current bavard
            ArrayList<PapotageEvent> listOfPapotageEventWithPl = concierge.getRecentDiscussion().get(pl);
            JLabel lastMessageLabel = new JLabel(listOfPapotageEventWithPl.get(listOfPapotageEventWithPl.size()-1).getMessages().getBody());

            recentDiscussionPanel.add(recentContactLabel);
            recentDiscussionPanel.add(lastMessageLabel);
            i++;
        }
        this.add(msgFeed,BorderLayout.WEST);

        // Il faudra fixer la taille des messages dans le msgFeed

        /* left panel : messaging panel */
        JPanel messagingPanel = new JPanel();
        messagingPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new BorderLayout());

        /*Area where the user can see previous message sent*/
        JPanel messageView = new JPanel();
        messageView.setBackground(Color.YELLOW);
        messageView.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()*3/4));
        System.out.println(messageView.getPreferredSize());
        int m=1;
        messageView.setLayout(new GridLayout(m,1));
        messagingPanel.add(messageView, BorderLayout.NORTH);
        // m is the number of messages in the conversation between the current user and the current addressee

        /* area where the user can write messages and send them*/
        JPanel messageFieldPanel = new JPanel();
        messageFieldPanel.setBackground(Color.GREEN);
        messageFieldPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/4));
        System.out.println(messageFieldPanel.getPreferredSize());
        messageFieldPanel.setLayout(new GridLayout(1,2));
        messagingPanel.add(messageFieldPanel,BorderLayout.SOUTH);

        this.add(messagingPanel, BorderLayout.CENTER);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
