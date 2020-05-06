package com.NoahRz;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.Set;

public class EPapotageFrame extends JFrame implements ActionListener, KeyListener {
    /**
     * This class is the frame where the Bavard can send, receive message and the concierge can create Bavard and send and receive message*/
    private Bavard bavardLogged;
    private Concierge concierge;
    private JPanel messagingPanel; /* right panel :  where we display messages of the discussion selected and the area to send messages */
    private JPanel messageViewPanel; /* right panel :  where we display messages of the discussion selected (inside messagingPanel) */
    private String bodyMessage;
    private String subjectMessage;

    public EPapotageFrame(PapotageListener papotageListenerLogged, Concierge concierge) {
        this.bavardLogged = (Bavard)papotageListenerLogged;
        this.concierge=concierge;
        this.setTitle("Bavard page");
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(600,600));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        EPapotageFrame frame = this;

        /* *********************************************************************************** */
        /* ************************** right panel :  messaging panel ************************* */
        /*  where we display messages of the discussion selected and the area to send messages */
        /* *********************************************************************************** */

        this.messagingPanel = new JPanel();
        messagingPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new BorderLayout());


        /*---- Area where the user can see previous message sent ----***/
        MessageViewPanel myMessageViewPanel = new MessageViewPanel(this, bavardLogged);
        bavardLogged.setMessageViewPanel(myMessageViewPanel);
        System.out.println(bavardLogged.getMessageViewPanel());

        JScrollPane scrollPane = new JScrollPane(myMessageViewPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 30, 300, 50);

        messagingPanel.add(scrollPane, BorderLayout.NORTH);

        /*---- area where the user can write messages and send them ----*/
        JPanel messageFieldPanel = new JPanel();
        messageFieldPanel.setBackground(Color.GREEN);
        messageFieldPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/4));
        messageFieldPanel.setLayout(new BorderLayout());

        JTextField messagesubjectTextField = new JTextField("tapez votre sujet ...");
        Dimension d = messagesubjectTextField.getPreferredSize(); // we do that to only modify one dimension
        d.width = this.getWidth() * 6 / 10;
        messagesubjectTextField.setPreferredSize(d);
        messagesubjectTextField.addKeyListener(this);

        JTextArea messagingTextArea = new JTextArea("tapez votre message... "); //area where the user can write message
        messagingTextArea.setPreferredSize(new Dimension(this.getWidth()*6/10 ,this.getHeight()/5));
        messagingTextArea.addKeyListener(this);

        JButton sendMessageButton = new JButton("Envoyer");
        sendMessageButton.setPreferredSize(new Dimension(this.getWidth()/10, this.getHeight()/15));
        sendMessageButton.addActionListener(this);

        //messageFieldPanel.add(messagesubjectTextField, BorderLayout.NORTH);
        //messageFieldPanel.add(messagingTextArea, BorderLayout.CENTER);
        messageFieldPanel.add(sendMessageButton, BorderLayout.EAST);

        JPanel inputMessagePanel = new JPanel();
        inputMessagePanel.setLayout(new FlowLayout());
        inputMessagePanel.add(messagesubjectTextField);
        inputMessagePanel.add(messagingTextArea);

        messageFieldPanel.add(inputMessagePanel, BorderLayout.CENTER);
        messagingPanel.add(messageFieldPanel);

        this.add(messagingPanel, BorderLayout.CENTER);

        /* ********************************************************************************************************** */
        /* ************************************ left panel : msg Feed *********************************************** */
        /* we display all the recent discussion the bavard have had
        /* ********************************************************************************************************** */

        /*JPanel msgFeedPanel = new JPanel();
        msgFeedPanel.setBackground(Color.BLUE);
        msgFeedPanel.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));
        ArrayList recentContact = new ArrayList(this.bavardLogged.getRecentDiscussion().keySet());
        // recentContact is a list of contact we have had a discussion with
        System.out.println(recentContact);
        msgFeedPanel.setLayout(new FlowLayout()); // we could have used a GridLayout but component wouldn't be resizable

        ArrayList<JPanelClickable> recentDiscussionList = new ArrayList<JPanelClickable>();
        for (PapotageListener pl: this.bavardLogged.getRecentDiscussion().keySet()) { // pl is a Bavard
            JPanelClickable recentDiscussionPanel = new JPanelClickable(); // it's the container of the login and the last message sent to this bavard
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
                    *//*Area where the user can see previous message sent*//*
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
                            break;
                        }

                    }
                    messagingPanel.add(messageViewPanel, BorderLayout.NORTH);


                }

            });
            recentDiscussionList.add(recentDiscussionPanel);

            msgFeedPanel.add(recentDiscussionPanel);
        }
        this.add(msgFeedPanel,BorderLayout.WEST);*/

        // Il faudra fixer la taille des messages dans le msgFeedPanel




        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the frame without closing the program
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton){
            this.bavardLogged.sendMessages(new Message(this.subjectMessage, this.bodyMessage));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() instanceof JTextArea) { // we do this way because JPasswordField is also an instance of JTextField
            JTextArea ta = (JTextArea) e.getSource();
            this.bodyMessage = ta.getText();
        }
        if(e.getSource() instanceof JTextField){
            JTextField tf = (JTextField) e.getSource();
            this.subjectMessage = tf.getText();
        }
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
