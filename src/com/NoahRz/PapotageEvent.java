package com.NoahRz;

import java.util.EventObject;

/*je pense que PapotageEvent c'est le message
REMARQUE : On nous dis pas de creer une classe message mais je pense que c'est plus propre de faire comme ca*/

public class PapotageEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    private Message message;

    public PapotageEvent(Object source, Message message) {
        super(source);
        this.message=message;
    }


    public Message getMessages() {
        return message;
    }
}



