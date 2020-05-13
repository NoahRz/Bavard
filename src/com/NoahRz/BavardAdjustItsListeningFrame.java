package com.NoahRz; //ok

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BavardAdjustItsListeningFrame extends JFrame implements ActionListener {
    /**
     * JFrame where the bavard connected can adjust bavards he wants to listen (but it won't be really the case, it will
     * just send a request to the concierge that this bavard wants to listen some one, then the concierge can approve or
     * not.
     * */

    private Concierge concierge;
    private Bavard bavardLogged;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

    public BavardAdjustItsListeningFrame(Bavard bavardLogged, Concierge concierge){
        this.bavardLogged=bavardLogged;
        this.concierge=concierge;

        this.setTitle("Adjust listening page");
        this.setSize(new Dimension(300 ,230));
        JPanel pane = new JPanel(); //panel which contains a title label, a list of bavard checkbox and a confirm button
        this.setContentPane(pane);
        this.setLayout(new BorderLayout());

        JLabel TitleLabel= new JLabel("Listen to :");
        pane.add(TitleLabel, BorderLayout.NORTH);

        JPanel listenToBavardPanel = new JPanel(); //panel which contains list of bavard checkbox
        listenToBavardPanel.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
        listenToBavardPanel.setLayout(new BoxLayout(listenToBavardPanel, BoxLayout.Y_AXIS));
        listenToBavardPanel.setBackground(Color.YELLOW);

        //look through bavard and check those who the bavard is listening to
        for (Bavard bavard : concierge.getBavardsListenToBavardMap().keySet()){
            if(bavard != bavardLogged) { // so that this bavard cannot listen to himself
                JCheckBox checkBox = new JCheckBox(bavard.getLogin());
                checkBox.setActionCommand(bavard.getLogin());
                if (concierge.getBavardListenersOfBavard(bavard).contains(this.bavardLogged)) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
                listenToBavardPanel.add(checkBox);
                this.checkBoxes.add(checkBox);
            }
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
                        // we check if the logged bavard is not already listening to the bavard corresponding to the checkbox. True:we can request
                        RequestEvent request = new RequestEvent(bavardLogged, "add", bavardListened);
                        concierge.receiveRequest(request);
                    }
                }else{
                    if(concierge.getBavardListenersOfBavard(bavardListened).contains(this.bavardLogged)){
                        // we check if the logged bavard is listening to the bavard corresponding to the checkbox. True : we can request
                        RequestEvent request = new RequestEvent(bavardLogged, "remove", bavardListened);
                        concierge.receiveRequest(request);
                    }
                }
            }
            this.dispose();
        }
    }
}
