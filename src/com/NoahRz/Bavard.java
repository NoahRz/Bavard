package com.NoahRz;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Bavard implements PapotageListener {
    private  String login;
    private Concierge concierge;
    private String password;
    private HashMap<PapotageListener, ArrayList<PapotageEvent>> recentDiscussion;
    private MessageViewPanel myMessageViewPanel;
    private BavardFrame1 myFrame;

    public Bavard(String login, String password, Concierge concierge){
        this.login = login;
        this.concierge = concierge;
        this.password = password;
        this.recentDiscussion= new HashMap<PapotageListener, ArrayList<PapotageEvent>>();
    }

    public void setFrame(BavardFrame1 myFrame){
        this.myFrame = myFrame;
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
        this.myFrame.receiveMessages(pe);
    }


    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * receive the message
         * @Param pe: PapotageEvent with message
         * */
        if(this.myFrame != null) {
            this.myFrame.receiveMessages(pe);
        }
        // when the bavard receive the message we display it on his messageViewPanel
    }


    public void setMessageViewPanel(MessageViewPanel myMessageViewPanel) {
        this.myMessageViewPanel = myMessageViewPanel;
    }

  public void alerteIsConnected(){
        /**
        send message to the concierge to alerte other bavard that this one is connected */
        Message message = new Message("",(this.getLogin() + " is connected"));
        OnlineBavardEvent bavardConnectedEvent = new OnlineBavardEvent(this, message);
        this.concierge.alerteBavardConnecedDisconnected(bavardConnectedEvent);
    }

    public MessageViewPanel getMessageViewPanel() {
        return myMessageViewPanel;
    }

    public void alerteIsDisconnected() {
        Message message = new Message("",(this.getLogin() + " is disconnected"));
        OfflineBavardEvent bavardDisconnectedEvent = new OfflineBavardEvent(this, message);
        this.concierge.alerteBavardConnecedDisconnected(bavardDisconnectedEvent);
    }
}



