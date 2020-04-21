package com.NoahRz;

import java.util.ArrayList;

public class Concierge implements PapotageListener {
    private String name;
    private String password;
    /**
     * this class will listen to messages sent by Bavards and send them to the reveicers (Bavards)
     * */

    private ArrayList<PapotageListener> papotageListeners;
    private int cpt; // number of bavard

    public Concierge(String name, String password){
        this.name = name;
        this.password = password;
        this.papotageListeners = new ArrayList<PapotageListener>();
        this.cpt=0;
    }

    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * receives the message sent and then send it to the addressees
         * @param pe: the PapotageEvent received*/
        System.out.println(this.name);
        System.out.println(pe.getSource());
        System.out.println(pe.getMessages().getSubject());
        for (String addressee : pe.getMessages().getAddressees()){
            PapotageListener pl = this.getPapotageListener(addressee);
            pl.receiveMessages(pe);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void sendMessages(Message messageCreated) {

    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public PapotageListener getPapotageListener(String addressee) {
        /**
         * return the PapotageListener having the name addressee
         * @Param addressee : name of the Papotage we are looking for*/
        for(PapotageListener pl : this.papotageListeners){
            if(pl.getName().equals(addressee)){
                return pl;
            }
        }
        return null;
    }

    public void createBavard(String name){
     /**
     * create a new Bavard and add him to the list papotageListeners
     * @Param name: name of the Bavard we want to create*/
        this.addPapotageListeners(new Bavard(name, "aaa",this));
        this.cpt++;
    }

    private void addPapotageListeners(PapotageListener pl) {
        /**
         * add a PapotageListeners to the list papotageListeners
         * @Param pl : PapotageListener we ant to add*/
        this.papotageListeners.add(pl);
    }
}



