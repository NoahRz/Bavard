package com.NoahRz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConciergeFrame extends JFrame implements ActionListener {
    private Concierge concierge;

    public ConciergeFrame(Concierge concierge){
        this.concierge=concierge;
        this.setTitle("Concierge Frame");
        this.setSize(300,100);
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

        /*-- Panel where we display request the concierge receive --*/
        JPanel requestPanel = new JPanel();
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));
        if(concierge.getRequestEventArrayList().size()==0){
            JLabel noRequestLabel = new JLabel("There is no request.");
            requestPanel.add(noRequestLabel);
        }
        else{
            for (RequestEvent re : concierge.getRequestEventArrayList()){
                JPanel oneRequestPanel = new JPanel();
                oneRequestPanel.setLayout(new BorderLayout());
                Bavard bavardRequester = (Bavard) re.getSource();
                JLabel requestLabel = new JLabel(bavardRequester.getLogin() + " wants to " + re.getRequest() + " " + re.getBavardSubject().getLogin());
                JButton approveButton = new JButton("Approve");
                JButton dismissButton = new JButton("Dismiss");

                oneRequestPanel.add(requestLabel, BorderLayout.WEST);
                oneRequestPanel.add(approveButton, BorderLayout.CENTER);
                oneRequestPanel.add(dismissButton, BorderLayout.EAST);

                approveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { //we approve the request
                        if(re.getRequest().equals("add")){
                            concierge.getBavardListenersOfBavard(re.getBavardSubject()).add(bavardRequester);
                            requestPanel.remove(oneRequestPanel);
                            requestPanel.revalidate();
                            requestPanel.repaint();
                        }
                        if(re.getRequest().equals("remove")){
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
        if(e.getActionCommand().equals("Create bavard")){
            new CreateBavardFrame(concierge);
            this.dispose();
        }
        if(e.getActionCommand().equals("Adjust listening")){
            new AdjustBavardListenerFrame(concierge);
            this.dispose();
        }
        if(e.getActionCommand().equals("Sign out")){
            this.dispose();
        }
    }
}
