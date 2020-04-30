package com.NoahRz;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.Set;

public class EPapotageFrame extends JFrame {
    /**
     * This class is the frame where the Bavard can send, receive message and the concierge can create Bavard and send and receive message*/
    private Bavard bavardLogged;
    private Concierge concierge;
    private JPanel messagingPanel; /* right panel :  where we display messages of the discussion selected and the area to send messages */
    private JPanel messageViewPanel; /* right panel :  where we display messages of the discussion selected */

    public EPapotageFrame(Concierge concierge){
        this.concierge = concierge;
        this.setTitle("Bavard page");
        this.setSize(new Dimension(1000,1000));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public EPapotageFrame(PapotageListener papotageListenerLogged, Concierge concierge) {
        this.bavardLogged = (Bavard)papotageListenerLogged;
        this.concierge=concierge;
        this.setTitle("Bavard page");
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(1000,1000));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        EPapotageFrame frame = this;

        /* ****************************** */
        /* right panel :  messaging panel  */
        /* ****************************** */

        this.messagingPanel = new JPanel();
        messagingPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new BorderLayout());

        /*Area where the user can see previous message sent*/
        messageViewPanel = new JPanel();
        messageViewPanel.setBackground(Color.YELLOW);
        messageViewPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()*3/4));
        messageViewPanel.setLayout(new FlowLayout());

        //we display the discussion of the discussion selected

//        for (int j=0; j<=1; j++){
//            JPanel oneMessagePanel = new JPanel();
//            oneMessagePanel.setLayout(new BorderLayout());
//
//            JTextPane messageTextPane = new JTextPane();
//            messageTextPane.setText("voici un message");
//            Dimension d = messageTextPane.getPreferredSize(); // we do that to only modify one dimension
//            d.width = this.getWidth()*2/10;
//            messageTextPane.setPreferredSize(d);
//            oneMessagePanel.add(messageTextPane, BorderLayout.WEST);
//
//            JPanel blankPanel = new JPanel();
//            blankPanel.setBackground(Color.BLUE);
//            blankPanel.setPreferredSize(new Dimension(this.getWidth()/2, 10));
//            //blankPanel.setPreferredSize(new Dimension(this.getWidth()/2, messageTextPane.getHeight()));
//            oneMessagePanel.add(blankPanel, BorderLayout.CENTER);
//
//            messageViewPanel.add(oneMessagePanel);
//        }

        messagingPanel.add(messageViewPanel, BorderLayout.NORTH);
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

        /* ************************ */
        /* left panel : msg Feed    */
        /* ************************ */

        JPanel msgFeedPanel = new JPanel();
        msgFeedPanel.setBackground(Color.BLUE);
        msgFeedPanel.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
        ArrayList recentContact = new ArrayList(this.bavardLogged.getRecentDiscussion().keySet()); // recentContact is a list of contact we had a discussion with
        System.out.println(recentContact);
        msgFeedPanel.setLayout(new FlowLayout()); // we could have used a GridLayout but component wouldn't be resizable
        int i=0;
        ArrayList<JPanelClickable> recentDiscussionList = new ArrayList<JPanelClickable>();
        for (PapotageListener pl: this.bavardLogged.getRecentDiscussion().keySet()) { // pl is a Bavard
            JPanelClickable recentDiscussionPanel = new JPanelClickable(i); // it's the container of the login and the last message sent to this bavard
            recentDiscussionPanel.setPreferredSize(new Dimension(this.getWidth() / 4, this.getHeight() / 10));
            recentDiscussionPanel.setLayout(new GridLayout(2, 1));
            // the login of the current bavard
            JLabel recentContactLabel = new JLabel(pl.getLogin()); // the login of the current bavard (contact)
            recentDiscussionPanel.setAddressee(pl);

            // the body of the last message sent to this current bavard
            ArrayList<PapotageEvent> listOfPapotageEventWithPl = this.bavardLogged.getRecentDiscussion().get(pl);
            JLabel lastMessageLabel = new JLabel(listOfPapotageEventWithPl.get(listOfPapotageEventWithPl.size()-1).getMessages().getBody());

            recentDiscussionPanel.add(recentContactLabel);
            recentDiscussionPanel.add(lastMessageLabel);

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
                    this.displayDiscussionMessage(); // we display the whole discussion
                }

                public void displayDiscussionMessage() {
                    /*Area where the user can see previous message sent*/
                    messageViewPanel.removeAll();
                    messageViewPanel.revalidate();
                    messageViewPanel.repaint();

                    //messageViewPanel = new JPanel();
                    messageViewPanel.setBackground(Color.YELLOW);
                    messageViewPanel.setPreferredSize(new Dimension(frame.getWidth()*3/4, frame.getHeight()*3/4));
                    messageViewPanel.setLayout(new FlowLayout());
                    for (JPanelClickable recentDiscussionP :recentDiscussionList ){
                        System.out.println("ici");
                        if (recentDiscussionP.isClicked()){ // we look for the discussion selected
                            System.out.println("recentDiscussionP" + recentDiscussionP);
                            PapotageListener addressee = recentDiscussionP.getAddressee();
                            System.out.println("addressee : " + addressee.getLogin());
                            ArrayList<PapotageEvent> discussionBavardAdressee = bavardLogged.getRecentDiscussion().get(addressee); //messages send by the bavard
                            ArrayList<PapotageEvent> discussionAdresseeBavard = bavardLogged.getRecentDiscussion().get(addressee); //messages send by the addressee
                            for(PapotageEvent pe : discussionAdresseeBavard){
                                System.out.println("message :" + pe.getMessages().getBody());
                                JPanel oneMessagePanel = new JPanel();
                                oneMessagePanel.setLayout(new BorderLayout());

                                JTextPane messageTextPane = new JTextPane();
                                messageTextPane.setText(pe.getMessages().getBody()); //à quoi sert le sujet du message
                                Dimension d = messageTextPane.getPreferredSize(); // we do that to only modify one dimension
                                d.width = frame.getWidth()*2/10;
                                messageTextPane.setPreferredSize(d);
                                oneMessagePanel.add(messageTextPane, BorderLayout.WEST);

                                JPanel blankPanel = new JPanel();
                                blankPanel.setBackground(Color.BLUE);
                                blankPanel.setPreferredSize(new Dimension(frame.getWidth()/2, 10));
                                //blankPanel.setPreferredSize(new Dimension(this.getWidth()/2, messageTextPane.getHeight()));
                                oneMessagePanel.add(blankPanel, BorderLayout.CENTER);

                                messageViewPanel.add(oneMessagePanel);
                            }
                        }
                        break;
                    }
                    messagingPanel.add(messageViewPanel, BorderLayout.NORTH);


                }

            });
            recentDiscussionList.add(recentDiscussionPanel);

            msgFeedPanel.add(recentDiscussionPanel);
            i++;
        }
        this.add(msgFeedPanel,BorderLayout.WEST);

        // Il faudra fixer la taille des messages dans le msgFeedPanel




        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

//    public void displayDiscussionMessage(){
//        for (JPanelClickable recentDiscussionP :recentDiscussionList ){
//            System.out.println("ici");
//            if (recentDiscussionP.isClicked()){ // we look for the discussion selected
//                System.out.println("recentDiscussionP" + recentDiscussionP);
//                PapotageListener addressee = recentDiscussionP.getAddressee();
//                ArrayList<PapotageEvent> discussionBavardAdressee = bavardLogged.getRecentDiscussion().get(addressee); //messages send by the bavard
//                ArrayList<PapotageEvent> discussionAdresseeBavard = bavardLogged.getRecentDiscussion().get(addressee); //messages send by the addressee
//                for(PapotageEvent pe : discussionAdresseeBavard){
//                    JPanel oneMessagePanel = new JPanel();
//                    oneMessagePanel.setLayout(new BorderLayout());
//
//                    JTextPane messageTextPane = new JTextPane();
//                    messageTextPane.setText(pe.getMessages().getBody()); //à quoi sert le sujet du message
//                    Dimension d = messageTextPane.getPreferredSize(); // we do that to only modify one dimension
//                    d.width = this.getWidth()*2/10;
//                    messageTextPane.setPreferredSize(d);
//                    oneMessagePanel.add(messageTextPane, BorderLayout.WEST);
//
//                    JPanel blankPanel = new JPanel();
//                    blankPanel.setBackground(Color.BLUE);
//                    blankPanel.setPreferredSize(new Dimension(this.getWidth()/2, 10));
//                    //blankPanel.setPreferredSize(new Dimension(this.getWidth()/2, messageTextPane.getHeight()));
//                    oneMessagePanel.add(blankPanel, BorderLayout.CENTER);
//
//                    messageViewPanel.add(oneMessagePanel);
//                }
//            }
//            break;
//        }
//    }
}
