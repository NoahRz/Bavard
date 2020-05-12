package com.NoahRz;// ne sert à rien

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MessageViewPanel extends JPanel implements PapotageListener { //ne sert à rien
    private BavardFrame frame; // the frame where this panel is
    private ArrayList<PapotageEvent> messagesReceivedList ;
    private Bavard bavardLogged;
    public MessageViewPanel(BavardFrame frame, Bavard bavardLogged){
        super();
        this.frame = frame;
        this.setBackground(Color.YELLOW);
        this.setPreferredSize(new Dimension(frame.getWidth()*3/4, frame.getHeight()*3/4));
        this.setLayout(new FlowLayout());
        this.messagesReceivedList = new ArrayList<PapotageEvent>();
        this.bavardLogged = bavardLogged;
        System.out.println("créé");
    }
    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * display the message receive by the bavard*/

        // we refresh the content of the panel
        this.removeAll();
        this.revalidate();
        this.repaint();

        this.messagesReceivedList.add(pe);

        // we display the messages which are in messagesReceivedList
        for(PapotageEvent message : this.messagesReceivedList) {
            JPanel oneMessagePanel = new JPanel();
            oneMessagePanel.setLayout(new BorderLayout());

            JPanel messageContentPanel = new JPanel();
            messageContentPanel.setLayout(new BorderLayout());

            JLabel messageBodyLabel = new JLabel();

            LineBorder line = new LineBorder(Color.blue, 5, true);

            oneMessagePanel.setBorder(line);

            JPanel blankPanel = new JPanel();
            String usernameSender;
            if (message instanceof OnlineOfflineBavardEvent){
                messageBodyLabel.setText(message.getMessages().getBody());
            }else {
                if (pe.getSource() == bavardLogged) {
                    usernameSender = "You";
                } else {
                    Bavard userSender = (Bavard) pe.getSource();
                    usernameSender = userSender.getLogin();
                }
                JLabel senderLabel = new JLabel("From:" + usernameSender);
                JLabel subjectLabel = new JLabel("Subject: "+message.getMessages().getSubject());

                messageContentPanel.add(senderLabel, BorderLayout.NORTH);
                messageContentPanel.add(subjectLabel, BorderLayout.CENTER);
                messageBodyLabel.setText("Body :" +message.getMessages().getBody());
            }

            Dimension d = messageBodyLabel.getPreferredSize();
            d.width = frame.getWidth() * 3 / 10;
            messageBodyLabel.setPreferredSize(d);



            messageContentPanel.add(messageBodyLabel, BorderLayout.SOUTH);

            messageContentPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    new MessageFrame(message);
                }
            });

            oneMessagePanel.add(messageContentPanel, BorderLayout.WEST); //messages we receive are on the left

            blankPanel.setBackground(Color.YELLOW);
            blankPanel.setPreferredSize(new Dimension(frame.getWidth() / 2, 10));
            oneMessagePanel.add(blankPanel, BorderLayout.CENTER);



            this.add(oneMessagePanel);
        }
    }

    public void sendMessages(PapotageEvent pe){
        /**
         * we display the message we have just sent, it's like if we receive a message sent by us
         * @param pe : PapotageEvent, the message we have sent*/
        this.receiveMessages(pe);
    }

    @Override
    public String getLogin() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
