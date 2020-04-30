package com.NoahRz;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public interface PapotageListener extends EventListener {
    public void receiveMessages(PapotageEvent pe);

    public String getLogin();

    public void sendMessages(Message messageCreated);

    public String getPassword();

    //public HashMap<PapotageListener, ArrayList<PapotageEvent>> getRecentDiscussion();
}



