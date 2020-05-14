package com.NoahRz; //ok

import java.util.ArrayList;
import java.util.HashMap;

public class Concierge implements PapotageListener {
    /**
     * this class will listen to messages sent by Bavards and send them to the reveicers (Bavards)
     * */

    private String login;
    private String password;
    //private ArrayList<PapotageListener> papotageListeners;
    private HashMap<Bavard, ArrayList<Bavard>> bavardsListenToBavardMap; // keys are bavards and the value is an array of all the bavard who listens to each bavard in keys
    private ArrayList<RequestEvent> requestEventArrayList; // ArrayList which gathers all the request sent by bavard asking to listen other bavard
    private boolean isConnected;
    private ConciergeFrame conciergeFrame;

    public Concierge(String login, String password){
        this.login = login;
        this.password = password;
        //this.papotageListeners = new ArrayList<PapotageListener>();
        this.bavardsListenToBavardMap = new HashMap<Bavard, ArrayList<Bavard>>();
        this.requestEventArrayList = new ArrayList<RequestEvent>();
        this.isConnected = false;
    }
    /********************************************************************
     Setter
     ********************************************************************/
    public void setConnected(boolean bool){
        this.isConnected=bool;
    }

    public void setFrame(ConciergeFrame conciergeFrame){
        this.conciergeFrame=conciergeFrame;
    }

    /********************************************************************
     Getter
     ********************************************************************/

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public String getLogin() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

/*    public PapotageListener getPapotageListener(String addressee) {
        *//**
         * return the PapotageListener having the login addressee
         * @Param addressee : login of the Papotage we are looking for*//*
        for(PapotageListener pl : this.papotageListeners){
            if(pl.getLogin().equals(addressee)){
                return pl;
            }
        }
        return null;
    }*/

    public HashMap<Bavard, ArrayList<Bavard>> getBavardsListenToBavardMap() {
        return bavardsListenToBavardMap;
    }

    public Bavard getBavard(String login){
        /**
         * return the bavard who has the same login
         * @param login : String, login of the bavard we want to return
         * */
        // we go through the keys the bavardsListenToBavardMap which are bavards and check if there is one who has the same login as the login in parameters
        for (Bavard bavard : this.bavardsListenToBavardMap.keySet()){
            if(bavard.getLogin().equals(login)){
                return bavard;
            }
        }return null;

    }

    public ArrayList<Bavard> getBavardListenersOfBavard(Bavard bavard){
        /**
         * return an ArrayList of Bavard who listens bavard
         * @param bavard : Bavard
         * @return ArrayList<Bavard>
         * */

        return this.bavardsListenToBavardMap.get(bavard);
    }

    public ArrayList<RequestEvent> getRequestEventArrayList() {
        return requestEventArrayList;
    }

    /********************************************************************
     Methods
     ********************************************************************/

    public void BavardListenToBavard(Bavard bavardListened, Bavard bavardListener){
        /**
         * add to bavardListened a new listener "bavardListener" in the hashmap BavardsListenToBavardMap
         * @param bavardListened : bavard Listened by bavardListener
         * @param bavardListener : the bavard who wants to listen to bavardListened*/
        this.bavardsListenToBavardMap.get(bavardListened).add(bavardListener);
    }

    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * receives the message sent and then send it to the bavard who listens to the bavard who sent this message
         * @param pe: the PapotageEvent received
         * */
        Bavard bavardSender = (Bavard)pe.getSource();
        if(this.bavardsListenToBavardMap.containsKey(bavardSender)){
            for (Bavard bavardListener : this.bavardsListenToBavardMap.get(bavardSender)){
                bavardListener.receiveMessages(pe);
            }
        }else{
            System.out.println("there is no bavardListener for this bavard");
        }
    }

    public boolean createBavard(String login, String password){
        /**
         * create a new Bavard and add him to the HashMap bavardsListenToBavardMap
         * @Param login: login of the Bavard we want to create
         * @return : true if the bavard has succesfully been created else return false
         * */
        // we go through the keys of bavardsListenToBavardMap which are bavards and check if there is one who has the same login as the login in parameters
        for (Bavard bavard : this.bavardsListenToBavardMap.keySet()){
            if(bavard.getLogin().equals(login)){
                return false;
            }
        }
        this.bavardsListenToBavardMap.put(new Bavard(login, password, this), new ArrayList<Bavard>());
        return true;
    }

    public void alertBavardConnectedDisconnected(PapotageEvent bavardConnectedDiscconnectedEvent){
        /**
         * Alerte all the bavard that someone has just signed out or signed up
         * @param bavardConnectedDiscconnectedEvent: OnlineOfflineBavardEvent
         * */
        Bavard bavardConnectedDisconnected = (Bavard) bavardConnectedDiscconnectedEvent.getSource();
        for (Bavard bavard : this.bavardsListenToBavardMap.keySet()){
            if(bavard != bavardConnectedDisconnected){
                bavard.receiveMessages(bavardConnectedDiscconnectedEvent);
            }
        }
    }

    public void receiveRequest(RequestEvent request){
        /**
         * add the request he have just received to the arrayList requestEventArrayList
         * @param request: RequestEvent
         * */
        this.requestEventArrayList.add(request);
        if(this.conciergeFrame!=null){
            this.conciergeFrame.refreshRequestList(); // we refresh the request list in his frame
        }
    }

    public void alertRequestApproved(RequestEvent re) {
        /**
         * Alert the bavard concerned (requester) that his request has been approved
         * @Param RequestEvent : re
         * */

        ConciergeEvent conciergeEvent = new ConciergeEvent(this,re, true);
        Bavard bavardRequester = (Bavard)re.getSource();
        bavardRequester.receiveMessages(conciergeEvent);
    }

    public  void alertRequestDismissed(RequestEvent re){
        /**
         * Alert the bavard concerned (requester) that his request has been dismissed
         */
        ConciergeEvent conciergeEvent = new ConciergeEvent(this,re, false);
        Bavard bavardRequester = (Bavard)re.getSource();
        bavardRequester.receiveMessages(conciergeEvent);
    }

}



