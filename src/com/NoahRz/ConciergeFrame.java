package com.NoahRz; //ok

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ConciergeFrame extends JFrame implements ActionListener {
    /**
     * JFrame where the concierge can create new bavard, adjust bavard's listening (who listens who) and manage request he receives
     * from bavard asking to listen other bavard
     * */
    private Concierge concierge;

    public ConciergeFrame(Concierge concierge){
        this.concierge=concierge;

        this.setTitle("Concierge Frame");
        this.setSize(500,100);
        this.setLayout(new GridLayout(1,2));
        JPanel pane = new JPanel();
        pane.setBackground(Color.YELLOW);
        this.setContentPane(pane);
        this.setLayout(new GridLayout(1,2));

        /*-- Menu --*/
        JMenuBar menubar= new JMenuBar();
        JMenu optionMenu = new JMenu("Option");
        JMenuItem bavardMenu=new JMenuItem(concierge.getLogin());
        JMenuItem createBavardMenu = new JMenuItem("Create bavard");
        JMenuItem adjustListeningMenu = new JMenuItem("Adjust listening");
        JMenuItem signOutMenu = new JMenuItem("Sign out");

        createBavardMenu.setActionCommand("Create bavard");
        createBavardMenu.addActionListener(this);

        adjustListeningMenu.setActionCommand("Adjust listening");
        adjustListeningMenu.addActionListener(this);

        signOutMenu.setActionCommand("Sign out");
        signOutMenu.addActionListener(this);

        optionMenu.add(createBavardMenu);
        optionMenu.add(adjustListeningMenu);
        optionMenu.add(signOutMenu);

        menubar.add(bavardMenu);
        menubar.add(optionMenu);

        this.setJMenuBar(menubar);

        /*-- Panel where we display request the concierge receives --*/
        JPanel requestPanel = new JPanel();
        requestPanel.setBackground(Color.YELLOW);
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS)); //panel where who gathers all the request the concierge receives

        if(concierge.getRequestEventArrayList().size() == 0){ // if there is no request
            JLabel noRequestLabel = new JLabel("There is no request.");
            requestPanel.add(noRequestLabel);
        }
        else{
            for (RequestEvent re : concierge.getRequestEventArrayList()) {
                Bavard bavardRequester = (Bavard) re.getSource();
                ArrayList<Bavard> bavardListeners = this.concierge.getBavardListenersOfBavard(re.getBavardSubject()); // listeners of the bavard subject of the request
                if ((re.getRequest().equals("add") && bavardListeners.contains(bavardRequester)) || (re.getRequest().equals("remove") && !bavardListeners.contains(bavardRequester))) {
                /*
                we check that the request is still make sense : ex if we want to add a new bavard to the listeners of the bavardSubject, we have to make sure that the bavard is
                not already among bavardSubject's listeners. Same thing if we want to remove a bavard from the listeners of the bavardSubject, we have to make sure that the bavard
                is  among the bavardSubject's listeners.
                this case can occur, if the concierge receives a new request but instead of directly manage it, the concierge goes to the AdjustBavardListenerFrame and do it manually
                */
                    System.out.println(re.getBavardSubject().getLogin());
                    concierge.getRequestEventArrayList().remove(re);
                }
            }

            for (RequestEvent re : concierge.getRequestEventArrayList()) { // we display the requests
                Bavard bavardRequester = (Bavard) re.getSource();
                JPanel oneRequestPanel = new JPanel(); //panel which gathers the request label, an approve button and an dismiss button
                    oneRequestPanel.setLayout(new BorderLayout());

                    String preposition;
                    if (re.getRequest().equals("add")) {
                        preposition = " to his listening";
                    } else { //it's "remove"
                        preposition = " from his listening";
                    }
                    JLabel requestLabel = new JLabel(bavardRequester.getLogin() + " wants to " + re.getRequest() + " " + re.getBavardSubject().getLogin() + preposition);

                    /* we add an approve button and a dismiss button next to the request so that the concierge can directly answer the request
                    without going to the AdjustBavardListenerFrame where the concierge will have to do it manually */
                    JButton approveButton = new JButton("Approve");
                    JButton dismissButton = new JButton("Dismiss");

                    oneRequestPanel.add(requestLabel, BorderLayout.WEST);
                    oneRequestPanel.add(approveButton, BorderLayout.CENTER);
                    oneRequestPanel.add(dismissButton, BorderLayout.EAST);

                    approveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) { //we approve the request
                            if (re.getRequest().equals("add")) {
                                concierge.getBavardListenersOfBavard(re.getBavardSubject()).add(bavardRequester);
                                requestPanel.remove(oneRequestPanel);
                                requestPanel.revalidate();
                                requestPanel.repaint();
                            }
                            if (re.getRequest().equals("remove")) {
                                concierge.getBavardListenersOfBavard(re.getBavardSubject()).remove(bavardRequester);
                                requestPanel.remove(oneRequestPanel);
                                requestPanel.revalidate();
                                requestPanel.repaint();
                            }
                        }
                    });
                    dismissButton.addActionListener(new ActionListener() { // we dismiss the request
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            requestPanel.remove(oneRequestPanel);
                            requestPanel.revalidate();
                            requestPanel.repaint();
                        }
                    });
                    requestPanel.add(oneRequestPanel);
            }
        }
        JScrollPane scrollPane = new JScrollPane(requestPanel);
        pane.add(scrollPane);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Create bavard")){ //if we clicked "create bavard" in the menu bar
            new CreateBavardFrame(concierge);
            this.dispose();
        }
        if(e.getActionCommand().equals("Adjust listening")){ //if we clicked "adjust listening" in the menu bar
            new AdjustBavardListenerFrame(concierge);
            this.dispose();
        }
        if(e.getActionCommand().equals("Sign out")){ //if we clicked "sign out" in the menu bar
            this.concierge.setConnected(false);
            this.dispose();
        }
    }
}
