package com.NoahRz;

import java.util.EventListener;

public interface PapotageListener extends EventListener {
    public void receiveMessages(PapotageEvent pe);

    public String getName();

    public void sendMessages(Message messageCreated);

    public String getPassword();
}



