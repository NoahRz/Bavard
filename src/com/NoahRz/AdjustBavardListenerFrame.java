package com.NoahRz; //ok

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdjustBavardListenerFrame extends JFrame implements ActionListener {
    /**
     * Frame where the concierge can  adjust the listener of a bavard, (who wants to listen who ?)
     * */
    private Concierge concierge;
    private ButtonGroup bavardListenedButtonGroup;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    private Bavard bavardSelected;

    public AdjustBavardListenerFrame(Concierge concierge){
        this.concierge = concierge;

        this.setTitle("Adjust bavardListener page");
        this.setSize(new Dimension(300 ,230));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        int nbRowsForGridLayout = this.concierge.getBavardsListenToBavardMap().keySet().size() + 2;
        //number of bavard + 2 (2 corresponds to the label and the button)

        /*-- Left panel : bavardListenedPanel, panel where we select the bavard we want to adjust its listeners --*/
        JPanel bavardListenedPanel = new JPanel(); //panel which gathers a title label, list of bavard radioButton and a confirm button
        bavardListenedPanel.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
        bavardListenedPanel.setLayout(new GridLayout(nbRowsForGridLayout,1));
        bavardListenedPanel.setBackground(Color.YELLOW);

        JLabel bavardListenedTitleLabel = new JLabel("Bavard Listened");
        bavardListenedPanel.add(bavardListenedTitleLabel);

        bavardListenedButtonGroup = new ButtonGroup();
        for (Bavard bavard : concierge.getBavardsListenToBavardMap().keySet()){ // create all the bavard radiobutton
            JRadioButton rb = new JRadioButton(bavard.getLogin());
            rb.setActionCommand(bavard.getLogin());
            bavardListenedButtonGroup.add(rb);
            bavardListenedPanel.add(rb);
        }

        JButton bavardListenedConfirmButton = new JButton("Confirm listened");
        bavardListenedConfirmButton.setActionCommand("Confirm listened");
        bavardListenedConfirmButton.addActionListener(this);
        bavardListenedPanel.add(bavardListenedConfirmButton);

        JScrollPane bavardListenedScrollPane = new JScrollPane(bavardListenedPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.add(bavardListenedScrollPane,BorderLayout.CENTER);

        /*Right Panel : bavardListenersPanel, panel where we adjust the listeners of the bavard selected*/
        JPanel bavardListenersPanel = new JPanel(); // panel which gathers title label, list of bavard checkbox and a confirm button
        bavardListenersPanel.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
        bavardListenersPanel.setLayout(new GridLayout(nbRowsForGridLayout,1));
        bavardListenersPanel.setBackground(Color.YELLOW);

        JLabel bavardListenersTitleLabel= new JLabel("Bavard Listener");
        bavardListenersPanel.add(bavardListenersTitleLabel);


        for (Bavard bavard : concierge.getBavardsListenToBavardMap().keySet()){  // create all the bavard chechBox
            JCheckBox checkBox = new JCheckBox(bavard.getLogin());
            checkBox.setActionCommand(bavard.getLogin());
            this.checkBoxes.add(checkBox);
            bavardListenersPanel.add(checkBox);
        }
        JButton confirmListenersButton = new JButton("Confirm listeners");
        confirmListenersButton.setActionCommand("Confirm listeners");
        confirmListenersButton.addActionListener(this);
        bavardListenersPanel.add(confirmListenersButton);

        JScrollPane bavardListenersScrollPane = new JScrollPane(bavardListenersPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.add(bavardListenersScrollPane,BorderLayout.EAST);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //for the bavard selected we pre-check checkboxes corresponding to bavard who are currently listening to the bavard selected
        if (e.getActionCommand().equals("Confirm listened")) {
            String bavardListenedSelected = bavardListenedButtonGroup.getSelection().getActionCommand();
            bavardSelected = concierge.getBavard(bavardListenedSelected);
            ArrayList<Bavard> bavardListenersOfBavardSelected = concierge.getBavardListenersOfBavard(bavardSelected);
            for (JCheckBox checkBox : this.checkBoxes) {
                Bavard bavard = concierge.getBavard(checkBox.getActionCommand());
                if (bavardListenersOfBavardSelected.contains(bavard)) {
                    checkBox.setSelected(true); // we the current bavard is among the bavard listeners who listens to the bavard selected, we pre-check its checkBox
                } else {
                    checkBox.setSelected(false);
                }
            }
        }
        else { /*if we have pressed the button "Confirm listeners"*/
            if(this.bavardSelected != null){
                ArrayList<Bavard> newBavardListeners = new ArrayList<Bavard>();
                for (JCheckBox checkBox : this.checkBoxes) {
                    if (checkBox.isSelected()){
                        newBavardListeners.add(concierge.getBavard(checkBox.getActionCommand())); // we add to the list the bavard related to the checkbox selected.
                    }
                }
                concierge.getBavardsListenToBavardMap().replace(bavardSelected, newBavardListeners);
                // we replace the old bavard listener arrayList of the bavard selected  by this new bavard listener arrayList
            }

        }
    }
    }
