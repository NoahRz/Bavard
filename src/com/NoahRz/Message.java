package com.NoahRz;

import java.util.ArrayList;

public class Message {
    /*messages created by a user (Bavard our concierge */
    private String subject;
    private String body;
    private ArrayList<String> addressees;


    public Message(String subject, String body, ArrayList<String> addressees){
        this.subject = subject;
        this.body =body;
        this.addressees = addressees;
    }

    public String getSubject(){
        return subject;
    }

    public ArrayList<String> getAddressees() {
        return addressees;
    }

    public String getBody() {
        return body;
    }
}



