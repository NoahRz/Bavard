package com.NoahRz;//ok

import java.util.EventObject;

public class RequestEvent extends EventObject {
    /**
     * RequestEvent for the request bavard send to the concierge
     *
     * @param source the object on which the Event initially occurred
     * @param request: string corresponding to the type of the request ("add" or "remove")
     * @param bavardSubject: the bavard on which we apply the request (ex: source wants to add bavardSubject to his listening
     * @throws IllegalArgumentException if source is null
     */
    private String request;
    private Bavard bavardSubject;

    /********************************************************************
     Constructor
     ********************************************************************/
    public RequestEvent(Object source, String request, Bavard bavardSubject) {
        super(source);
        this.request=request;
        this.bavardSubject=bavardSubject;
    }

    /********************************************************************
     Getter
     ********************************************************************/
    public String getRequest() {
        return request;
    }

    public Bavard getBavardSubject() {
        return bavardSubject;
    }
}
