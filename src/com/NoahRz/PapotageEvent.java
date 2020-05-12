package com.NoahRz; //ok

import java.util.EventObject;

public class PapotageEvent extends EventObject {
    /**
     * Event trigged when a bavard send a message
     *
     * @param source:the object on which the Event initially occurred
     * @param message: message sent
     * @throws IllegalArgumentException if source is null
     */
    private Message message;

    /********************************************************************
     Constructor
     ********************************************************************/
    public PapotageEvent(Object source, Message message) {
        super(source);
        this.message=message;
    }

    /********************************************************************
     Getter
     ********************************************************************/
    protected Message getMessages() {
        return message;
    }
}



