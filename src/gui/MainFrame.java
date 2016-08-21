package gui;

import constants.Constants;
import io.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.ref.WeakReference;

/**
 * Main frame that is top level that contains
 * the panels responsible for allowing
 * user interaction with this secure chat application.
 */
public class MainFrame extends JFrame{

    private CardLayout cardLayout = new CardLayout();
    private final JPanel panel;
    private Controller controller;
    private final UserDialog userDialog;

    public MainFrame(){
        ConnectionPanel connectionPanel = new ConnectionPanel(this);
        userDialog = new UserDialog(this);
        this.panel = new JPanel(cardLayout);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

        panel.add(connectionPanel, Constants.CONNECT_PANEL);
        panel.add(userDialog, Constants.USER_PANEL);
        panel.setVisible(true);

        this.add(panel);
        addWindowClosingAction();

        this.setVisible(true);
    }

    void showPanel(String panelName){
        cardLayout.show(panel, panelName);
    }

    void assignController(byte[] userKey, byte[] IV){
        this.controller = new Controller(userKey, IV, this);
    }

    Controller getController(){
        return this.controller;
    }

    public UserDialog getUserDialog(){
        return this.userDialog;
    }

    private void addWindowClosingAction(){
        this.addWindowListener(new FrameExitAdapter(this));
    }

    private static class FrameExitAdapter extends WindowAdapter{
        private final WeakReference<MainFrame> wFrame;
        FrameExitAdapter(MainFrame frame){
            this.wFrame = new WeakReference<>(frame);
        }

        @Override
        public void windowClosing(WindowEvent e){
            MainFrame frame = this.wFrame.get();
            if(frame == null){
                return;
            }
            frame.getController().shutdown();
        }
    }
}
