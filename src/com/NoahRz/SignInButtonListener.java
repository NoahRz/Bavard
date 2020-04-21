package com.NoahRz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInButtonListener implements ActionListener {
    private  String passwordEntered;
    private  String loginEntered;
    private Concierge concierge;
    private SignInFrame signInFrame;

    public SignInButtonListener(Concierge concierge, String loginEntered, String passwordEntered, SignInFrame signInFrame) {
        this.concierge = concierge;
        this.loginEntered = loginEntered;
        this.passwordEntered = passwordEntered;
        this.signInFrame = signInFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*We check if the login and password entered match with one Bavard */
        PapotageListener guestBavard = concierge.getPapotageListener(loginEntered);
        if (guestBavard != null){
            if (passwordEntered.equals(guestBavard.getPassword())){
                /*close the current Frame and open an other one */
                signInFrame.dispose(); /*close the signInFrame*/
                //new EPapotageFrame(); /*param : PapotageListenerConnected guestBavard, Concierge concierge*/
            } else {
                signInFrame.displaySignInErrorMsg();
            }
        }else{
            signInFrame.displaySignInErrorMsg();
        }


    }
}


