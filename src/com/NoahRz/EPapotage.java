package com.NoahRz;

import java.util.ArrayList;
import java.util.Arrays;

public class EPapotage {

    /*les messages sont d'abord envoyés au concierge et le concierge le renvoie aux destinataires*/
    public static void main(String[] args) {
        Concierge concierge = new Concierge("Le concierge", "123"); /*default concierge*/

        /*Bavard a = new Bavard("tom", "aaa",concierge);
        Bavard b = new Bavard("bob", "aaa",concierge);
        Bavard c = new Bavard("baba", "aaa",concierge);*/

        concierge.createBavard("paul", "aaa");
        concierge.createBavard("noah", "aaa");
        concierge.createBavard("baba", "aaa");

        Bavard a = concierge.getBavard("paul");
        Bavard b = concierge.getBavard("noah");

        concierge.BavardListenToBavard(a,b);
        //concierge.BavardListenToBavard(a,c);

        /*concierge.createBavard("bob", "aaa");
        concierge.createBavard("tom", "aaa");
        concierge.createBavard("baba", "aaa");*/

        //Message message = new Message("un sujet", "un contenu");

        //a.sendMessages(message);

        new SignInFrame("Connexion", concierge);

    }
}



