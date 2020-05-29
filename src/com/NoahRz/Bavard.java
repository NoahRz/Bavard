package com.NoahRz; //ok1

import java.util.ArrayList;

public class Bavard implements PapotageListener {
    /**
     * this class can send and receive messages, warn others he has just logged in or he has just logged out
     * */

    private  String login;
    private Concierge concierge;
    private String password;
    private BavardFrame myFrame;
    private boolean isConnected;
    private ArrayList<String> theme; //arrayList of theme he likes

    public Bavard(String login, String password, Concierge concierge){
        this.login = login;
        this.concierge = concierge;
        this.password = password;
        this.isConnected=false;
        this.theme = new ArrayList<String>();
    }

    /********************************************************************
     Setter
     ********************************************************************/

    public void setFrame(BavardFrame myFrame){
        this.myFrame = myFrame;
    }


    public void setThemes(ArrayList<String> themes){
        this.theme = themes;
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

    public BavardFrame getMyFrame() {
        return myFrame;
    } // je crois qu'on s'en sert pas

    public boolean isConnected() {
        return isConnected;
    }

    public ArrayList<String> getThemes() {
        return this.theme;
    }

    /********************************************************************
     Methods
     ********************************************************************/

    public void sendMessages(ArrayList<String> themes, String messageSubject, String messageBody){
        /**
         * send the message to the concierge and then the concierge will send it to bavards who listens to this bavard
         * @Param messageSubject : String
         * @Param messageBody : String
         * */
        PapotageEvent pe = new PapotageEvent(this, themes, messageSubject, messageBody); // source it's this object
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

  public void warnIsConnected(){
      /**
       * send message to the concierge to warn other bavard that he is connected
       */

        String messageBody = (" " + this.getLogin() + " is connected");
        OnlineBavardEvent bavardConnectedEvent = new OnlineBavardEvent(this, messageBody);
        this.isConnected = true;
        this.concierge.alertBavardConnectedDisconnected(bavardConnectedEvent);
    }

    public void warnIsDisconnected() {
        /**
         * send message to the concierge to warn other bavard that this one is disconnected
         */
        String messageBody = (" " + this.getLogin() + " is disconnected");
        OfflineBavardEvent bavardDisconnectedEvent = new OfflineBavardEvent(this, messageBody);
        this.isConnected=false;
        this.concierge.alertBavardConnectedDisconnected(bavardDisconnectedEvent);

    }
}



