package com.NoahRz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInButton extends JButton implements ActionListener {
    /**
     * We create a custom button because this button is also an ActionListener of loginField and passwordField
     */
    private String loginEntered;
    private String passwordEntered;

    @Override

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JTextField) {
            JTextField tf = (JTextField) e.getSource();
            this.loginEntered = tf.getText();
        }
        if (e.getSource() instanceof JPasswordField) {
            JPasswordField pf = (JPasswordField) e.getSource();
            this.passwordEntered = new String(pf.getPassword());
        }
    }

    public String getLoginEntered() {
        return loginEntered;
    }

    public String getPasswordEntered() {
        return passwordEntered;
    }
}
