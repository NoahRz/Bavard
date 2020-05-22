package com.NoahRz; //ok

import java.util.ArrayList;
import java.util.EventObject;

public class PapotageEvent extends EventObject {
    /**
     * Event trigged when a bavard send a message
     *
     * @param source:the object on which the Event initially occurred
     * @param message: message sent
     * @throws IllegalArgumentException if source is null
     */
    //private Message message;
    protected String messageSubject; //used by ConciergeEvent
    protected String messageBody;
    private ArrayList<String> messageThemes;

    /********************************************************************
     Constructor
     ********************************************************************/
    public PapotageEvent(Object source){
        super(source);
    }

    public PapotageEvent(Object source, ArrayList<String> messageThemes,String messageSubject, String messageBody) {
        super(source);
        //this.message=message;
        this.messageThemes=messageThemes;
        this.messageSubject = messageSubject;
        this.messageBody = messageBody;
    }

    /********************************************************************
     Getter
     ********************************************************************/

    public String getMessageSubject() {
        return messageSubject;
    }

    protected String getMessageBody() {
        return messageBody;
    }

    public ArrayList<String> getMessageThemes() {
        return messageThemes;
    }
}



