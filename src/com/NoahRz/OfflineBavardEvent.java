package com.NoahRz;//ok

public class OfflineBavardEvent extends PapotageEvent implements OnlineOfflineBavardEvent {
    /**
     * Event trigged when a bavard sign out
     * */

    /********************************************************************
     Constructor
     ********************************************************************/
    public OfflineBavardEvent(Object source, Message message) {
        super(source, message);
    }
}
