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
    //private ArrayList<PapotageListener> papotageListeners;
    private HashMap<Bavard, ArrayList<Bavard>> BavardsListenToBavardMap;
    // keys are bavards and the value is an array of all the bavard who listens to each bavard

    public Concierge(String login, String password){
        this.login = login;
        this.password = password;
        //this.papotageListeners = new ArrayList<PapotageListener>();
        this.BavardsListenToBavardMap = new HashMap<Bavard, ArrayList<Bavard>>();
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
        return BavardsListenToBavardMap;
    }

    /********************************************************************
     Methods
     ********************************************************************/

    public void BavardListenToBavard(Bavard bavardListened, Bavard bavardListener){
        /**
         * add to bavardListened a new listener "bavardListener" in the hashmap BavardsListenToBavardMap
         * @param bavardListened : bavard Listened by bavardListener
         * @param bavardListener : the bavard who wants to listen to bavardListened*/

        if (this.BavardsListenToBavardMap.containsKey(bavardListened)){ // check is the bavard is already present in the hashmap
            this.BavardsListenToBavardMap.get(bavardListened).add(bavardListener);
        }
        else{ // if not create a new place in it
            this.BavardsListenToBavardMap.put(bavardListened, new ArrayList<Bavard>(Arrays.asList(bavardListener)));
        }
    }

    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * receives the message sent and then send it to the addressees
         * @param pe: the PapotageEvent received*/
        Bavard bavardSender = (Bavard)pe.getSource();
        if(this.BavardsListenToBavardMap.containsKey(bavardSender)){
            for (Bavard bavardListener : this.BavardsListenToBavardMap.get(bavardSender)){
                bavardListener.receiveMessages(pe);
            }
        }else{
            System.out.println("there is no bavardListener for this bavard");
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
        //this.papotageListeners.add(pl);
    }
}



