package com.NoahRz;

import java.util.EventObject;

public class RequestEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */

    private String request;
    private Bavard bavardSubject;

    public RequestEvent(Object source, String request, Bavard bavardSubject) {
        super(source);
        this.request=request;
        this.bavardSubject=bavardSubject;
    }

    public String getRequest() {
        return request;
    }

    public Bavard getBavardSubject() {
        return bavardSubject;
    }
}
