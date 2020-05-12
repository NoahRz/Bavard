package com.NoahRz;

import java.util.ArrayList;
import java.util.HashMap;

public class Bavard implements PapotageListener {
    /**
     * this class can send and receive messages, say that it is connected or disconnected
     * */
    private  String login;
    private Concierge concierge;
    private String password;
    private HashMap<PapotageListener, ArrayList<PapotageEvent>> recentDiscussion; //pas besoin
    private MessageViewPanel myMessageViewPanel; // Je crois qu'on en a pas besoin
    private BavardFrame1 myFrame;
    private boolean isConnected;

    public Bavard(String login, String password, Concierge concierge){
        this.login = login;
        this.concierge = concierge;
        this.password = password;
        this.recentDiscussion= new HashMap<PapotageListener, ArrayList<PapotageEvent>>();
        this.isConnected=false;
    }

    /********************************************************************
     Setter
     ********************************************************************/

    public void setFrame(BavardFrame1 myFrame){
        this.myFrame = myFrame;
    }

    public void setMessageViewPanel(MessageViewPanel myMessageViewPanel) { // Je crois qu'on en a pas besoin
        this.myMessageViewPanel = myMessageViewPanel;
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

    public BavardFrame1 getMyFrame() {
        return myFrame;
    } // je crois qu'on s'en sert pas

    public boolean isConnected() {
        return isConnected;
    }

    public MessageViewPanel getMessageViewPanel() {
        return myMessageViewPanel;
    }  // je crois qu'on s'en sert pas

    /********************************************************************
     Methods
     ********************************************************************/

    public void sendMessages(Message message){
        /**
         * send the message to the concierge and then the concierge will send it to bavards who listens this bavard
         * @Param messageCreated : the message we've just created and we want to send
         * */
        PapotageEvent pe = new PapotageEvent(this, message); // source it's this object
        concierge.receiveMessages(pe); //message sent
        this.myFrame.receiveMessages(pe); // we do this to display the message we have just sent in our Frame
    }

    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * receive the message
         * @Param pe: PapotageEvent with message
         * */
        if(this.myFrame != null) {
            this.myFrame.receiveMessages(pe); // when the bavard receive the message we display it on his Frame
        }
    }

  public void alerteIsConnected(){
      /**
       * send message to the concierge to alerte other bavard that this one is connected
       */

        Message message = new Message("",(" " + this.getLogin() + " is connected"));
        OnlineBavardEvent bavardConnectedEvent = new OnlineBavardEvent(this, message);
        this.isConnected = true;
        this.concierge.alerteBavardConnectedDisconnected(bavardConnectedEvent);
    }

    public void alerteIsDisconnected() {
        /**
         * send message to the concierge to alerte other bavard that this one is disconnected
         */
        Message message = new Message("",(" " + this.getLogin() + " is disconnected"));
        OfflineBavardEvent bavardDisconnectedEvent = new OfflineBavardEvent(this, message);
        this.isConnected=false;
        this.concierge.alerteBavardConnectedDisconnected(bavardDisconnectedEvent);

    }
}



