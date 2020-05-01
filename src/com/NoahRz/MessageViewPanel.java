package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MessageViewPanel extends JPanel implements PapotageListener {
    private EPapotageFrame frame; // the frame where this panel is
    private ArrayList<PapotageEvent> messagesReceivedList ;
    public MessageViewPanel(EPapotageFrame frame){
        super();
        this.frame = frame;
        this.setBackground(Color.YELLOW);
        this.setPreferredSize(new Dimension(frame.getWidth()*3/4, frame.getHeight()*3/4));
        this.setLayout(new FlowLayout());
        this.messagesReceivedList = new ArrayList<PapotageEvent>();
    }
    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * display the message receive by the bavard*/
        this.removeAll();
        this.revalidate();
        this.repaint();

        this.messagesReceivedList.add(pe);

        System.out.println("dans message view panel :" + pe.getMessages().getBody());

        for(PapotageEvent message : this.messagesReceivedList) {
            JPanel oneMessagePanel = new JPanel();
            oneMessagePanel.setLayout(new BorderLayout());

            JTextPane messageTextPane = new JTextPane();
            messageTextPane.setText(message.getMessages().getBody()); //à quoi sert le sujet du message
            Dimension d = messageTextPane.getPreferredSize(); // we do that to only modify one dimension
            d.width = frame.getWidth() * 2 / 10;
            messageTextPane.setPreferredSize(d);
            oneMessagePanel.add(messageTextPane, BorderLayout.WEST);

            JPanel blankPanel = new JPanel();
            blankPanel.setBackground(Color.BLUE);
            blankPanel.setPreferredSize(new Dimension(frame.getWidth() / 2, 10));
            //blankPanel.setPreferredSize(new Dimension(this.getWidth()/2, messageTextPane.getHeight()));
            oneMessagePanel.add(blankPanel, BorderLayout.CENTER);

            this.add(oneMessagePanel);
        }
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
