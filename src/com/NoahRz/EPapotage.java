package com.NoahRz;

import com.NoahRz.GUI.SignInFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EPapotage {
    /**
     * Main class
     * */

    public final static Color myYellowColor = new Color(255, 255, 102);

    public static void main(String[] args) {
        Concierge concierge = new Concierge("Le concierge", "123"); //default concierge

        concierge.setThemes(new ArrayList<String>(Arrays.asList("sport", "cinema", "actualite", "politique", "ecologie")));

        concierge.createBavard("augustin", "aaa");
        concierge.getBavard("augustin").setThemes(new ArrayList<String>(Arrays.asList("sport", "cinema")));

        concierge.createBavard("marin", "aaa");
        concierge.createBavard("henri", "aaa");
        concierge.createBavard("hadrien", "aaa");
        concierge.createBavard("louis", "aaa");
        concierge.createBavard("pierre-Louis", "aaa");
        concierge.createBavard("michel", "aaa");
        concierge.createBavard("victor", "aaa");
        concierge.createBavard("pierre-Antoine", "aaa");
        concierge.createBavard("vivien", "aaa");
        concierge.createBavard("fLorentin", "aaa");


        new SignInFrame("Connexion", concierge);

    }
}



