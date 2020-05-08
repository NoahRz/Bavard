package com.NoahRz;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class BavardFrame1 extends JFrame implements ActionListener, KeyListener {
    /**
     * This class is the frame where the Bavard can send, receive message and the concierge can create Bavard and send and receive message*/
    private Bavard bavardLogged;
    private JPanel messagingPanel; /* right panel :  where we display messages of the discussion selected and the area to send messages */
    private String bodyMessage;
    private String subjectMessage;
    private JPanel myMessageViewPanel;

    public BavardFrame1(PapotageListener papotageListenerLogged) {
        this.bavardLogged = (Bavard)papotageListenerLogged;
        this.bavardLogged.setFrame(this);
        this.setTitle("Bavard page");
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(600,600));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        BavardFrame1 frame = this;

        /* *********************************************************************************** */
        /* ************************** right panel :  messaging panel ************************* */
        /*  where we display messages of the discussion selected and the area to send messages */
        /* *********************************************************************************** */

        this.messagingPanel = new JPanel();
        messagingPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()));
        messagingPanel.setLayout(new BorderLayout());


        /*---- Area where the bavard can see messages he received and messages he receives----***/
        myMessageViewPanel = new JPanel();
        myMessageViewPanel.setBackground(Color.YELLOW);
        //myMessageViewPanel.setPreferredSize(new Dimension(frame.getWidth()*3/4, frame.getHeight()*3/4));
        myMessageViewPanel.setLayout(new BoxLayout(myMessageViewPanel, BoxLayout.Y_AXIS));
        //myMessageViewPanel.setLayout(new FlowLayout());

        //JScrollPane scrollPane = new JScrollPane(myMessageViewPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scrollPane = new JScrollPane(myMessageViewPanel);

        messagingPanel.add(scrollPane, BorderLayout.CENTER);

        /*---- Area where the user can write messages and send them ----*/
        JPanel messageFieldPanel = new JPanel();
        messageFieldPanel.setBackground(Color.GREEN);
        messageFieldPanel.setPreferredSize(new Dimension(this.getWidth()*3/4, this.getHeight()/4));
        messageFieldPanel.setLayout(new BorderLayout());

        JTextField messagesubjectTextField = new JTextField("Write the message subject ...");
        Dimension d = messagesubjectTextField.getPreferredSize(); // we do that to only modify one dimension
        d.width = this.getWidth() * 6 / 10;
        messagesubjectTextField.setPreferredSize(d);
        messagesubjectTextField.addKeyListener(this);

        JTextArea messagingTextArea = new JTextArea("Write the message body ... "); //area where the user can write message
        messagingTextArea.setPreferredSize(new Dimension(this.getWidth()*6/10 ,this.getHeight()/5));
        messagingTextArea.addKeyListener(this);

        JButton sendMessageButton = new JButton("send");
        sendMessageButton.setActionCommand("send");
        sendMessageButton.setPreferredSize(new Dimension(this.getWidth()/10, this.getHeight()/15));
        sendMessageButton.addActionListener(this);
        messageFieldPanel.add(sendMessageButton, BorderLayout.EAST);

        JPanel inputMessagePanel = new JPanel();
        inputMessagePanel.setLayout(new FlowLayout());
        inputMessagePanel.add(messagesubjectTextField);
        inputMessagePanel.add(messagingTextArea);

        messageFieldPanel.add(inputMessagePanel, BorderLayout.CENTER);
        messagingPanel.add(messageFieldPanel,BorderLayout.SOUTH);

        pane.add(messagingPanel, BorderLayout.CENTER);
        /*---- Area where the user can disconnect and see a list of bavard to listen to  ----*/

        JButton disconnectButton = new JButton("Sign out");
        disconnectButton.setActionCommand("sign out");
        disconnectButton.addActionListener(this);

        pane.add(disconnectButton, BorderLayout.EAST);


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the frame without closing the program
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("send")){
            this.bavardLogged.sendMessages(new Message(this.subjectMessage, this.bodyMessage));
        }
        else{ // disconnect button
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
        if (e.getSource() instanceof JTextArea) { // we do this way because JPasswordField is also an instance of JTextField
            JTextArea ta = (JTextArea) e.getSource();
            this.bodyMessage = ta.getText();
        }
        if(e.getSource() instanceof JTextField){
            JTextField tf = (JTextField) e.getSource();
            this.subjectMessage = tf.getText();
        }
    }

    public void receiveMessages(PapotageEvent pe){
        JPanel oneMessagePanel = new JPanel();
        oneMessagePanel.setLayout(new BorderLayout());

        JPanel messageContentPanel = new JPanel();
        messageContentPanel.setLayout(new BorderLayout());

        JLabel messageBodyLabel = new JLabel();

        LineBorder line = new LineBorder(Color.blue, 2, true);

        String usernameSender;
        if (pe instanceof OnlineOfflineBavardEvent){
            messageBodyLabel.setText(pe.getMessages().getBody());
        }else {
            if (pe.getSource() == bavardLogged) {
                usernameSender = "You";
            } else {
                Bavard userSender = (Bavard) pe.getSource();
                usernameSender = userSender.getLogin();
            }
            JLabel senderLabel = new JLabel("From:" + usernameSender);
            JLabel subjectLabel = new JLabel("Subject: "+pe.getMessages().getSubject());

            messageContentPanel.add(senderLabel, BorderLayout.NORTH);
            messageContentPanel.add(subjectLabel, BorderLayout.CENTER);
            messageBodyLabel.setText("Body :" +pe.getMessages().getBody());
        }

        messageContentPanel.add(messageBodyLabel, BorderLayout.SOUTH);
        Dimension d = messageContentPanel.getPreferredSize();
        d.width=this.getWidth()/3;
        messageContentPanel.setPreferredSize(d);

        messageContentPanel.setBorder(line);


        messageContentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                new MessageFrame(pe);
            }
        });

        oneMessagePanel.add(messageContentPanel, BorderLayout.WEST); //messages we receive are on the left

        System.out.println(messageContentPanel.getPreferredSize().height);
        System.out.println(messageContentPanel.getHeight());
        oneMessagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, messageContentPanel.getPreferredSize().height+5));
        oneMessagePanel.setBackground(Color.YELLOW);

        this.myMessageViewPanel.add(Box.createRigidArea(new Dimension(0, 5))); //add space between messages
        this.myMessageViewPanel.add(oneMessagePanel);
        this.myMessageViewPanel.revalidate();
        this.myMessageViewPanel.repaint();
    }
}
