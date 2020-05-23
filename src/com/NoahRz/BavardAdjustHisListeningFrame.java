package com.NoahRz; //ok1

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BavardAdjustHisListeningFrame extends JFrame implements ActionListener {
    /**
     * JFrame where the bavard connected can adjust bavards he wants to listen to (but it won't really be the case, it will
     * just send a request to the concierge that this bavard wants to listen some one, then the concierge can approve or
     * not.
     * */

    private Concierge concierge;
    private Bavard bavardLogged;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>(); //ArrayList of other bavard checkbox (useful for ActionPerformed method)

    public BavardAdjustHisListeningFrame(Bavard bavardLogged, Concierge concierge){
        this.bavardLogged=bavardLogged;
        this.concierge=concierge;

        this.setTitle("Adjust listening page");
        this.setSize(new Dimension(300 ,230));
        JPanel pane = new JPanel(); //panel which contains a title label, a list of bavard checkbox and a confirm button
        this.setContentPane(pane);
        this.setLayout(new BorderLayout());

        JLabel TitleLabel= new JLabel(" Listen to :");
        pane.add(TitleLabel, BorderLayout.NORTH);

        JPanel listenToBavardPanel = new JPanel(); //panel which contains list of bavard checkbox
        listenToBavardPanel.setLayout(new BoxLayout(listenToBavardPanel, BoxLayout.Y_AXIS));
        listenToBavardPanel.setBackground(Color.YELLOW);

        //look through bavard and create checkbox and check those which correponds to bavard already listened by this one.
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
                this.checkBoxes.add(checkBox); // add checkBox to the arrayList of checkBox : useful for the ActionPerformed method
            }
        }

        JScrollPane bavardListenersScrollPane = new JScrollPane(listenToBavardPanel);
        pane.add(bavardListenersScrollPane,BorderLayout.CENTER);

        JButton confirmListenersButton = new JButton("Confirm listening");
        confirmListenersButton.setActionCommand("Confirm listening");
        confirmListenersButton.addActionListener(this);
        pane.add(confirmListenersButton, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Confirm listening")){ //when we press on the "Confirm listening" button
            for(JCheckBox cb : this.checkBoxes){
                Bavard bavardListened = this.concierge.getBavard(cb.getActionCommand());
                if (cb.isSelected()){ // if checked, it means we want to listen the bavard corresponding to this checkBox
                    if(!concierge.getBavardListenersOfBavard(bavardListened).contains(this.bavardLogged)){
                        // we check if the logged bavard is not already listening to the bavard corresponding to the checkbox. True:we can request
                        RequestEvent request = new RequestEvent(bavardLogged, "add", bavardListened);
                        int i = 0;
                        boolean doublon = false;
                        while (i<concierge.getRequestEventArrayList().size() && !doublon){
                            if(concierge.getRequestEventArrayList().get(i).equals(request)){
                                doublon = true;
                            }
                            i++;
                        }
                        if(!doublon){ // we make sure that we have not already sent the same request (there is no doublon),so we can send the request
                            concierge.receiveRequest(request);
                        }
                    }
                }else{ // if checked, it means we don't want to listen the bavard corresponding to this checkBox
                    if(concierge.getBavardListenersOfBavard(bavardListened).contains(this.bavardLogged)){
                        // we check if the logged bavard is listening to the bavard corresponding to the checkbox. True : we can request
                        RequestEvent request = new RequestEvent(bavardLogged, "remove", bavardListened);
                        int i = 0;
                        boolean doublon = false;
                        while (i<concierge.getRequestEventArrayList().size() && !doublon){
                            if(concierge.getRequestEventArrayList().get(i).equals(request)){
                                doublon = true;
                            }
                            i++;
                        }
                        if(!doublon){ // if there is no doublon, we can send the request
                            concierge.receiveRequest(request);
                        }
                    }
                }
            }
            this.dispose();
        }
    }
}
