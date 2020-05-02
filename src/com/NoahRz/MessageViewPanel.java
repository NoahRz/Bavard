package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MessageViewPanel extends JPanel implements PapotageListener {
    private EPapotageFrame frame; // the frame where this panel is
    private ArrayList<PapotageEvent> messagesReceivedList ;
    private Bavard bavardLogged;
    public MessageViewPanel(EPapotageFrame frame, Bavard bavardLogged){
        super();
        this.frame = frame;
        this.setBackground(Color.YELLOW);
        this.setPreferredSize(new Dimension(frame.getWidth()*3/4, frame.getHeight()*3/4));
        this.setLayout(new FlowLayout());
        this.messagesReceivedList = new ArrayList<PapotageEvent>();
        this.bavardLogged = bavardLogged;
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

        System.out.println("dans message view panel :" + pe.getMessages().getBody());

        // we display the messages in messagesReceivedList
        for(PapotageEvent message : this.messagesReceivedList) {
            JPanel oneMessagePanel = new JPanel();
            oneMessagePanel.setLayout(new BorderLayout());

            String usernameSender;
            if(pe.getSource() == this){
                usernameSender = "vous";
            }
            else{
                Bavard userSender = (Bavard)pe.getSource();
                usernameSender = userSender.getLogin();
            }


            JTextPane messageTextPane = new JTextPane();
            messageTextPane.setText(usernameSender +"\n" + message.getMessages().getBody()); //à quoi sert le sujet du message
            Dimension d = messageTextPane.getPreferredSize(); // we do that to only modify one dimension
            d.width = frame.getWidth() * 2 / 10;
            messageTextPane.setPreferredSize(d);
            oneMessagePanel.add(messageTextPane, BorderLayout.WEST);

            JPanel blankPanel = new JPanel();
            blankPanel.setBackground(Color.BLUE);
            blankPanel.setPreferredSize(new Dimension(frame.getWidth() / 2, 10));
            oneMessagePanel.add(blankPanel, BorderLayout.CENTER);

            this.add(oneMessagePanel);
        }
    }

    public void sendMessages(PapotageEvent pe){
        /**
         * we display the message we have just sent, it's like if we receive a message send by us
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
