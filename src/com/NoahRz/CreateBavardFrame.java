package com.NoahRz; //ok

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CreateBavardFrame extends JFrame implements ActionListener, KeyListener {
    /**
     * JFrame where the concierge can create new bavards
     * */
    private Concierge concierge;
    private String passwordEntered;
    private String loginEntered;
    private JTextArea creationMessageTextArea;

    public CreateBavardFrame(Concierge concierge){
        this.concierge = concierge;

        this.setTitle("Create bavard page");
        this.setSize(new Dimension(600 ,600));
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


        /*-- creation bavard panel on the center of the Frame --*/
        JPanel creationBavardPanel  = new JPanel();
        creationBavardPanel.setLayout(new GridLayout(5,1));
        creationBavardPanel.setBackground(Color.YELLOW);

        JLabel creationBavardTitlePanel = new JLabel("Create a new Bavard", JLabel.CENTER);
        JTextField bavardNameTextField = new JTextField("Login");
        JPasswordField bavardPasswordField = new JPasswordField("Password");
        JButton creationBavardButton = new JButton("Create");

        this.creationMessageTextArea = new JTextArea(); // display if the bavard has been succesfully added or not
        this.creationMessageTextArea.setBackground(Color.YELLOW);

        /*-- listeners --*/
        bavardNameTextField.addKeyListener(this);
        bavardPasswordField.addKeyListener(this);
        creationBavardButton.addActionListener(this);

        creationBavardPanel.add(creationBavardTitlePanel);
        creationBavardPanel.add(bavardNameTextField);
        creationBavardPanel.add(bavardPasswordField);
        creationBavardPanel.add(creationBavardButton);
        creationBavardPanel.add(creationMessageTextArea);

        pane.add(creationBavardPanel, BorderLayout.CENTER);

        /*-- Yellow panel around the connexionPanel --*/
        JPanel borderFramePanelN = new JPanel();
        borderFramePanelN.setBackground(Color.YELLOW);
        borderFramePanelN.setPreferredSize(new Dimension(this.getWidth(), this.getWidth()/4));

        JPanel borderFramePanelS = new JPanel();
        borderFramePanelS.setBackground(Color.YELLOW);
        borderFramePanelS.setPreferredSize(new Dimension(this.getWidth(), this.getWidth()/4));

        JPanel borderFramePanelW = new JPanel();
        borderFramePanelW.setBackground(Color.YELLOW);
        borderFramePanelW.setPreferredSize(new Dimension(this.getWidth()/3, this.getWidth()/3));

        JPanel borderFramePanelE = new JPanel();
        borderFramePanelE.setBackground(Color.YELLOW);
        borderFramePanelE.setPreferredSize(new Dimension(this.getWidth()/3, this.getWidth()/3));

        pane.add(borderFramePanelN, BorderLayout.NORTH);
        pane.add(borderFramePanelS, BorderLayout.SOUTH);
        pane.add(borderFramePanelW, BorderLayout.WEST);
        pane.add(borderFramePanelE, BorderLayout.EAST);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Create")) { // if the button create is pressed
            if (concierge.createBavard(this.loginEntered, this.passwordEntered)) {
                this.creationMessageTextArea.setText("Bavard has been succesfully\ncreated !");
            } else {
                this.creationMessageTextArea.setText("Error: the login is already taken,\nplease chose another one.");
            }
        }
        if(e.getActionCommand().equals("back")){ //if back is pressed, we go back to the concierge Frame
            new ConciergeFrame(concierge);
            this.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() instanceof JPasswordField) { // we do this way because JPasswordField is also an instance of JTextField
            JPasswordField pf = (JPasswordField) e.getSource();
            this.passwordEntered = new String(pf.getPassword());
        }
        else if (e.getSource() instanceof JTextField) {
            JTextField tf = (JTextField) e.getSource();
            this.loginEntered = tf.getText();
        }
    }
}
