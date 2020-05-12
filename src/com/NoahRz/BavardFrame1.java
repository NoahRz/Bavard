package com.NoahRz; //ok

import javax.swing.*;
import javax.swing.border.Border;
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
    private JPopupMenu popupmenu;
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
        JMenuItem signOutMenuItem = new JMenuItem("Sign out");

        adjustlisteningMenuItem.setActionCommand("adjust listening");
        adjustlisteningMenuItem.addActionListener(this);

        signOutMenuItem.setActionCommand("Sign out");
        signOutMenuItem.addActionListener(this);

        optionMenu.add(adjustlisteningMenuItem); optionMenu.add(signOutMenuItem);
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
        JPanel messageFieldPanel = new JPanel();
        //messageFieldPanel.setBackground(Color.GREEN);
        messageFieldPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/4));
        messageFieldPanel.setLayout(new BorderLayout());

        JTextField messageSubjectTextField = new JTextField("Write the message subject ...");
        Dimension d = messageSubjectTextField.getPreferredSize(); // we do that to only modify one dimension
        d.width = this.getWidth() * 6 / 10;
        messageSubjectTextField.setPreferredSize(d);
        messageSubjectTextField.addKeyListener(this);

        JTextArea messagingTextArea = new JTextArea("Write the message body ... "); //area where the user can write message
        messagingTextArea.setPreferredSize(new Dimension(this.getWidth()*6/10 ,this.getHeight()/5));
        messagingTextArea.addKeyListener(this);


        JButton sendMessageButton = new JButton("send");
        sendMessageButton.setActionCommand("send");
        sendMessageButton.setPreferredSize(new Dimension(this.getWidth()/10, this.getHeight()/15));
        sendMessageButton.addActionListener(this);
        messageFieldPanel.add(sendMessageButton, BorderLayout.EAST);

        JPanel inputMessagePanel = new JPanel();
        //inputMessagePanel.setLayout(new FlowLayout());
        inputMessagePanel.setLayout(new BoxLayout(inputMessagePanel, BoxLayout.Y_AXIS));
        inputMessagePanel.add(messageSubjectTextField);
        inputMessagePanel.add(messagingTextArea);

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

        /*-- popupmenu --*/
        popupmenu = new JPopupMenu("Edit"); // popupmenu when we click on a connected bavard
        JMenuItem listen_toMenuItem = new JMenuItem("Listen to");
        JMenuItem un_listen_toMenuItem = new JMenuItem("un Listen to");
        JMenuItem dmMenuItem = new JMenuItem("DM");
        popupmenu.add(listen_toMenuItem);
        popupmenu.add(un_listen_toMenuItem);
        popupmenu.add(dmMenuItem);
        /*--------------*/

        bavardConnectedListSubPanel = new JPanel(); //panel which contains the list of bavard label
        bavardConnectedListSubPanel.setLayout(new BoxLayout(bavardConnectedListSubPanel, BoxLayout.Y_AXIS));

        LineBorder lineBorder = new LineBorder(new Color(128, 128, 128), 2, true);

        //we look through the bavard and add those connected
        for (Bavard bavard : this.concierge.getBavardsListenToBavardMap().keySet()){
            if(bavard.isConnected()){
                JLabel bavardLabel = new JLabel(bavard.getLogin(), SwingConstants.CENTER);
                bavardLabel.setBorder(lineBorder);
                bavardLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
                bavardLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        popupmenu.show(bavardConnectedListPanel , e.getX(), e.getY());
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("send")){ //if we click on the send button
            if(this.subjectMessage == null && this.bodyMessage == null){ //we display a Dialog to warn the user there is no content in his message.
                JOptionPane.showConfirmDialog(this,"There is no content in your message, please fill the field.", "Warning : no content",JOptionPane.DEFAULT_OPTION);
            }else {
                this.bavardLogged.sendMessages(new Message(this.subjectMessage, this.bodyMessage));
            }
        }
        if (e.getActionCommand().equals("Sign out")){ // if we click on "sign out"
            this.bavardLogged.alerteIsDisconnected();
            this.dispose();
        }
        if (e.getActionCommand().equals("adjust listening")){ //if we click on "adjust listening"
            new BavardAdjustItsListeningFrame(this.bavardLogged, this.concierge);
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
            messageBodyLabel.setText(pe.getMessages().getBody());
            messageBodyLabel.setForeground(new Color(255, 128, 0));
            Font newLabelFont=new Font(messageBodyLabel.getFont().getName(),Font.ITALIC,messageBodyLabel.getFont().getSize());
            messageBodyLabel.setFont(newLabelFont);
            messageContentPosition = BorderLayout.WEST;
            this.refreshConnectedBavardList();

        }else { // it's a simple message
            if (pe.getSource() == bavardLogged) { // message we sent
                usernameSender = "You";
                messageContentPosition = BorderLayout.EAST;
            } else { // message we received
                Bavard userSender = (Bavard) pe.getSource();
                usernameSender = userSender.getLogin();
                messageContentPosition = BorderLayout.WEST;
            }
            JLabel senderLabel = new JLabel("From: " + usernameSender);
            JLabel subjectLabel = new JLabel("Subject: "+pe.getMessages().getSubject());

            messageContentPanel.add(senderLabel, BorderLayout.NORTH);
            messageContentPanel.add(subjectLabel, BorderLayout.CENTER);
            messageBodyLabel.setText("Body :" +pe.getMessages().getBody());
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

                bavardLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        popupmenu.show(bavardConnectedListSubPanel, e.getX(), e.getY());
                    }
                });

                this.bavardConnectedListSubPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between bavards
                this.bavardConnectedListSubPanel.add(bavardLabel);
            }
        }
    }
}
