package gui;

import constants.Constants;
import io.PayloadMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;

/**
 * User panel to allow user to type messages to
 * another connected client.
 */
public class UserDialog extends JPanel{
    /**Constraints for placing components within this panel.*/
    private final GridBagConstraints constraints = new GridBagConstraints();

    /**Reference to the parent frame who created this panel.*/
    private final MainFrame frame;

    private JTextArea conversationArea;

    private JTextField userData;

    UserDialog(MainFrame frame){
        this.frame = frame;this.setSize(Constants.CONNECT_WIDTH, Constants.CONNECT_HEIGHT);
        this.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        setUpConversationArea();
        setUpUserSentData();
        this.setVisible(true);
    }

    private void setUpConversationArea(){
        conversationArea = new JTextArea(Constants.CONVERSATION_AREA_WELCOME);
        conversationArea.setEditable(false);
        ScrollPane conversationScrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        conversationScrollPane.setSize(Constants.CONVERSATION_WIDTH, Constants.CONVERSATION_HEIGHT);
        conversationScrollPane.add(conversationArea);
        this.add(conversationScrollPane, constraints);
        constraints.gridy++;
    }

    private void setUpUserSentData(){
        userData = new JTextField(20);
        this.add(userData, constraints);
        constraints.gridy++;
        userData.addActionListener(new UserDataActionListener(this));
    }

    private void sendData(){
        PayloadMessage payload = new PayloadMessage(userData.getText(),Constants.DATA_MESSAGE);
        this.frame.getController().frameOutgoingMessage(payload);
        clearField();
    }

    public void addDataToConversation(String newData){
        conversationArea.append(newData);
    }

    private void clearField(){
        userData.setText("");
    }

    private static class UserDataActionListener implements ActionListener{
        private final WeakReference<UserDialog> wUserDialog;
        UserDataActionListener(UserDialog userDialog){
            this.wUserDialog = new WeakReference<>(userDialog);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            UserDialog userDialog = this.wUserDialog.get();
            if(userDialog == null){
                return;
            }
            userDialog.sendData();
        }
    }
}
