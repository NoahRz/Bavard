package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BavardAdjustItsListeningFrame extends JFrame implements ActionListener {
    private Bavard bavardLogged;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();


    public BavardAdjustItsListeningFrame(Bavard bavardLogged, Concierge concierge){
        this.bavardLogged=bavardLogged;
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
        System.out.println("ok1");
        if(e.getActionCommand().equals("Confirm listeners")){
            System.out.println("ok");
            for(JCheckBox cb : this.checkBoxes){
                if (cb.isSelected()){
                    System.out.println(cb.getActionCommand());
                }
            }
        }
    }
}
