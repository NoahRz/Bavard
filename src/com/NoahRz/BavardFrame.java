package com.NoahRz; //ok1

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BavardFrame extends JFrame implements ActionListener, KeyListener {
    /**
     * Jrame where the Bavard can send, receive message, see others connected bavard and ask to listen other barvard
     * */

    private Bavard bavardLogged;
    private JPanel messagingPanel;
    private String bodyMessage;
    private String subjectMessage;
    private JPanel myMessageViewPanel; // panel where we can see messages we received and messages we sent
    private Concierge concierge;
    private JPanel bavardConnectedListContentPanel;
    private ArrayList<JCheckBox> themeChexboxes = new ArrayList<JCheckBox>(); //ArrayList of theme checkbox
    private JPanel selectThemePanel;

    public BavardFrame(Bavard bavardLogged, Concierge concierge) { //je pense que c'est inutile de garder papotageListener, plutot mettre bavard
        this.bavardLogged = bavardLogged;
        this.bavardLogged.setFrame(this);
        this.concierge=concierge;
        this.bavardLogged.warnIsConnected();

        this.setTitle("Bavard page");
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(600,600));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        /*-- Menu --*/
        JMenuBar menubar= new JMenuBar();
        JMenu bavardMenu=new JMenu(bavardLogged.getLogin());
        JMenu optionMenu = new JMenu("Option");
        JMenuItem adjustlisteningMenuItem = new JMenuItem("adjust listening");
        JMenuItem adjustThemeMenuItem = new JMenuItem("adjust theme");
        JMenuItem signOutMenuItem = new JMenuItem("Sign out");

        adjustlisteningMenuItem.setActionCommand("adjust listening");
        adjustlisteningMenuItem.addActionListener(this);

        adjustThemeMenuItem.setActionCommand("adjust theme");
        adjustThemeMenuItem.addActionListener(this);

        signOutMenuItem.setActionCommand("Sign out");
        signOutMenuItem.addActionListener(this);

        optionMenu.add(adjustlisteningMenuItem);
        optionMenu.add(adjustThemeMenuItem);
        optionMenu.add(signOutMenuItem);

        menubar.add(bavardMenu);
        menubar.add(optionMenu);
        this.setJMenuBar(menubar);


        /**************************************************************************************************************
         messaging panel: left side
         Area where we display messages we received and send  and  area Where we send messages
         **************************************************************************************************************/

        this.messagingPanel = new JPanel();
        messagingPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new BorderLayout());


        /*--- Area where we can see messages we received and messages we sent ---*/
        myMessageViewPanel = new JPanel();
        myMessageViewPanel.setBackground(Color.YELLOW);
        myMessageViewPanel.setLayout(new BoxLayout(myMessageViewPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(myMessageViewPanel);
        messagingPanel.add(scrollPane, BorderLayout.CENTER);

        /*--- Area where the user can write messages and send them ---*/
        JPanel messageFieldPanel = new JPanel(); //panel which contains a panel (which contains a textField, another panel and a text area) and a button
        messageFieldPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/4));
        messageFieldPanel.setLayout(new BorderLayout());

        JTextField messageSubjectTextField = new JTextField("Write the message subject ...");
        messageSubjectTextField.addKeyListener(this);


        /*--- Theme panel ---*/
        selectThemePanel = new JPanel();
        selectThemePanel.setLayout(new BoxLayout(selectThemePanel,BoxLayout.X_AXIS));

        JLabel selectThemeTitlePanel = new JLabel(" Select message theme :");
        selectThemePanel.add(selectThemeTitlePanel);

        //look through theme this bavard likes, create theme checkbox
            for(String theme : this.bavardLogged.getTheme()){
            JCheckBox themeCheckBox = new JCheckBox(theme);
            selectThemePanel.add(themeCheckBox);
            themeChexboxes.add(themeCheckBox); // add it to the arrayList for the listener
        }

        JScrollPane selectThemeScrollPane = new JScrollPane(selectThemePanel); // we make selectThemePanel scrollable
        selectThemeScrollPane.setMinimumSize(new Dimension(this.getWidth(), 50));
        /*--------------------*/


        JTextArea messagingTextArea = new JTextArea("Write the message body ... ", 5, 10); //area where the user can write message
        messagingTextArea.addKeyListener(this);

        JScrollPane messagingScrollPane = new JScrollPane(messagingTextArea); //make messagingTextArea scrollable so that it's easier to write long message


        JButton sendMessageButton = new JButton("send");
        sendMessageButton.setActionCommand("send");
        sendMessageButton.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/15));
        sendMessageButton.addActionListener(this);
        messageFieldPanel.add(sendMessageButton, BorderLayout.SOUTH);

        JPanel inputMessagePanel = new JPanel(); // panel which contains a textField (message subject field), checkbox (themes) and messaging scrollPane ( message body field)
        inputMessagePanel.setLayout(new BoxLayout(inputMessagePanel, BoxLayout.Y_AXIS));
        inputMessagePanel.add(messageSubjectTextField);
        inputMessagePanel.add(selectThemeScrollPane);
        inputMessagePanel.add(messagingScrollPane);

        messageFieldPanel.add(inputMessagePanel, BorderLayout.CENTER);
        messagingPanel.add(messageFieldPanel,BorderLayout.SOUTH);

        pane.add(messagingPanel, BorderLayout.CENTER);


        /**************************************************************************************************************
         bavardConnectedListPanel :right side
         Area where the user can see a list of connected bavard
         **************************************************************************************************************/

        JPanel bavardConnectedListPanel = new JPanel(); //panel which contains a title label and a list of connected Bavard label (the list is in another panel)
        bavardConnectedListPanel.setLayout(new BoxLayout(bavardConnectedListPanel, BoxLayout.Y_AXIS)); // display bavard vertically

        JLabel connectedBavardTitleLabel = new JLabel("Connected bavards");
        Font fontForconnectedBavardTitleLabel =new Font(connectedBavardTitleLabel.getFont().getName(),Font.BOLD,connectedBavardTitleLabel.getFont().getSize()); //add Font to the label
        connectedBavardTitleLabel.setFont(fontForconnectedBavardTitleLabel);
        bavardConnectedListPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between bavards
        bavardConnectedListPanel.add(connectedBavardTitleLabel);

        bavardConnectedListContentPanel = new JPanel(); //panel which contains the list of bavard label
        bavardConnectedListContentPanel.setLayout(new BoxLayout(bavardConnectedListContentPanel, BoxLayout.Y_AXIS));

        this.refreshConnectedBavardList(); //we display the list of bavard connected

        JScrollPane scrollPaneForbavardConnectedListSubPanel = new JScrollPane(bavardConnectedListContentPanel); //we make bavardConnectedListSubPanel scrollable
        bavardConnectedListPanel.add(scrollPaneForbavardConnectedListSubPanel);

        pane.add(bavardConnectedListPanel, BorderLayout.EAST);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the frame without closing the program
        this.setVisible(true);
    }

    /******************************************************************
     * listener method
     ******************************************************************/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("send")){ //if we click on the send button
            if((this.subjectMessage == null && this.bodyMessage == null) || !this.thereIsAtLeastOneThemeSelected()){ //we display a Dialog to warn the user there is no content in his message or there is no theme for the message
                JOptionPane.showMessageDialog(this,"There is no content in your message or there is no theme selected, please fill the field.", "Warning : no content",JOptionPane.WARNING_MESSAGE);
            }else {
                ArrayList<String> themeSelectedArrayList = new ArrayList<String>(); //Array which contains the themeSelected (string) for the message

                for (JCheckBox themeChexbox : this.themeChexboxes){ // we add all theme selected
                    if (themeChexbox.isSelected()){
                        themeSelectedArrayList.add(themeChexbox.getActionCommand());
                    }
                }

                this.bavardLogged.sendMessages(themeSelectedArrayList,this.subjectMessage, this.bodyMessage);
            }
        }
        if (e.getActionCommand().equals("Sign out")){ // if we click on "sign out"
            this.bavardLogged.warnIsDisconnected();
            this.dispose();
        }
        if (e.getActionCommand().equals("adjust listening")){ //if we click on "adjust listening"
            new BavardAdjustHisListeningFrame(this.bavardLogged, this.concierge); //open a new BavardAdjustHisListeningFrame
        }

        if (e.getActionCommand().equals("adjust theme")){ //if we click on "adjust theme"
            new BavardAdjustHisThemeFrame(this.bavardLogged, this.concierge); //open a new BavardAdjustHisThemeFrame
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
        //we when finish typing on the JTextArea (message Body) or JTextField(message subject)
        if (e.getSource() instanceof JTextArea) {
            JTextArea ta = (JTextArea) e.getSource();
            this.bodyMessage = ta.getText();
        }
        if(e.getSource() instanceof JTextField){
            JTextField tf = (JTextField) e.getSource();
            this.subjectMessage = tf.getText();
        }
    }

    public void receiveMessages(PapotageEvent pe){
        /**
         * we display the message we have just received on the myMessageViewPanel
         * @param pe:PapotageEvent, event which handles the message
         * */

        JPanel oneMessagePanel = new JPanel(); // panel which contains the messageContentPanel
        oneMessagePanel.setLayout(new BorderLayout());

        JPanel messageContentPanel = new JPanel(); // panel which gathers the sender name, the message subject and the body subject (small part)
        messageContentPanel.setBackground(new Color(242, 242, 242));
        messageContentPanel.setLayout(new BoxLayout(messageContentPanel, BoxLayout.Y_AXIS));


        JLabel messageBodyLabel = new JLabel();
        LineBorder lineBorder = new LineBorder(new Color(128, 128, 128), 2, true);

        String usernameSender;

        String messageContentPosition;
        //For each type of message, we display it differently (different format)
        if (pe instanceof OnlineOfflineBavardEvent){ //if we receive a OnlineOfflineBavardEvent : message that someone has just logged in or logged out
            messageBodyLabel.setText(pe.getMessageBody());
            messageBodyLabel.setForeground(new Color(255, 128, 0));
            Font newLabelFont=new Font(messageBodyLabel.getFont().getName(),Font.ITALIC,messageBodyLabel.getFont().getSize());
            messageBodyLabel.setFont(newLabelFont);
            messageContentPosition = BorderLayout.WEST;
            this.refreshConnectedBavardList();

        }else if (pe instanceof ConciergeEvent) { //if we message from the concierge (request approved or rejected)
            messageBodyLabel.setText(pe.getMessageSubject() + ": " + pe.getMessageBody());
            messageBodyLabel.setForeground(Color.BLUE);
            Font newLabelFont=new Font(messageBodyLabel.getFont().getName(),Font.ITALIC,messageBodyLabel.getFont().getSize());
            messageBodyLabel.setFont(newLabelFont);
            messageContentPosition = BorderLayout.WEST;

        } else{ // it's a simple message : message sent from a bavard
            if (pe.getSource() == bavardLogged) { // message we sent ( we send the message to ourself in order to display it on our frame
                usernameSender = "You";
                messageContentPosition = BorderLayout.EAST; //message will be displayed on the right
            } else { // message we received
                Bavard userSender = (Bavard) pe.getSource();
                usernameSender = userSender.getLogin();
                messageContentPosition = BorderLayout.WEST; //message will be displayed on the left
            }
            JLabel senderLabel = new JLabel("From: " + usernameSender);

            JLabel themesLabel = new JLabel();
            String themesContentLabel = "Theme : "; //the text of themesLabel
            for (String theme : pe.getMessageThemes()){ //we add to the themes content Label all the themes of the message
                themesContentLabel += theme + " ";
            }

            themesLabel.setText(themesContentLabel);


            JLabel subjectLabel = new JLabel("Subject: "+pe.getMessageSubject());


            messageContentPanel.add(senderLabel);
            messageContentPanel.add(themesLabel);
            messageContentPanel.add(subjectLabel);
            messageBodyLabel.setText("Body :" +pe.getMessageBody());
        }

        messageContentPanel.add(messageBodyLabel);

        Dimension d = messageContentPanel.getPreferredSize();
        d.width=this.getWidth()/3; //to only modify one attribute
        messageContentPanel.setPreferredSize(d);

        //Add listener to the message
        messageContentPanel.setBorder(lineBorder);
        messageContentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // if we click on the message, we open a new frame to show the whole message
                super.mousePressed(e);
                messageContentPanel.setBackground(new Color(128, 128, 128)); //we change the background color of the panel when we press and release it
                new MessageFrame(pe);
            }

             @Override
             public void mouseReleased(MouseEvent e) {
                 super.mouseReleased(e);
                 messageContentPanel.setBackground(new Color(242, 242, 242));
             }
         }
        );
        //Add the message to the panel
        oneMessagePanel.add(messageContentPanel, messageContentPosition);

        oneMessagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, messageContentPanel.getPreferredSize().height+5));
        oneMessagePanel.setBackground(Color.YELLOW);

        this.myMessageViewPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between messages
        this.myMessageViewPanel.add(oneMessagePanel);
        this.myMessageViewPanel.revalidate();
        this.myMessageViewPanel.repaint();
    }

    public void refreshConnectedBavardList(){ // I did this way because it guarantees a good synchronization
        /**
         * Refresh the connected bavard list in bavardConnectedListContentPanel
         * */
        this.bavardConnectedListContentPanel.removeAll();
        this.bavardConnectedListContentPanel.revalidate();
        this.bavardConnectedListContentPanel.repaint();

        LineBorder lineBorder = new LineBorder(new Color(128, 128, 128), 2, true);

        //we look through the bavard and add those connected (create bavard labels and add them to the panel)
        for (Bavard bavard:this.concierge.getBavardsListenToBavardMap().keySet()){
            if(bavard.isConnected()) {
                JLabel bavardLabel = new JLabel(bavard.getLogin(), SwingConstants.CENTER);
                bavardLabel.setBorder(lineBorder);
                bavardLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

                this.bavardConnectedListContentPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between bavards
                this.bavardConnectedListContentPanel.add(bavardLabel);
            }
        }
    }

    public void refreshThemes(){
        /**
         * Refresh the themes list
         * */

        this.selectThemePanel.removeAll();
        this.selectThemePanel.revalidate();
        this.selectThemePanel.repaint();

        JLabel selectThemeTitlePanel = new JLabel(" Select message theme :");
        selectThemePanel.add(selectThemeTitlePanel);

        //we look through themes the bavard likes and add them to the panel
        for(String theme : this.bavardLogged.getTheme()){
            JCheckBox themeCheckBox = new JCheckBox(theme);
            selectThemePanel.add(themeCheckBox);
            themeChexboxes.add(themeCheckBox); // add it to the arrayList for the listener
        }
    }

    public Boolean thereIsAtLeastOneThemeSelected(){
        /**
         * return true if there is at least one theme selected, false otherwise
         */

        for(JCheckBox themeCheckBox : this.themeChexboxes){
            if(themeCheckBox.isSelected()){
                return true; // return true once a theme checkBox selected is found
            }
        }
        return false;
    }

}
