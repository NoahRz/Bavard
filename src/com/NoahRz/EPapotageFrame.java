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

        JPanel msgFeedPanel = new JPanel();
        msgFeedPanel.setBackground(Color.BLUE);
        msgFeedPanel.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
        ArrayList recentContact = new ArrayList(concierge.getRecentDiscussion().keySet()); // recentContact is a list of contact we had a discussion with
        System.out.println(recentContact);
        msgFeedPanel.setLayout(new FlowLayout()); // we could have used a GridLayout but component wouldn't be resizable
        int i=0;
        ArrayList<JPanelClickable> recentDiscussionList = new ArrayList<JPanelClickable>();
        for (PapotageListener pl: concierge.getRecentDiscussion().keySet()) { // pl is a Bavard
            JPanelClickable recentDiscussionPanel = new JPanelClickable(i); // it's the container of the login and the last message sent to this bavard
            recentDiscussionPanel.setPreferredSize(new Dimension(this.getWidth() / 4, this.getHeight() / 10));
            recentDiscussionPanel.setLayout(new GridLayout(2, 1));
            recentDiscussionPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    recentDiscussionPanel.setIsClicked(true);
                    for (JPanelClickable myRecentDiscussionPanel : recentDiscussionList) {
                        // we unclick other recentDiscussionPanel, so there is only one clicked(selected)
                        if(myRecentDiscussionPanel!=recentDiscussionPanel) {
                            myRecentDiscussionPanel.setIsClicked(false);
                        }
                    }
                }
            });
            recentDiscussionList.add(recentDiscussionPanel);


            // the login of the current bavard
            JLabel recentContactLabel = new JLabel(pl.getLogin()); // the login of the current bavard (contact)

            // the body of the last message sent to this current bavard
            ArrayList<PapotageEvent> listOfPapotageEventWithPl = concierge.getRecentDiscussion().get(pl);
            JLabel lastMessageLabel = new JLabel(listOfPapotageEventWithPl.get(listOfPapotageEventWithPl.size()-1).getMessages().getBody());

            recentDiscussionPanel.add(recentContactLabel);
            recentDiscussionPanel.add(lastMessageLabel);
            msgFeedPanel.add(recentDiscussionPanel);
            i++;
        }
        this.add(msgFeedPanel,BorderLayout.WEST);

        // Il faudra fixer la taille des messages dans le msgFeedPanel

        /* ****************************** */
        /* left panel :  messaging panel  */
        /* ****************************** */

        JPanel messagingPanel = new JPanel();
        messagingPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new BorderLayout());

        /*Area where the user can see previous message sent*/
        JPanel messageView = new JPanel();
        messageView.setBackground(Color.YELLOW);
        messageView.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()*3/4));
        //int m=1;
        //messageView.setLayout(new GridLayout(m,1));
        messageView.setLayout(new FlowLayout());



        messagingPanel.add(messageView, BorderLayout.NORTH);
        // m is the number of messages in the conversation between the current user and the current addressee

        /* area where the user can write messages and send them*/
        JPanel messageFieldPanel = new JPanel();
        messageFieldPanel.setBackground(Color.GREEN);
        messageFieldPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/4));
        messageFieldPanel.setLayout(new FlowLayout());
        JTextArea messagingTextArea = new JTextArea("write your message ... "); //area where the user can write message
        messagingTextArea.setPreferredSize(new Dimension(this.getWidth()*6/10 ,this.getHeight()/4));

        JButton sendMessageButton = new JButton("Envoyer");
        sendMessageButton.setPreferredSize(new Dimension(this.getWidth()/10, this.getHeight()/15));


        messageFieldPanel.add(messagingTextArea);
        messageFieldPanel.add(sendMessageButton);


        messagingPanel.add(messageFieldPanel);


        this.add(messagingPanel, BorderLayout.CENTER);


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
