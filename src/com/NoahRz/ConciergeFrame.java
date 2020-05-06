package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConciergeFrame extends JFrame implements ActionListener {
    private Concierge concierge;

    public ConciergeFrame(Concierge concierge){
        this.concierge=concierge;
        this.setTitle("Concierge Frame");
        this.setSize(300,100);
        this.setLayout(new GridLayout(1,2));
        JPanel pane = new JPanel();
        pane.setBackground(Color.YELLOW);
        this.setContentPane(pane);
        this.setLayout(new GridLayout(1,2));

        JButton createBavardButton = new JButton("Create a new Bavard");
        createBavardButton.setActionCommand("Create a new Bavard");
        createBavardButton.addActionListener(this);

        JButton adjustBavardListenersButton = new JButton("Adjust bavard listener");
        adjustBavardListenersButton.setActionCommand("adjust bavard listener");
        adjustBavardListenersButton.addActionListener(this);

        pane.add(createBavardButton);
        pane.add(adjustBavardListenersButton);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Create a new Bavard")){
            new CreateBavardFrame(concierge);
            this.dispose();
        }else{
            new AdjustBavardListenerFrame(concierge);
            this.dispose();
        }
    }
}
