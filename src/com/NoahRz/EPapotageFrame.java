package com.NoahRz;

import javax.swing.*;

public class EPapotageFrame extends JFrame {
    /**
     * This class is the frame where the Bavard can send, receive message and the concierge can create Bavard and send and receive message*/
    private PapotageListener papotageListenerLogged;
    private Concierge concierge;

    public EPapotageFrame(PapotageListener papotageListenerLogged, Concierge concierge){
        this.papotageListenerLogged = papotageListenerLogged;
        this.concierge = concierge;
        this.setTitle("Bavard page");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    public EPapotageFrame(Concierge concierge) {
        this.concierge=concierge;
        this.setTitle("Concierge page");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
