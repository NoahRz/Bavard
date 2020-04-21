package com.NoahRz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInButtonListener implements ActionListener {
    private Concierge concierge;
    private SignInFrame signInFrame;

    public SignInButtonListener(Concierge concierge, SignInFrame signInFrame) {
        this.concierge = concierge;
        this.signInFrame = signInFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /*We check if the login and password entered match with one Bavard */
        SignInButton signInButton = (SignInButton)e.getSource();
        String loginEntered = signInButton.getLoginEntered();
        String passwordEntered = signInButton.getPasswordEntered();

        System.out.println("pressed button");
        System.out.println("loginEntered : " + loginEntered);
        System.out.println("passwordEntered : " + passwordEntered);


        PapotageListener guestBavard = concierge.getPapotageListener(loginEntered);
        if (loginEntered.equals(concierge.getLogin())){
            System.out.println("1");
            if (passwordEntered.equals(concierge.getPassword())){
                System.out.println("2");
                signInFrame.dispose(); /*close the signInFrame*/
                new EPapotageFrame(concierge);
            }
            else{
                System.out.println("3");
                signInFrame.displaySignInErrorMsg();
            }
        }
        else if (guestBavard != null){
            if (passwordEntered.equals(guestBavard.getPassword())){
                System.out.println("4");
                signInFrame.dispose(); /*close the signInFrame*/
                new EPapotageFrame(guestBavard, concierge); /*Open a new Frame*/
            } else {
                System.out.println("5");
                signInFrame.displaySignInErrorMsg();
            }
        }else{
            System.out.println("6");
            signInFrame.displaySignInErrorMsg();
        }


    }
}


