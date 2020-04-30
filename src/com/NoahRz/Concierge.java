package com.NoahRz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Concierge implements PapotageListener {
    /**
     * this class will listen to messages sent by Bavards and send them to the reveicers (Bavards)
     * */

    private String login;
    private String password;
    private ArrayList<PapotageListener> papotageListeners;
    //private HashMap<PapotageListener, ArrayList<PapotageEvent>> recentDiscussion;

    public Concierge(String login, String password){
        this.login = login;
        this.password = password;
        this.papotageListeners = new ArrayList<PapotageListener>();
        //this.recentDiscussion = new HashMap<PapotageListener, ArrayList<PapotageEvent>>();
    }

    /********************************************************************
     Getter
     ********************************************************************/

    @Override
    public String getLogin() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public PapotageListener getPapotageListener(String addressee) {
        /**
         * return the PapotageListener having the login addressee
         * @Param addressee : login of the Papotage we are looking for*/
        for(PapotageListener pl : this.papotageListeners){
            if(pl.getLogin().equals(addressee)){
                return pl;
            }
        }
        return null;
    }

//    public HashMap<PapotageListener, ArrayList<PapotageEvent>> getRecentDiscussion() {
//        return recentDiscussion;
//    }

    /********************************************************************
     Methods
     ********************************************************************/

    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * receives the message sent and then send it to the addressees
         * @param pe: the PapotageEvent received*/
        System.out.println(this.login);
        System.out.println(pe.getSource());
        System.out.println(pe.getMessages().getSubject());
        for (String addressee : pe.getMessages().getAddressees()){
            PapotageListener pl = this.getPapotageListener(addressee);
            pl.receiveMessages(pe);
        }
    }

    @Override
    public void sendMessages(Message messageCreated) {
        /**
         * send the message to the addresses
         * @Param messageCreated : the message we've just created and we want to send
         * */
        PapotageEvent pe = new PapotageEvent(this, messageCreated); // source it's this object
        for (String addressee : pe.getMessages().getAddressees()){
            PapotageListener pl = this.getPapotageListener(addressee);
//            if (this.recentDiscussion.containsKey(pl)){
//                this.recentDiscussion.get(pl).add(pe); // we add this new message to the discussion
//            }else{
//                this.recentDiscussion.put(pl,new ArrayList<>(Arrays.asList(pe))); // we add a new discussion
//            }
            pl.receiveMessages(pe);
        }
    }

    public void createBavard(String login, String password){
     /**
     * create a new Bavard and add him to the list papotageListeners
     * @Param login: login of the Bavard we want to create*/
        this.addPapotageListeners(new Bavard(login, password,this));
    }

    private void addPapotageListeners(PapotageListener pl) {
        /**
         * add a PapotageListeners to the list papotageListeners
         * @Param pl : PapotageListener we ant to add*/
        this.papotageListeners.add(pl);
    }
}



