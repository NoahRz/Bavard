package com.NoahRz;

import java.util.ArrayList;

public class Bavard implements PapotageListener {
    private  String login;
    private PapotageListener concierge;
    private String password;

    public Bavard(String login, String password, PapotageListener concierge){
        this.login = login;
        this.concierge = concierge;
        this.password = password;
    }

    public Message createMessage(String subject, String body, ArrayList<String> addressees){
        /**
         * return the message created by the Bavard
         * @Param subject: the subject of the message
         * @Param body : the body of the message
         * @Param addresses : ArrayList of addressees*/
        return new Message(subject, body, addressees);
    }

    public void sendMessages(Message messageCreated){
        /**
         * send the message to the concierge with the addresses and then the concierge will send it to the addressees
         * @Param messageCreated : the message we've just created and we want to send
         * */
        PapotageEvent pe = new PapotageEvent(this, messageCreated); // source it's this object
        concierge.receiveMessages(pe); //message sent
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public void receiveMessages(PapotageEvent pe) {
        /**
         * receive the message
         * @Param pe: PapotageEvent with message*/
        System.out.println(this.login);
        System.out.println(pe.getSource());
        System.out.println(pe.getMessages().getSubject());
    }

    @Override
    public String getLogin(){
        return login;
    }

    public void addPapotageListener(PapotageListener pl){
        /**
         * add a PapotageListener
         * @param pl : the PapotageListener*/
        this.concierge = pl;
    }
}



