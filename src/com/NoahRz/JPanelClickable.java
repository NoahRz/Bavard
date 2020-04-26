package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class JPanelClickable extends JPanel{
    private Boolean isClicked;
    private int id;
    public JPanelClickable (int id){
        super();
        this.id=id;
        this.isClicked = false;
        this.setBackground(new Color(217,217,217));
    }

    public Boolean isClicked(){
        return this.isClicked;
    }

    public void setIsClicked(Boolean bool){
        this.isClicked=bool;
    }

    public void clicked(){
        this.setBackground(new Color(140,140,140));
    }
}
