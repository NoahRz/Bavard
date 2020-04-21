package com.NoahRz;

import java.util.ArrayList;
import java.util.Arrays;

public class EPapotage {

    /*les messages sont d'abord envoyés au concierge et le concierge le renvoie aux destinataires*/
    public static void main(String[] args) {
        Concierge concierge = new Concierge("Le concierge", "123"); /*default concierge*/
        new SignInFrame("Connexion", concierge);
        concierge.createBavard("bob");
        concierge.createBavard("tom");
        concierge.createBavard("baba");

        Message message = new Message("a", "b", new ArrayList<>(Arrays.asList("tom")));
        concierge.getPapotageListener("bob").sendMessages(message);

    }
}



