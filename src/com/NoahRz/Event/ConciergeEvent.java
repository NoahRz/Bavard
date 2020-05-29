package com.NoahRz.Event;

public class ConciergeEvent extends PapotageEvent {
    /**
     * this class represents messages/notifications sent by the concierge
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */

    private RequestEvent requestEvent; //the requestEvent linked to the notification (the conciergeEvent)
    private Boolean approved; //if it's true, that means the request has been approved, else the request has not been approved

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
            this.messageSubject = "Request approved ";
            if(requestEvent.getRequest().equals("add")){
                this.messageBody = " You are now listening to "+ requestEvent.getBavardSubject().getLogin(); // the bavard wants to listen to another bavard and his request has been approved
            }else{// it's requestEvent.getRequest().equals("remove"))
                this.messageBody = " You are not listening to "+ requestEvent.getBavardSubject().getLogin() + " anymore"; // the bavard wants to stop listening to another bavard and his request has been approved
            }
        }
        else{
            this.messageSubject = "Request rejected ";
            if(requestEvent.getRequest().equals("add")){
                this.messageBody = " You are not listening to "+ requestEvent.getBavardSubject().getLogin(); // the bavard wants to listen to another bavard but his request has been rejected
            }else{// it's requestEvent.getRequest().equals("remove"))
                this.messageBody = " You are still listening to "+ requestEvent.getBavardSubject().getLogin(); // the bavard wants to stop listening to another bavard but his request has been rejected
            }
        }
    }
}
