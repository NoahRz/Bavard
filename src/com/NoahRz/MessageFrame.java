package com.NoahRz;

import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame {
    /**
     * this class is a frame where we display the whole message, that we could'nt see entirely in the BavardFrame
     * */

    public MessageFrame(PapotageEvent message){
        this.setTitle("Message Frame");
        this.setSize(200,100);
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        Bavard sender = (Bavard)message.getSource();
        JLabel senderLabel = new JLabel("From :"+sender.getLogin());
        JLabel messageSubject = new JLabel("Subject : "+ message.getMessages().getSubject());
        JTextPane messageBody = new JTextPane();

        senderLabel.setOpaque(true); //so that we can change the background color of the label
        messageSubject.setOpaque(true);

        senderLabel.setBackground(Color.WHITE);
        messageSubject.setBackground(Color.WHITE);
        messageBody.setBackground(Color.WHITE);

        messageBody.setEditable(false);
        messageBody.setText(message.getMessages().getBody());


        pane.add(senderLabel,BorderLayout.NORTH);
        pane.add(messageSubject,BorderLayout.CENTER);
        pane.add(messageBody, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the frame without closing the program
        this.setVisible(true);
    }
}
