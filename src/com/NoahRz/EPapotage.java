package com.NoahRz; //ok

public class EPapotage {
    /**
     * Main class
     * */
    public static void main(String[] args) {
        Concierge concierge = new Concierge("Le concierge", "123"); //default concierge

        concierge.createBavard("paul", "aaa");
        concierge.createBavard("noah", "aaa");
        concierge.createBavard("baba", "aaa");

        Bavard a = concierge.getBavard("paul");
        Bavard b = concierge.getBavard("noah");

        concierge.BavardListenToBavard(a,b); // bavard b listens to bavard a

        new SignInFrame("Connexion", concierge);

    }
}



