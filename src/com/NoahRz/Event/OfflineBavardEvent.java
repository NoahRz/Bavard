package com.NoahRz.Event;

public class OfflineBavardEvent extends PapotageEvent implements OnlineOfflineBavardEvent {
    /**
     * Event trigged when a bavard signs out
     * */

    /********************************************************************
     Constructor
     ********************************************************************/
    public OfflineBavardEvent(Object source, String messageBody) {
        super(source);
        this.messageBody=messageBody;
    }
}
