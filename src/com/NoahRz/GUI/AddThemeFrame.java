package com.NoahRz.GUI;

import com.NoahRz.Concierge;
import com.NoahRz.EPapotage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AddThemeFrame extends JFrame implements KeyListener, ActionListener {
    /**
     * JFrame where the concierge can add new theme
     * */

    private Concierge concierge;
    private String themeEntered;
    private JTextArea addThemeMessageTextArea;

    public AddThemeFrame(Concierge concierge) {
        this.concierge = concierge;

        this.setTitle("add theme page");
        this.setSize(new Dimension(450 ,450));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());

        /*-- Menu --*/
        JMenuBar menubar= new JMenuBar();
        JMenu optionMenu = new JMenu("Option");
        JMenuItem backMenu = new JMenuItem("Back");
        backMenu.setActionCommand("back");
        backMenu.addActionListener(this);

        optionMenu.add(backMenu);

        menubar.add(optionMenu);
        this.setJMenuBar(menubar);

        /*-- add theme panel on the center of the Frame --*/
        JPanel addThemePanel  = new JPanel();
        addThemePanel.setLayout(new GridLayout(4,1));
        addThemePanel.setBackground(EPapotage.myYellowColor);

        JLabel addThemeTitleLabel = new JLabel("Add a new theme", JLabel.CENTER);
        Font fontForCreationBavardTitlePanel =new Font(addThemeTitleLabel.getFont().getName(),Font.BOLD,addThemeTitleLabel.getFont().getSize()); //make the creationBavardTitleLabel bold
        addThemeTitleLabel.setFont(fontForCreationBavardTitlePanel);
        JTextField themeNameTextField = new JTextField("theme");

        JButton addThemeButton = new JButton("Add");

        this.addThemeMessageTextArea = new JTextArea(); // display if the theme has been succesfully added or not
        this.addThemeMessageTextArea.setBackground(EPapotage.myYellowColor);

        /*-- listeners --*/
        themeNameTextField.addKeyListener(this);
        addThemeButton.addActionListener(this);
        /*---------------*/

        addThemePanel.add(addThemeTitleLabel);
        addThemePanel.add(themeNameTextField);
        addThemePanel.add(addThemeButton);
        addThemePanel.add(addThemeMessageTextArea);

        pane.add(addThemePanel, BorderLayout.CENTER);

        /*-- Yellow panel around the addThemePanel --*/
        JPanel borderFramePanelN = new JPanel();
        borderFramePanelN.setBackground(EPapotage.myYellowColor);
        borderFramePanelN.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/5));

        JPanel borderFramePanelS = new JPanel();
        borderFramePanelS.setBackground(EPapotage.myYellowColor);
        borderFramePanelS.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/5));

        JPanel borderFramePanelW = new JPanel();
        borderFramePanelW.setBackground(EPapotage.myYellowColor);
        borderFramePanelW.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()*3/5));

        JPanel borderFramePanelE = new JPanel();
        borderFramePanelE.setBackground(EPapotage.myYellowColor);
        borderFramePanelE.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()*3/5));

        pane.add(borderFramePanelN, BorderLayout.NORTH);
        pane.add(borderFramePanelS, BorderLayout.SOUTH);
        pane.add(borderFramePanelW, BorderLayout.WEST);
        pane.add(borderFramePanelE, BorderLayout.EAST);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //so that we can't close the frame by clicking on "X", if we want to close we have to click on "back"
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) { //when we type the theme we want to add
        JTextField tf = (JTextField) e.getSource();
        this.themeEntered = tf.getText();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add")) { // if the button create is pressed
            if (this.themeEntered!=null && concierge.addTheme(this.themeEntered)) {
                this.addThemeMessageTextArea.setText("thame has been succesfully\nadded !");
                this.addThemeMessageTextArea.setForeground(Color.BLACK);
            } else {
                this.addThemeMessageTextArea.setText("Error: the theme is already taken\nor is not possible,\nplease chose another one.");
                this.addThemeMessageTextArea.setForeground(Color.RED);
            }
        }
        if(e.getActionCommand().equals("back")){ //if back is pressed, we go back to the concierge Frame
            new ConciergeFrame(concierge);
            this.dispose();
        }
    }
}
