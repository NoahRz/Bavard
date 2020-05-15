package com.NoahRz; //ok

public class EPapotage {
    /**
     * Main class
     * */
    public static void main(String[] args) {
        Concierge concierge = new Concierge("Le concierge", "123"); //default concierge

        concierge.createBavard("Augustin", "aaa");
        concierge.createBavard("marin", "aaa");
        concierge.createBavard("henri", "aaa");
        concierge.createBavard("hadrien", "aaa");
        concierge.createBavard("Louis", "aaa");
        concierge.createBavard("Pierre-Louis", "aaa");
        concierge.createBavard("Michel", "aaa");
        concierge.createBavard("Victor", "aaa");
        concierge.createBavard("Pierre-Antoine", "aaa");
        concierge.createBavard("Vivien", "aaa");
        concierge.createBavard("FLorentin", "aaa");


        new SignInFrame("Connexion", concierge);

    }
}



