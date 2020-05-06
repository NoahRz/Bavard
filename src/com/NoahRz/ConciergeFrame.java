package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConciergeFrame extends JFrame implements ActionListener, KeyListener {
    private Concierge concierge;
    private String passwordEntered;
    private String loginEntered;
    private JTextArea creationMessageTextArea;

    public ConciergeFrame(Concierge concierge){
        this.concierge = concierge;
        this.setTitle("Concierge page");
        this.setSize(new Dimension(600 ,600));
        JPanel pane = new JPanel();
        this.setContentPane(pane);
        this.setLayout( new BorderLayout());


        JPanel creationBavardPanel  = new JPanel();
        creationBavardPanel.setLayout(new GridLayout(5,1));
        creationBavardPanel.setBackground(Color.YELLOW);

        JLabel creationBavardTitlePanel = new JLabel("Create a new Bavard");
        JTextField bavardNameTextField = new JTextField("Login");
        JPasswordField bavardPasswordField = new JPasswordField("Password");
        JButton creationBavardButton = new JButton("Create");
        creationMessageTextArea = new JTextArea(); // display if the bavard has been succesfully added or not
        creationMessageTextArea.setBackground(Color.YELLOW);

        bavardNameTextField.addKeyListener(this);
        bavardPasswordField.addKeyListener(this);
        creationBavardButton.addActionListener(this);


        creationBavardPanel.add(creationBavardTitlePanel);
        creationBavardPanel.add(bavardNameTextField);
        creationBavardPanel.add(bavardPasswordField);
        creationBavardPanel.add(creationBavardButton);
        creationBavardPanel.add(creationMessageTextArea);

        pane.add(creationBavardPanel, BorderLayout.CENTER);


        /*Yellow panel around the connexionPanel*/
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

        /**Test**/
        JButton bouton = new JButton("adjust bavardListener");
        bouton.addActionListener(this);
        borderFramePanelE.add(bouton);
        /***/

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
                concierge.createBavard(this.loginEntered, this.passwordEntered);
                this.creationMessageTextArea.setText("Error: the login is already taken,\nplease chose another one.");
            }
        }else{
                new AdjustBavardListenerFrame(concierge);
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
