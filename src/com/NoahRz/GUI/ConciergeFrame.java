package com.NoahRz.GUI;

import com.NoahRz.*;
import com.NoahRz.Event.RequestEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConciergeFrame extends JFrame implements ActionListener {
    /**
     * JFrame where the concierge can create new bavard, adjust bavard's listening (who listens who) and manage request he receives
     * from bavard asking to listen other bavard
     * */
    private Concierge concierge;
    private JPanel requestPanel;

    public ConciergeFrame(Concierge concierge){
        this.concierge=concierge;
        concierge.setFrame(this);

        this.setTitle("Concierge Frame");
        this.setSize(500,250);
        this.setLayout(new GridLayout(1,2));
        JPanel pane = new JPanel();
        pane.setBackground(EPapotage.myYellowColor);
        this.setContentPane(pane);
        this.setLayout(new GridLayout(3,1));

        /*-- Menu --*/
        JMenuBar menubar= new JMenuBar();
        JMenu optionMenu = new JMenu("Option");
        JMenuItem bavardMenu=new JMenuItem(concierge.getLogin());
        JMenuItem createBavardMenu = new JMenuItem("Create bavard");
        JMenuItem adjustListeningMenu = new JMenuItem("Adjust listening");
        JMenuItem addThemeMenu = new JMenuItem("Add theme");
        JMenuItem signOutMenu = new JMenuItem("Sign out");

        createBavardMenu.setActionCommand("Create bavard");
        createBavardMenu.addActionListener(this);

        adjustListeningMenu.setActionCommand("Adjust listening");
        adjustListeningMenu.addActionListener(this);

        addThemeMenu.setActionCommand("Add theme");
        addThemeMenu.addActionListener(this);

        signOutMenu.setActionCommand("Sign out");
        signOutMenu.addActionListener(this);

        optionMenu.add(createBavardMenu);
        optionMenu.add(adjustListeningMenu);
        optionMenu.add(addThemeMenu);
        optionMenu.add(signOutMenu);

        menubar.add(bavardMenu);
        menubar.add(optionMenu);

        this.setJMenuBar(menubar);

        /*-- Panel where we display a list of bavard registered in the system --*/
        JPanel bavardListPanel = new JPanel();
        bavardListPanel.setLayout(new BoxLayout(bavardListPanel, BoxLayout.X_AXIS));

        JLabel bavardListTitleLabel = new JLabel("Bavard: ");
        Font fontForBavardListTitleLabel =new Font(bavardListTitleLabel.getFont().getName(),Font.BOLD,bavardListTitleLabel.getFont().getSize()); //add some style to bavardListTitleLabel
        bavardListTitleLabel.setFont(fontForBavardListTitleLabel);
        bavardListPanel.add(bavardListTitleLabel);

        //look through bavard registered in the system
        for(Bavard bavard : concierge.getBavardsListenToBavardMap().keySet()){
            JLabel bavardLoginLabel = new JLabel(bavard.getLogin());
            bavardListPanel.add(Box.createRigidArea(new Dimension(5, 0))); // add some space between bavardLoginLabel
            bavardListPanel.add(bavardLoginLabel);
        }

        JScrollPane bavardListScrollPane = new JScrollPane(bavardListPanel); // we make the bavardListPanel scrollable

        pane.add(bavardListScrollPane);
        /*-- Panel where we display a list of theme in the system --*/
        JPanel themeListPanel = new JPanel();
        themeListPanel.setLayout(new BoxLayout(themeListPanel, BoxLayout.X_AXIS));

        JLabel themeListTitleLabel = new JLabel("Theme: ");
        Font fontForThemeListTitleLabel =new Font(themeListTitleLabel.getFont().getName(),Font.BOLD,themeListTitleLabel.getFont().getSize()); //add some style to themeListTitleLabel
        themeListTitleLabel.setFont(fontForThemeListTitleLabel);
        themeListPanel.add(themeListTitleLabel);

        //look through theme  in the system
        for(String theme : concierge.getThemes()){
            JLabel themeLabel = new JLabel(theme);
            themeListPanel.add(Box.createRigidArea(new Dimension(5, 0))); // add some space between themeLabel
            themeListPanel.add(themeLabel);
        }

        JScrollPane themeListScrollPane = new JScrollPane(themeListPanel); // we make the themeListPanel scrollable

        pane.add(themeListScrollPane);

        /*-- Panel where we display request the concierge receives --*/
        requestPanel = new JPanel();
        requestPanel.setBackground(EPapotage.myYellowColor);
        requestPanel.setSize(new Dimension(this.getWidth(), this.getHeight()/4));
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS)); //panel where who gathers all the request the concierge receives

        this.refreshRequestList(); // we refresh the request list

        JScrollPane requestPanelScrollPane = new JScrollPane(requestPanel); // we make requestPanel scrollable
        pane.add(requestPanelScrollPane);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //so that we can't close the frame by clicking on "X", if we want to close we have to click on "sign out"
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
        if(e.getActionCommand().equals("Add theme")){ //if we clicked "adjust listening" in the menu bar
            new AddThemeFrame(concierge);
            this.dispose();
        }

        if(e.getActionCommand().equals("Sign out")){ //if we clicked "sign out" in the menu bar
            this.concierge.setConnected(false);
            this.dispose();
        }
    }

    public void refreshRequestList(){
        /**
         * refresh the panel requestPanel which contains list of request
         * */

        requestPanel.removeAll();
        requestPanel.revalidate();
        requestPanel.repaint();

        ArrayList<RequestEvent> requestEventsToRemove = new ArrayList<RequestEvent>();
        for (RequestEvent re : concierge.getRequestEventArrayList()) {
            Bavard bavardRequester = (Bavard) re.getSource();
            ArrayList<Bavard> bavardListeners = this.concierge.getBavardListenersOfBavard(re.getBavardSubject()); // listeners of the bavard subject of the request
            if ((re.getRequest().equals("add") && bavardListeners.contains(bavardRequester)) || (re.getRequest().equals("remove") && !bavardListeners.contains(bavardRequester))) {
                /*
                we check that the request is still making sense : ex if we want to add a new bavard to the listeners of the bavardSubject, we have to make sure that the bavard is
                not already among bavardSubject's listeners. Same thing if we want to remove a bavard from the listeners of the bavardSubject, we have to make sure that the bavard
                is  among the bavardSubject's listeners.
                this case can occur, if the concierge receives a new request but instead of directly managing it, the concierge goes to the AdjustBavardListenerFrame and do it manually
                */
                requestEventsToRemove.add(re);
            }
        }

        concierge.getRequestEventArrayList().removeAll(requestEventsToRemove);

        if(concierge.getRequestEventArrayList().size() == 0){ // if there is no request
            JLabel noRequestLabel = new JLabel("There is no request.");
            requestPanel.add(noRequestLabel);
        }
        else{

            for (RequestEvent re : concierge.getRequestEventArrayList()) { // we display the requests
                Bavard bavardRequester = (Bavard) re.getSource();
                JPanel oneRequestPanel = new JPanel(); //panel which gathers the request label, an approve button and an dismiss button
                oneRequestPanel.setLayout(new BorderLayout());

                String preposition;
                if (re.getRequest().equals("add")) {
                    preposition = " to his listening";
                } else { //it equals "remove"
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
                            concierge.getRequestEventArrayList().remove(re); //we remove the request we have just handled
                            concierge.alertRequestApproved(re); // concierge alerts the bavard who sent the request that is request has been approved
                            requestPanel.remove(oneRequestPanel);
                            requestPanel.revalidate();
                            requestPanel.repaint();
                        }
                        if (re.getRequest().equals("remove")) {
                            concierge.getBavardListenersOfBavard(re.getBavardSubject()).remove(bavardRequester);
                            concierge.getRequestEventArrayList().remove(re); //we remove the request we have just handled
                            concierge.alertRequestApproved(re); // concierge alerts the bavard who sent the request that is request has been approved
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
                        concierge.getRequestEventArrayList().remove(re); //we remove the request we have just handled
                        concierge.alertRequestDismissed(re); // concierge alerts the bavard who sent the request that is request has been dismissed
                        requestPanel.revalidate();
                        requestPanel.repaint();
                    }
                });
                requestPanel.add(oneRequestPanel);
            }
        }
    }
}
