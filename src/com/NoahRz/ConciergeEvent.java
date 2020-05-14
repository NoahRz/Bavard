package com.NoahRz;

import java.util.EventObject;

public class ConciergeEvent extends PapotageEvent {
    /**
     * this class represents messages/notifications sent by the concierge
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */

    private RequestEvent requestEvent;
    private Boolean approved;

    /********************************************************************
     Constructor
     ********************************************************************/

    public ConciergeEvent(Object source, RequestEvent requestEvent, Boolean approved) {
        super(source);
        this.requestEvent=requestEvent;
        this.approved=approved;
        this.setMessage();
    }

    /********************************************************************
     Setter
     ********************************************************************/

    public void setMessage() {
        /**
         * set the right message according to the request and the status of the request (approved or not)
         * */
        if(approved){
            if(requestEvent.getRequest().equals("add")){
                this.messageBody = " Request approved : You are now listening to "+ requestEvent.getBavardSubject().getLogin(); // the bavard wants to listen to another bavard and his request has been approved
            }else{// it's requestEvent.getRequest().equals("remove"))
                this.messageBody = " Request approved : You are not listening to "+ requestEvent.getBavardSubject().getLogin() + " anymore"; // the bavard wants to stop listening to another bavard and his request has been approved
            }
        }
        else{
            if(requestEvent.getRequest().equals("add")){
                this.messageBody = " Request rejected : You are not listening to "+ requestEvent.getBavardSubject().getLogin(); // the bavard wants to listen to another bavard but his request has been rejected
            }else{// it's requestEvent.getRequest().equals("remove"))
                this.messageBody = " Request rejected : You are still listening to "+ requestEvent.getBavardSubject().getLogin(); // the bavard wants to stop listening to another bavard but his request has been rejected
            }
        }
    }
}
