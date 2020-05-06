package com.NoahRz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Bavard implements PapotageListener {
    private  String login;
    private PapotageListener concierge;
    private String password;
    private HashMap<PapotageListener, ArrayList<PapotageEvent>> recentDiscussion;
    private MessageViewPanel myMessageViewPanel;

    public Bavard(String login, String password, PapotageListener concierge){
        this.login = login;
        this.concierge = concierge;
        this.password = password;
        this.recentDiscussion= new HashMap<PapotageListener, ArrayList<PapotageEvent>>();
    }

    /********************************************************************
     Getter
     ********************************************************************/
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getLogin(){
        return login;
    }

    public HashMap<PapotageListener, ArrayList<PapotageEvent>> getRecentDiscussion() {
        return recentDiscussion;
    }

    /********************************************************************
     Methods
     ********************************************************************/
    public Message createMessage(String subject, String body){
        /**
         * return the message created by the Bavard
         * @Param subject: the subject of the message
         * @Param body : the body of the message
         * @Param addresses : ArrayList of addressees*/
        return new Message(subject, body);
    }

    public void sendMessages(Message messageCreated){
        /**
         * send the message to the concierge with the addresses and then the concierge will send it to the addressees
         * @Param messageCreated : the message we've just created and we want to send
         * */
        PapotageEvent pe = new PapotageEvent(this, messageCreated); // source it's this object
        concierge.receiveMessages(pe); //message sent
        this.myMessageViewPanel.sendMessages(pe);
    }


    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * receive the message
         * @Param pe: PapotageEvent with message
         * */
        this.myMessageViewPanel.receiveMessages(pe);
        // when the bavard receive the message we display it on his messageViewPanel
    }


    public void addPapotageListener(PapotageListener pl){
        /**
         * add a PapotageListener
         * @param pl : the PapotageListener
         * */
        this.concierge = pl;
    }

    public void setMessageViewPanel(MessageViewPanel myMessageViewPanel) {
        this.myMessageViewPanel = myMessageViewPanel;
    }

    public MessageViewPanel getMessageViewPanel() {
        return myMessageViewPanel;
    }
}



