package com.NoahRz; //ok

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class BavardFrame1 extends JFrame implements ActionListener, KeyListener {
    /**
     * Jrame where the Bavard can send, receive message, see others connected bavard and ask to listen other barvard
     * */

    private Bavard bavardLogged;
    private JPanel messagingPanel;
    private String bodyMessage;
    private String subjectMessage;
    private JPanel myMessageViewPanel;
    private Concierge concierge;
    private JPanel bavardConnectedListSubPanel;

    public BavardFrame1(PapotageListener papotageListenerLogged, Concierge concierge) { //je pense que c'est inutile de garder papotageListener, plutot mettre bavard
        this.bavardLogged = (Bavard)papotageListenerLogged;
        this.bavardLogged.setFrame(this);
        this.concierge=concierge;
        this.bavardLogged.alerteIsConnected();

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

        /* ************************************************************************************* */
        /* ************************** messaging panel ****************************************** */
        /* Area where we display messages we received and send  and  area Where we send messages */
        /* ************************************************************************************* */

        this.messagingPanel = new JPanel();
        messagingPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new BorderLayout());


        /*-- Area where we can see messages we received and messages we sent --*/
        myMessageViewPanel = new JPanel();
        myMessageViewPanel.setBackground(Color.YELLOW);
        myMessageViewPanel.setLayout(new BoxLayout(myMessageViewPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(myMessageViewPanel);
        messagingPanel.add(scrollPane, BorderLayout.CENTER);

        /*-- Area where the user can write messages and send them --*/
        JPanel messageFieldPanel = new JPanel(); //panel which contains a panel (which contains a textField and text area) and a button
        messageFieldPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/5));
        messageFieldPanel.setLayout(new BorderLayout());

        JTextField messageSubjectTextField = new JTextField("Write the message subject ...");
        Dimension d = messageSubjectTextField.getPreferredSize(); // we do that to only modify one dimension
        d.width = this.getWidth() * 6 / 10;
        messageSubjectTextField.setPreferredSize(d);
        messageSubjectTextField.addKeyListener(this);


        /*String[] themeArray = new String[this.bavardLogged.getTheme().size()]; //convert ArrayList to array
        themeArray = this.bavardLogged.getTheme().toArray(themeArray);

        JComboBox themeComboBox=new JComboBox(themeArray); // the bavard can only send message with theme he likes
        Dimension d1 = themeComboBox.getPreferredSize(); // we do that to only modify one dimension
        d1.width = this.getWidth() * 6 / 10;
        themeComboBox.setPreferredSize(d);
        themeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bruh");
                themeComboBox.revalidate();
                themeComboBox.repaint();
            }
        });*/

        JButton themeButton = new JButton("add theme");
        themeButton.setActionCommand("add theme");
        themeButton.addActionListener(this);


        JTextArea messagingTextArea = new JTextArea("Write the message body ... ", 10, 10); //area where the user can write message
        messagingTextArea.addKeyListener(this);

        JScrollPane messagingScrollPane = new JScrollPane(messagingTextArea); //make messagingTextArea scrollable so that it's easier to write long message


        JButton sendMessageButton = new JButton("send");
        sendMessageButton.setActionCommand("send");
        sendMessageButton.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/15));
        sendMessageButton.addActionListener(this);
        messageFieldPanel.add(sendMessageButton, BorderLayout.SOUTH);

        JPanel inputMessagePanel = new JPanel(); // panel which contains a textField (message subject field), comboBox (themes) and messaging scrollPane ( message body field)
        inputMessagePanel.setLayout(new BoxLayout(inputMessagePanel, BoxLayout.Y_AXIS));
        inputMessagePanel.add(messageSubjectTextField);
        inputMessagePanel.add(themeButton);
        inputMessagePanel.add(messagingScrollPane);

        messageFieldPanel.add(inputMessagePanel, BorderLayout.CENTER);
        messagingPanel.add(messageFieldPanel,BorderLayout.SOUTH);

        pane.add(messagingPanel, BorderLayout.CENTER);

        /* ************************************************************************************* */
        /* ************************** bavardConnectedListPanel :right side ********************** */
        /* ************ Area where the user can see a list of connected bavard ***************** */
        /* ************************************************************************************* */

        JPanel bavardConnectedListPanel = new JPanel(); //panel which contains a title label and a list of connected Bavard label (the list is in another panel)
        bavardConnectedListPanel.setLayout(new BoxLayout(bavardConnectedListPanel, BoxLayout.Y_AXIS)); // display bavard vertically

        JLabel connectedBavardTitleLabel = new JLabel("Connected bavards");
        Font fontForconnectedBavardTitleLabel =new Font(connectedBavardTitleLabel.getFont().getName(),Font.BOLD,connectedBavardTitleLabel.getFont().getSize()); //add Font to the label
        connectedBavardTitleLabel.setFont(fontForconnectedBavardTitleLabel);
        bavardConnectedListPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between bavards
        bavardConnectedListPanel.add(connectedBavardTitleLabel);

        bavardConnectedListSubPanel = new JPanel(); //panel which contains the list of bavard label
        bavardConnectedListSubPanel.setLayout(new BoxLayout(bavardConnectedListSubPanel, BoxLayout.Y_AXIS));

        LineBorder lineBorder = new LineBorder(new Color(128, 128, 128), 2, true);

        //we look through the bavard and add those connected
        for (Bavard bavard : this.concierge.getBavardsListenToBavardMap().keySet()){
            if(bavard.isConnected()){
                JLabel bavardLabel = new JLabel(bavard.getLogin(), SwingConstants.CENTER);
                bavardLabel.setBorder(lineBorder);
                bavardLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

                /*-- popupmenu --*/
                JPopupMenu popupmenu = new JPopupMenu("Edit"); // popupmenu when we click on a connected bavard
                JMenuItem dmMenuItem = new JMenuItem("DM");

                dmMenuItem.setActionCommand(bavard.getLogin());
                dmMenuItem.addActionListener(this);

                popupmenu.add(dmMenuItem);
                /*--------------*/
                bavardLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        popupmenu.show(bavardConnectedListPanel , bavardLabel.getX(), bavardLabel.getY());
                    }
                });

                bavardConnectedListSubPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between bavards
                bavardConnectedListSubPanel.add(bavardLabel);
            }
        }
        JScrollPane scrollPaneForbavardConnectedListSubPanel = new JScrollPane(bavardConnectedListSubPanel); //we make bavardConnectedListSubPanel scrollable
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
            if(this.subjectMessage == null && this.bodyMessage == null){ //we display a Dialog to warn the user there is no content in his message.
                JOptionPane.showMessageDialog(this,"There is no content in your message, please fill the field.", "Warning : no content",JOptionPane.WARNING_MESSAGE);
            }else {
                this.bavardLogged.sendMessages(this.subjectMessage, this.bodyMessage);
            }
        }
        if (e.getActionCommand().equals("Sign out")){ // if we click on "sign out"
            this.bavardLogged.alerteIsDisconnected();
            this.dispose();
        }
        if (e.getActionCommand().equals("adjust listening")){ //if we click on "adjust listening"
            new BavardAdjustItsListeningFrame(this.bavardLogged, this.concierge);
        }

        if (e.getActionCommand().equals("adjust theme")){ //if we click on "adjust theme"
            new BavardAdjustItsThemeFrame(this.bavardLogged, this.concierge);
        }


        if(e.getSource() instanceof JMenuItem){ // we click on tht popupmenu
            String bavardLogin = e.getActionCommand();
            Bavard bavardAddressee = this.concierge.getBavard(bavardLogin);
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
         * we add the message we have juste received to the myMessageViewPanel
         * @param pe:PapotageEvent, event which handles the message
         * */

        JPanel oneMessagePanel = new JPanel(); // panel which contains the messageContentPanel
        oneMessagePanel.setLayout(new BorderLayout());

        JPanel messageContentPanel = new JPanel(); // panel which gathers the sender, the message subject and the body subject (small part)
        messageContentPanel.setBackground(new Color(242, 242, 242));
        messageContentPanel.setLayout(new BorderLayout());

        JLabel messageBodyLabel = new JLabel();
        LineBorder lineBorder = new LineBorder(new Color(128, 128, 128), 2, true);

        String usernameSender;

        String messageContentPosition;
        if (pe instanceof OnlineOfflineBavardEvent){
            messageBodyLabel.setText(pe.getMessageBody());
            messageBodyLabel.setForeground(new Color(255, 128, 0));
            Font newLabelFont=new Font(messageBodyLabel.getFont().getName(),Font.ITALIC,messageBodyLabel.getFont().getSize());
            messageBodyLabel.setFont(newLabelFont);
            messageContentPosition = BorderLayout.WEST;
            this.refreshConnectedBavardList();

        }else if (pe instanceof ConciergeEvent) { //message from the concierge (request approved or rejected)
            messageBodyLabel.setText(pe.getMessageSubject() + ": " + pe.getMessageBody());
            messageBodyLabel.setForeground(Color.BLUE);
            Font newLabelFont=new Font(messageBodyLabel.getFont().getName(),Font.ITALIC,messageBodyLabel.getFont().getSize());
            messageBodyLabel.setFont(newLabelFont);
            messageContentPosition = BorderLayout.WEST;

        } else{ // it's a simple message
            if (pe.getSource() == bavardLogged) { // message we sent
                usernameSender = "You";
                messageContentPosition = BorderLayout.EAST;
            } else { // message we received
                Bavard userSender = (Bavard) pe.getSource();
                usernameSender = userSender.getLogin();
                messageContentPosition = BorderLayout.WEST;
            }
            JLabel senderLabel = new JLabel("From: " + usernameSender);
            JLabel subjectLabel = new JLabel("Subject: "+pe.getMessageSubject());

            messageContentPanel.add(senderLabel, BorderLayout.NORTH);
            messageContentPanel.add(subjectLabel, BorderLayout.CENTER);
            messageBodyLabel.setText("Body :" +pe.getMessageBody());
        }

        messageContentPanel.add(messageBodyLabel, BorderLayout.SOUTH);
        Dimension d = messageContentPanel.getPreferredSize();
        d.width=this.getWidth()/3;
        messageContentPanel.setPreferredSize(d);

        messageContentPanel.setBorder(lineBorder);
        messageContentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // if we click on the message, we open a new frame to show the whole message
                super.mousePressed(e);
                messageContentPanel.setBackground(new Color(128, 128, 128)); //we change the backgournd color of the panel when we press and release it
                new MessageFrame(pe);
            }

             @Override
             public void mouseReleased(MouseEvent e) {
                 super.mouseReleased(e);
                 messageContentPanel.setBackground(new Color(242, 242, 242));
             }
         }
        );

        oneMessagePanel.add(messageContentPanel, messageContentPosition); //messages we receive are on the left

        oneMessagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, messageContentPanel.getPreferredSize().height+5));
        oneMessagePanel.setBackground(Color.YELLOW);

        this.myMessageViewPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between messages
        this.myMessageViewPanel.add(oneMessagePanel);
        this.myMessageViewPanel.revalidate();
        this.myMessageViewPanel.repaint();
    }

    public void refreshConnectedBavardList(){ // I did this way because it guarantees a good synchronization
        /**
         * Refresh the connected bavard list
         * */
        this.bavardConnectedListSubPanel.removeAll();
        this.bavardConnectedListSubPanel.revalidate();
        this.bavardConnectedListSubPanel.repaint();

        LineBorder lineBorder = new LineBorder(new Color(128, 128, 128), 2, true);

        //we look through the bavard and add those connected
        for (Bavard bavard:this.concierge.getBavardsListenToBavardMap().keySet()){
            if(bavard.isConnected()) {
                JLabel bavardLabel = new JLabel(bavard.getLogin(), SwingConstants.CENTER);
                bavardLabel.setBorder(lineBorder);
                bavardLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

                /*-- popupmenu --*/
                JPopupMenu popupmenu = new JPopupMenu("Edit"); // popupmenu when we click on a connected bavard
                JMenuItem dmMenuItem = new JMenuItem("DM");

                dmMenuItem.setActionCommand(bavard.getLogin());
                dmMenuItem.addActionListener(this);

                popupmenu.add(dmMenuItem);
                /*--------------*/

                bavardLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        popupmenu.show(bavardConnectedListSubPanel, bavardLabel.getX(), bavardLabel.getY());
                    }
                });

                this.bavardConnectedListSubPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between bavards
                this.bavardConnectedListSubPanel.add(bavardLabel);
            }
        }
    }
}
