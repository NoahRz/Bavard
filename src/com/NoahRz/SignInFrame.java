package com.NoahRz;

import javax.swing.*;
import java.awt.*;

public class SignInFrame extends JFrame {

    public SignInFrame(String nom, Concierge concierge){
        this.setTitle(nom);
        this.setSize(400,400);

        JPanel pane = new JPanel();
        pane.setBackground(Color.YELLOW);
        this.setContentPane(pane);
        this.setLayout(new BorderLayout());

        /*Connexion panel in Center of the Frame*/
        JPanel connexionPanel = new JPanel();
        connexionPanel.setLayout(new GridLayout(4,1));
        connexionPanel.setBackground(Color.YELLOW);

        JLabel titleLabel = new JLabel("EPapotage", SwingConstants.CENTER);
        JTextField loginField = new JTextField("Login");
        JPasswordField passwordField = new JPasswordField("Password");
        JButton signInButton = new JButton("Sign in");

        connexionPanel.add(titleLabel);
        connexionPanel.add(loginField);
        connexionPanel.add(passwordField);
        connexionPanel.add(signInButton);

        connexionPanel.setPreferredSize(new Dimension(pane.getWidth()/3, pane.getHeight()/3));
        pane.add(connexionPanel, BorderLayout.CENTER);

        /*Yellow panel around the connexionPanel*/
        JPanel borderFramePanelN = new JPanel();
        borderFramePanelN.setBackground(Color.YELLOW);
        System.out.println(this.getWidth());
        System.out.println(this.getWidth()/3);
        borderFramePanelN.setPreferredSize(new Dimension(this.getWidth(), this.getWidth()/3));

        JPanel borderFramePanelS = new JPanel();
        borderFramePanelS.setBackground(Color.YELLOW);
        borderFramePanelS.setPreferredSize(new Dimension(this.getWidth(), this.getWidth()/3));

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
        //signInButton.addActionListener(new SignInButtonListener(concierge, this));

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public void displaySignInErrorMsg() {

    }
}


