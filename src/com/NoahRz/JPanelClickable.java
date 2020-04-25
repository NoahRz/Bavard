package com.NoahRz;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JPanelClickable extends JPanel implements MouseListener {
    private Boolean isClicked;
    public JPanelClickable (){
        super();
        this.isClicked = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.isClicked=true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
