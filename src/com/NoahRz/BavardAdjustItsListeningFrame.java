package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BavardAdjustItsListeningFrame extends JFrame implements ActionListener {
    private Concierge concierge;
    private Bavard bavardLogged;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();


    public BavardAdjustItsListeningFrame(Bavard bavardLogged, Concierge concierge){
        this.bavardLogged=bavardLogged;
        this.concierge=concierge;
        this.setTitle("Adjust listening page");
        this.setSize(new Dimension(300 ,230));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.YELLOW);


        JLabel TitleLabel= new JLabel("Listen to :");
        pane.add(TitleLabel, BorderLayout.NORTH);

        JPanel listenToBavardPanel = new JPanel();
        listenToBavardPanel.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
        listenToBavardPanel.setLayout(new BoxLayout(listenToBavardPanel, BoxLayout.Y_AXIS));
        listenToBavardPanel.setBackground(Color.YELLOW);

        for (Bavard bavard : concierge.getBavardsListenToBavardMap().keySet()){
            JCheckBox checkBox = new JCheckBox(bavard.getLogin());
            checkBox.setActionCommand(bavard.getLogin());
            if (concierge.getBavardListenersOfBavard(bavard).contains(this.bavardLogged)){
                checkBox.setSelected(true);
            }else{
                checkBox.setSelected(false);
            }
            listenToBavardPanel.add(checkBox);
            this.checkBoxes.add(checkBox);

        }

        JScrollPane bavardListenersScrollPane = new JScrollPane(listenToBavardPanel);
        pane.add(bavardListenersScrollPane,BorderLayout.CENTER);

        JButton confirmListenersButton = new JButton("Confirm listeners");
        confirmListenersButton.setActionCommand("Confirm listeners");
        confirmListenersButton.addActionListener(this);
        pane.add(confirmListenersButton, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Confirm listeners")){
            for(JCheckBox cb : this.checkBoxes){
                Bavard bavardListened = this.concierge.getBavard(cb.getActionCommand());
                if (cb.isSelected()){
                    if(!concierge.getBavardListenersOfBavard(bavardListened).contains(this.bavardLogged)){
                        // we check if the logged bavard is not already listening to the bavard corresponding to the checkbox. True:we can add it
                        RequestEvent request = new RequestEvent(bavardLogged, "add", bavardListened);
                        concierge.receiveRequest(request);
                    }
                }else{
                    if(concierge.getBavardListenersOfBavard(bavardListened).contains(this.bavardLogged)){
                        // we check if the logged bavard is listening to the bavard corresponding to the checkbox. True : we can remove it
                        RequestEvent request = new RequestEvent(bavardLogged, "remove", bavardListened);
                        concierge.receiveRequest(request);
                    }
                }
            }
        }
    }
}
