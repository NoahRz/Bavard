package com.NoahRz;

import com.NoahRz.Event.PapotageEvent;

import java.util.ArrayList;
import java.util.EventListener;


public interface PapotageListener extends EventListener {
    /**
     * interface which listens to PapotageEvent
     *
     */

    public void receiveMessages(PapotageEvent pe);

    public String getLogin();

    public String getPassword();

    public ArrayList<String> getThemes();

    public void setThemes(ArrayList<String> themes);
}



