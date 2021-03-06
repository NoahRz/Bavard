package com.NoahRz.GUI;

import com.NoahRz.Bavard;
import com.NoahRz.Concierge;
import com.NoahRz.EPapotage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdjustBavardListenerFrame extends JFrame implements ActionListener {
    /**
     * JFrame where the concierge can  adjust the listener of a bavard, (who wants to listen who ?)
     */

    private Concierge concierge;
    private ButtonGroup bavardListenedButtonGroup;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
    private Bavard bavardSelected;

    public AdjustBavardListenerFrame(Concierge concierge) {
        this.concierge = concierge;

        this.setTitle("Adjust bavardListener page");
        this.setSize(new Dimension(300, 230));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout(new BorderLayout());

        /*-- Menu --*/
        JMenuBar menubar = new JMenuBar();
        JMenu optionMenu = new JMenu("Option");
        JMenuItem backMenu = new JMenuItem("Back");
        backMenu.setActionCommand("back");
        backMenu.addActionListener(this);

        optionMenu.add(backMenu);

        menubar.add(optionMenu);
        this.setJMenuBar(menubar);

        /*-- Left panel : bavardListenedPanel, panel where we select the bavard we want to adjust his listeners --*/

        JPanel bavardListenedPanel = new JPanel(); //panel which gathers a title label, list of bavard radioButton (in another panel) and a confirm button
        bavardListenedPanel.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight()));
        bavardListenedPanel.setLayout(new BorderLayout());

        JLabel bavardListenedTitleLabel = new JLabel(" Bavard Listened");
        bavardListenedPanel.add(bavardListenedTitleLabel, BorderLayout.NORTH);

        JPanel panelForRadioButton = new JPanel(); //panel which contains radioButton
        panelForRadioButton.setBackground(EPapotage.myYellowColor);
        panelForRadioButton.setLayout(new BoxLayout(panelForRadioButton, BoxLayout.Y_AXIS));
        bavardListenedButtonGroup = new ButtonGroup();
        for (Bavard bavard : concierge.getBavardsListenToBavardMap().keySet()) { // create all the bavard radiobutton
            JRadioButton rb = new JRadioButton(bavard.getLogin());
            rb.setActionCommand(bavard.getLogin());
            rb.addActionListener(this);
            bavardListenedButtonGroup.add(rb);
            panelForRadioButton.add(rb);
        }
        JScrollPane bavardListenedScrollPane = new JScrollPane(panelForRadioButton);//we make the panelForRadioButton scrollable
        bavardListenedPanel.add(bavardListenedScrollPane, BorderLayout.CENTER);

        JButton bavardListenedConfirmButton = new JButton("Confirm listened");
        bavardListenedConfirmButton.setActionCommand("Confirm listened");
        bavardListenedConfirmButton.addActionListener(this);

        pane.add(bavardListenedPanel, BorderLayout.CENTER);

        /*Right Panel : bavardListenersPanel, panel where we adjust the listeners of the bavard selected*/

        JPanel bavardListenersPanel = new JPanel(); // panel which gathers title label, list of bavard checkbox (in another panel) and a confirm button
        bavardListenersPanel.setPreferredSize(new Dimension(this.getWidth() / 2, this.getHeight()));
        bavardListenersPanel.setLayout(new BorderLayout());

        JLabel bavardListenersTitleLabel = new JLabel(" Bavard Listener");
        bavardListenersPanel.add(bavardListenersTitleLabel, BorderLayout.NORTH);

        JPanel panelForCheckBox = new JPanel(); //panel which contains checkbox
        panelForCheckBox.setBackground(EPapotage.myYellowColor);
        panelForCheckBox.setLayout(new BoxLayout(panelForCheckBox, BoxLayout.Y_AXIS));

        for (Bavard bavard : concierge.getBavardsListenToBavardMap().keySet()) {  // create all the bavard checkBox
            JCheckBox checkBox = new JCheckBox(bavard.getLogin());
            checkBox.setActionCommand(bavard.getLogin());
            this.checkBoxes.add(checkBox);
            panelForCheckBox.add(checkBox);
        }
        JScrollPane bavardListenersScrollPane = new JScrollPane(panelForCheckBox);//we make the panel For checkbox scrollable
        bavardListenersPanel.add(bavardListenersScrollPane, BorderLayout.CENTER);

        /* confirm listeners button */

        JButton confirmListenersButton = new JButton("Confirm listeners");
        confirmListenersButton.setActionCommand("Confirm listeners");
        confirmListenersButton.addActionListener(this);
        pane.add(confirmListenersButton, BorderLayout.SOUTH);

        pane.add(bavardListenersPanel, BorderLayout.EAST);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //so that we can't close the frame by clicking on "X", if we want to close we have to click on "back"
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) { //if back is pressed, we go back to the concierge Frame
            new ConciergeFrame(concierge);
            this.dispose();
        } else if (e.getActionCommand().equals("Confirm listeners")) { /*if we have pressed the button "Confirm listeners"*/
            if (this.bavardSelected != null) {
                ArrayList<Bavard> newBavardListeners = new ArrayList<Bavard>(); //we create a new ArrayList of new bavard listeners
                for (JCheckBox checkBox : this.checkBoxes) {
                    if (checkBox.isSelected()) {
                        newBavardListeners.add(concierge.getBavard(checkBox.getActionCommand())); // we add to the list the bavard related to the checkbox selected.
                    }
                }
                concierge.getBavardsListenToBavardMap().replace(bavardSelected, newBavardListeners);
                // we replace the old bavard listener arrayList of the bavard selected  by this new bavard listener arrayList
                JOptionPane.showMessageDialog(this, "Bavard listener has been selected"); //showMessageDialog that the action has been handled
            }

        } else { //if a radioButton is selected (Bavard radioButton)
            //for the bavard selected we pre-check checkboxes corresponding to bavard who are currently listening to the bavard selected

            for (JCheckBox checkBox : this.checkBoxes) { // we set all the checkBox enabled true so that we can check them and we unchecked them.
                checkBox.setSelected(false);
                checkBox.setEnabled(true);
            }

            String bavardListenedSelected = e.getActionCommand(); // we take the login of the bavard selected.
            bavardSelected = concierge.getBavard(bavardListenedSelected);
            ArrayList<Bavard> bavardListenersOfBavardSelected = concierge.getBavardListenersOfBavard(bavardSelected);
            for (JCheckBox checkBox : this.checkBoxes) {
                Bavard bavard = concierge.getBavard(checkBox.getActionCommand());
                if (bavard == bavardSelected) { //we set Enabled false the checkBox corresponding to the bavard selected so that he can't listen to himself
                    checkBox.setEnabled(false);
                } else if (bavardListenersOfBavardSelected.contains(bavard)) {
                    checkBox.setSelected(true); // if the current bavard is among the bavard listeners who listens to the bavard selected, we pre-check its checkBox
                } else {
                    checkBox.setSelected(false);
                }
            }
        }
    }
}
