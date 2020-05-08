package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SignInFrame extends JFrame implements ActionListener, KeyListener{
    private Concierge concierge;
    private String loginEntered;
    private String passwordEntered;
    private JTextArea errorMessage = new JTextArea("Login or password is \nincorrect, please try again.");

    public SignInFrame(String nom, Concierge concierge){
        this.concierge = concierge;
        this.setTitle(nom);
        this.setSize(400,400);

        JPanel pane = new JPanel();
        pane.setBackground(Color.YELLOW);
        this.setContentPane(pane);
        this.setLayout(new BorderLayout());

        /*Connexion panel in Center of the Frame*/
        JPanel connexionPanel = new JPanel();
        connexionPanel.setLayout(new GridLayout(5,1));
        connexionPanel.setBackground(Color.YELLOW);

        JLabel titleLabel = new JLabel("EPapotage", SwingConstants.CENTER);
        JTextField loginField = new JTextField("Login");
        JPasswordField passwordField = new JPasswordField("Password");
        JButton signInButton = new JButton("Sign in");
        errorMessage.setBackground(Color.YELLOW);
        this.errorMessage.setVisible(false);

        connexionPanel.add(titleLabel);
        connexionPanel.add(loginField);
        connexionPanel.add(passwordField);
        connexionPanel.add(errorMessage);
        connexionPanel.add(signInButton);

        connexionPanel.setPreferredSize(new Dimension(pane.getWidth()/3, pane.getHeight()/2));
        pane.add(connexionPanel, BorderLayout.CENTER);

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

        pane.add(borderFramePanelN, BorderLayout.NORTH);
        pane.add(borderFramePanelS, BorderLayout.SOUTH);
        pane.add(borderFramePanelW, BorderLayout.WEST);
        pane.add(borderFramePanelE, BorderLayout.EAST);

        /*Listeners*/
        loginField.addKeyListener(this);
        passwordField.addKeyListener(this);
        signInButton.addActionListener(this);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
   public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            if ((this.loginEntered!= null) && (this.passwordEntered!=null)){
                Bavard guestBavard = concierge.getBavard(loginEntered);
                if (loginEntered.equals(concierge.getLogin())){
                    if (passwordEntered.equals(concierge.getPassword())){
                        new ConciergeFrame(concierge); /*Open a new Frame, the ConciergeFrame*/
                    }
                    else{
                        this.errorMessage.setVisible(true);
                    }
                }
                else if (guestBavard != null){
                    if (passwordEntered.equals(guestBavard.getPassword())){
                        new BavardFrame1(guestBavard); /*Open a new Frame*/
                        guestBavard.alerteIsConnected();

                    } else {
                        this.errorMessage.setVisible(true);
                    }
                }else{
                    this.errorMessage.setVisible(true);
                }
            }else{
                this.errorMessage.setVisible(true);
            }
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


