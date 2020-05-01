package com.NoahRz;

import java.util.ArrayList;

public class Message {
    /*messages created by a user (Bavard our concierge */
    private String subject;
    private String body;


    public Message(String subject, String body){
        this.subject = subject;
        this.body =body;
    }

    public String getSubject(){
        return subject;
    }

    public String getBody() {
        return body;
    }
}



