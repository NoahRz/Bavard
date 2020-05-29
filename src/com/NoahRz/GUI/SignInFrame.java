package com.NoahRz.GUI;

import com.NoahRz.Bavard;
import com.NoahRz.Concierge;
import com.NoahRz.EPapotage;
import com.NoahRz.GUI.BavardFrame;
import com.NoahRz.GUI.ConciergeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SignInFrame extends JFrame implements ActionListener, KeyListener{
    /**
     * JFrame where the user can sign in as a Bavard or as a Concierge
     * */

    private Concierge concierge;
    private String loginEntered;
    private String passwordEntered;
    private JTextArea errorMessage = new JTextArea("Login or password is \nincorrect, please try again.");

    public SignInFrame(String nom, Concierge concierge){
        this.concierge = concierge;
        this.setTitle(nom);
        this.setSize(500,450);
        JPanel pane = new JPanel();
        pane.setBackground(EPapotage.myYellowColor);
        this.setContentPane(pane);
        this.setLayout(new BorderLayout());

        /*-- Connexion panel in Center of the Frame --*/
        JPanel connexionPanel = new JPanel();
        connexionPanel.setLayout(new GridLayout(5,1));
        connexionPanel.setBackground(EPapotage.myYellowColor);

        JLabel titleLabel = new JLabel("ePapotage", SwingConstants.CENTER);
        Font fontForTitleLabel =new Font(titleLabel.getFont().getName(),Font.BOLD,titleLabel.getFont().getSize());
        titleLabel.setFont(fontForTitleLabel);
        JTextField loginField = new JTextField("Login");
        JPasswordField passwordField = new JPasswordField("Password");
        JButton signInButton = new JButton("Sign in");
        errorMessage.setBackground(EPapotage.myYellowColor);
        errorMessage.setForeground(Color.RED); // set the text color to red
        this.errorMessage.setVisible(false);

        connexionPanel.add(titleLabel);
        connexionPanel.add(loginField);
        connexionPanel.add(passwordField);
        connexionPanel.add(errorMessage);
        connexionPanel.add(signInButton);

        connexionPanel.setPreferredSize(new Dimension(this.getWidth()/3, this.getHeight()/2));
        pane.add(connexionPanel, BorderLayout.CENTER);

        /*-- Yellow panel around the connexionPanel --*/
        JPanel borderFramePanelN = new JPanel();
        borderFramePanelN.setBackground(EPapotage.myYellowColor);
        borderFramePanelN.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/4));

        JPanel borderFramePanelS = new JPanel();
        borderFramePanelS.setBackground(EPapotage.myYellowColor);
        borderFramePanelS.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/4));

        JPanel borderFramePanelW = new JPanel();
        borderFramePanelW.setBackground(EPapotage.myYellowColor);
        borderFramePanelW.setPreferredSize(new Dimension(this.getWidth()/3, this.getHeight()/3));

        JPanel borderFramePanelE = new JPanel();
        borderFramePanelE.setBackground(EPapotage.myYellowColor);
        borderFramePanelE.setPreferredSize(new Dimension(this.getWidth()/3, this.getHeight()/3));

        pane.add(borderFramePanelN, BorderLayout.NORTH);
        pane.add(borderFramePanelS, BorderLayout.SOUTH);
        pane.add(borderFramePanelW, BorderLayout.WEST);
        pane.add(borderFramePanelE, BorderLayout.EAST);

        /*-- Listeners --*/
        loginField.addKeyListener(this);
        passwordField.addKeyListener(this);
        signInButton.addActionListener(this);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
   public void actionPerformed(ActionEvent e) {
        errorMessage.setEditable(false);
        String errorLoginPasswordMessage = "Login or password is \nincorrect, please try again.";
        String errorAlreadyConnectedMessage = "You are already connected";
        if(e.getSource() instanceof JButton){
            if ((this.loginEntered!= null) && (this.passwordEntered!=null)){
                Bavard guestBavard = concierge.getBavard(loginEntered);
                if (loginEntered.equals(concierge.getLogin())){
                    if (passwordEntered.equals(concierge.getPassword())){ // check if the login and password entered by the user correspond to the concierge's
                        if(!concierge.isConnected()) { // so that we can't have more than one concierge Frame opened at the same time
                            new ConciergeFrame(concierge); // Open a new Frame, the ConciergeFrame
                            concierge.setConnected(true);
                        }else{
                            this.errorMessage.setText(errorAlreadyConnectedMessage);
                            this.errorMessage.setVisible(true);
                        }
                    }
                    else{
                        this.errorMessage.setText(errorLoginPasswordMessage);
                        this.errorMessage.setVisible(true);
                    }
                }
                else if (guestBavard != null){
                    if (passwordEntered.equals(guestBavard.getPassword())){ // check if the login and password entered by the user correspond to a bavard's
                        if(!guestBavard.isConnected()) { // so that we can't have more than one bavard Frame of a bavard opened at the same time
                            new BavardFrame(guestBavard, this.concierge); // Open a new Frame, the BavardFrame
                        }else{
                            this.errorMessage.setText(errorAlreadyConnectedMessage);
                            this.errorMessage.setVisible(true);
                        }
                    } else {
                        this.errorMessage.setText(errorLoginPasswordMessage);
                        this.errorMessage.setVisible(true);
                    }
                }else{
                    this.errorMessage.setText(errorLoginPasswordMessage);
                    this.errorMessage.setVisible(true);
                }
            }else{
                this.errorMessage.setText(errorLoginPasswordMessage);
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
        if (e.getSource() instanceof JPasswordField) { // If we typed on the password field. We do this way because JPasswordField is also an instance of JTextField
            JPasswordField pf = (JPasswordField) e.getSource();
            this.passwordEntered = new String(pf.getPassword());
        }
        else if (e.getSource() instanceof JTextField) { // If we typed on the login field
            JTextField tf = (JTextField) e.getSource();
            this.loginEntered = tf.getText();
        }

    }
}


