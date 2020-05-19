package com.NoahRz;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class DmFrame extends JFrame implements ActionListener, KeyListener {
    /**
     * Frame where the bavard can dm the bavard addressee
     * */

    private Bavard bavardLogged;
    private Bavard bavardAdressee;
    private JPanel messagingPanel;
    private JPanel myMessageViewPanel;
    private String messageBody;
    private String messageSubject;


    public DmFrame(Bavard bavardLogged, Bavard bavardAdressee){
        this.bavardLogged=bavardLogged;
        this.bavardAdressee=bavardAdressee;
        this.bavardLogged.setDmFrame(this);

        this.setTitle("Discussion with "+ bavardAdressee.getLogin());
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(500,500));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        /*-- Menu --*/
        JMenuBar menubar= new JMenuBar();
        JMenu bavardMenu=new JMenu(bavardLogged.getLogin());
        JMenuItem signOutMenuItem = new JMenuItem("Sign out");


        signOutMenuItem.setActionCommand("Sign out");
        signOutMenuItem.addActionListener(this);

        bavardMenu.add(signOutMenuItem);
        menubar.add(bavardMenu);
        this.setJMenuBar(menubar);
        /*----------*/


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


        JTextArea messagingTextArea = new JTextArea("Write the message body ... ", 10, 10); //area where the user can write message
        messagingTextArea.addKeyListener(this);

        JScrollPane messagingScrollPane = new JScrollPane(messagingTextArea); //make messagingTextArea scrollable so that it's easier to write long message


        JButton sendMessageButton = new JButton("send");
        sendMessageButton.setActionCommand("send");
        sendMessageButton.setPreferredSize(new Dimension(this.getWidth()/10, this.getHeight()/15));
        sendMessageButton.addActionListener(this);
        messageFieldPanel.add(sendMessageButton, BorderLayout.SOUTH);

        JPanel inputMessagePanel = new JPanel();
        inputMessagePanel.setLayout(new BoxLayout(inputMessagePanel, BoxLayout.Y_AXIS));
        inputMessagePanel.add(messageSubjectTextField);
        inputMessagePanel.add(messagingScrollPane);

        messageFieldPanel.add(inputMessagePanel, BorderLayout.CENTER);
        messagingPanel.add(messageFieldPanel,BorderLayout.SOUTH);

        pane.add(messagingPanel, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the frame without closing the program
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("send")){ //if we click on the send button
            if(this.messageSubject == null && this.messageBody == null){ //we display a Dialog to warn the user there is no content in his message.
                JOptionPane.showMessageDialog(this,"There is no content in your message, please fill the field.", "Warning : no content",JOptionPane.WARNING_MESSAGE);
            }else {
                this.bavardLogged.sendDM(this.messageSubject, this.messageBody, this.bavardAdressee);
            }
        }
        if (e.getActionCommand().equals("Sign out")){ // if we click on "sign out"
            this.bavardLogged.alerteIsDisconnected();
            this.dispose();
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
            this.messageBody = ta.getText();
        }
        if(e.getSource() instanceof JTextField){
            JTextField tf = (JTextField) e.getSource();
            this.messageSubject = tf.getText();
        }
    }

    public void receiveDM(PapotageEvent pe){
        /**
         * we add the DM we have juste received to the myMessageViewPanel
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

}
