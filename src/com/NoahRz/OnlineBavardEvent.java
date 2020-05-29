package com.NoahRz;//ok

public class OnlineBavardEvent extends PapotageEvent implements OnlineOfflineBavardEvent {
    /**
     * Event trigged when a bavard signs in
     * */

    /********************************************************************
     Constructor
     ********************************************************************/
    public OnlineBavardEvent(Object source, String messageBody) {
        super(source);
        this.messageBody = messageBody;
    }
}
