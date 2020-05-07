package com.NoahRz;

import java.util.EventObject;

public class OnlineBavardEvent extends PapotageEvent {
    private Message message;
    public OnlineBavardEvent(Object source, Message message){
        super(source,message);
        Bavard BavardSource = (Bavard) source;
    }

    public Message getAlerteMessage() {
        return message;
    }
}
