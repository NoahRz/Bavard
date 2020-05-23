package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BavardAdjustHisThemeFrame extends JFrame implements ActionListener {
    /**
     * JFrame where the bavard connected can adjust theme he likes. He will only receive theme he has selected.
     * Unlike the BavardAdjustItsListeningFrame, it won't send a request to the Concierge, it will directly change here.
     * */

    private Concierge concierge;
    private Bavard bavardLogged;
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

    public BavardAdjustHisThemeFrame(Bavard bavardLogged, Concierge concierge) {
        this.bavardLogged=bavardLogged;
        this.concierge=concierge;

        this.setTitle("Adjust theme page");
        this.setSize(new Dimension(200 ,230));
        JPanel pane = new JPanel(); //panel which contains a title label, a list of theme checkbox and a confirm button
        this.setContentPane(pane);
        this.setLayout(new BorderLayout());

        JLabel TitleLabel= new JLabel(" Themes you like :");
        pane.add(TitleLabel, BorderLayout.NORTH);

        JPanel themePanel = new JPanel(); //panel which contains list of theme checkbox
        themePanel.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()));
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        themePanel.setBackground(Color.YELLOW);

        //look through theme and check those who the bavard likes (= theme present in theme ArrayList of the bavard logged)
        for (String theme : this.concierge.getTheme()){
            JCheckBox checkBox = new JCheckBox(theme);
            checkBox.setActionCommand(theme);
            if (this.bavardLogged.getTheme().contains(theme)) {
                checkBox.setSelected(true);
            } else {
                checkBox.setSelected(false);
            }
            themePanel.add(checkBox);
            this.checkBoxes.add(checkBox);
        }

        JScrollPane themeScrollPane = new JScrollPane(themePanel); //we make themePanel scrollable
        pane.add(themeScrollPane,BorderLayout.CENTER);

        JButton confirmListenersButton = new JButton("Confirm theme");
        confirmListenersButton.setActionCommand("Confirm theme");
        confirmListenersButton.addActionListener(this);
        pane.add(confirmListenersButton, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Confirm theme")){ // if we press the confirm button
            ArrayList<String> theme = new ArrayList<>();
            for(JCheckBox cb : this.checkBoxes) {
                if (cb.isSelected()) {
                    theme.add(cb.getActionCommand());
                }
            }
            this.bavardLogged.setTheme(theme); // we set a new arrayList of theme to the bavard logged

            BavardFrame bavardLoggedFrame = this.bavardLogged.getMyFrame();
            if(bavardLoggedFrame != null){
                bavardLoggedFrame.refreshThemes(); // we refresh the list of themes in the bavard's Frame
            }

            JOptionPane.showMessageDialog(this,"theme selected");
            this.dispose();
        }
    }
}
