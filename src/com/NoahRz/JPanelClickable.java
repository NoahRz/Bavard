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
        this.setIsClicked(false);
    }

    public Boolean isClicked(){
        return this.isClicked;
    }

    public void setIsClicked(Boolean bool){
        /**
         * set isClicked attribut to bool and set the background color corresponding to the case
         * @param bool: boolean, is clicked or not*/
        this.isClicked=bool;
        if (bool){
            this.setBackground(new Color(140,140,140));} // grey
        else{
            this.setBackground(new Color(217,217,217)); //lighter grey
        }
    }
}
