package com.NoahRz;//ok

import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame {
    /**
     * JFrame where we display the whole message, that we could'nt see entirely in the BavardFrame
     * */

    public MessageFrame(PapotageEvent message){
        this.setTitle("Message Frame");
        this.setSize(300,200);
        JPanel pane = new JPanel(); //panel contains two labels (one to show the sender and one to show the message subject) and a textPane(to show the whole message body)
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        Bavard sender = (Bavard)message.getSource();
        JLabel senderLabel = new JLabel("From :"+sender.getLogin());
        JLabel messageSubjectLabel = new JLabel("Subject : " + message.getMessages().getSubject());
        JTextPane messageBodyTextPane = new JTextPane();

        JPanel panelForSenderLabelAndmessageSubjectLabel = new JPanel();
        panelForSenderLabelAndmessageSubjectLabel.setLayout(new BorderLayout());

        JScrollPane messageBodyScrollPane = new JScrollPane(messageBodyTextPane); //we make the message body scrollable, so that it's easier to view the whole message

        senderLabel.setOpaque(true); //so that we can change the background color of the label
        messageSubjectLabel.setOpaque(true);

        senderLabel.setBackground(Color.WHITE);
        messageSubjectLabel.setBackground(Color.WHITE);
        messageBodyTextPane.setBackground(Color.WHITE);

        messageBodyTextPane.setEditable(false);
        messageBodyTextPane.setText(message.getMessages().getBody());

        panelForSenderLabelAndmessageSubjectLabel.add(senderLabel,BorderLayout.NORTH);
        panelForSenderLabelAndmessageSubjectLabel.add(messageSubjectLabel,BorderLayout.CENTER);
        pane.add(panelForSenderLabelAndmessageSubjectLabel, BorderLayout.NORTH);
        pane.add(messageBodyScrollPane, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the frame without closing the program
        this.setVisible(true);
    }
}
