package com.NoahRz;//ok

public class Message {
    /**
     * Message sent by bavards
     * */
    private String subject;
    private String body;

    /********************************************************************
     Constructor
     ********************************************************************/
    public Message(String subject, String body){
        this.subject = subject;
        this.body =body;
    }

    /********************************************************************
     Getter
     ********************************************************************/
    public String getSubject(){
        return subject;
    }

    public String getBody() {
        return body;
    }
}



