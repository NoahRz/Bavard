package com.NoahRz;//ok

import java.util.EventListener;


public interface PapotageListener extends EventListener {

    public void receiveMessages(PapotageEvent pe);

    public String getLogin();

    public String getPassword();
}



