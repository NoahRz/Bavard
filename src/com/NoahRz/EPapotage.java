package com.NoahRz; //ok

import java.util.ArrayList;
import java.util.Arrays;

public class EPapotage {
    /**
     * Main class
     * */
    public static void main(String[] args) {
        Concierge concierge = new Concierge("Le concierge", "123"); //default concierge

        concierge.setTheme(new ArrayList<String>(Arrays.asList("sport", "cinema", "actualite", "politique", "ecologie")));

        concierge.createBavard("augustin", "aaa");

        concierge.getBavard("augustin").setTheme(new ArrayList<String>(Arrays.asList("sport", "cinema")));

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



