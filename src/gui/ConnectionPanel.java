package gui;

import connection.ConnectionManager;
import constants.Constants;
import encryption.KeyGen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;

/**
 * Connection panel for establishing a new connection.
 */
public class ConnectionPanel extends JPanel{
    /**Constraints for placing components within this panel.*/
    private final GridBagConstraints constraints = new GridBagConstraints();

    /*Text fields for user input.*/
    private JTextField inBoundPortField;
    private JTextField outBoundPortField;
    private JTextField ipAddressField;
    private JTextField encryptionKeyField;

    /**Assign new values to Connection Manager per user input.*/
    private JButton doneButton;
    /**Reference to the parent frame who created this panel.*/
    private final MainFrame frame;

    /**
     * Create a new Connection Panel for the user
     * to add the ports and ip address of the other client.
     * @param frame reference to the main frame for toggling panels.
     */
    public ConnectionPanel(MainFrame frame){
        this.frame = frame;
        this.setSize(Constants.CONNECT_WIDTH, Constants.CONNECT_HEIGHT);
        this.setLayout(new GridBagLayout());
        constraints.gridx = 0;
        constraints.gridy = 0;
        setUpPortInput();
        setUpIPInput();
        setUpUserPasswordInput();
        setUpDoneButton();
        setDefaults();
        this.setVisible(true);
    }

    /**
     * Create labels and text fields for
     * inbound and outbound ports.
     */
    private void setUpPortInput(){
        /*Initialize the fields.*/
        JLabel inBoundPortLabel = new JLabel(Constants.IN_BOUND_PORT_LABEL);
        JLabel outBoundPortLabel = new JLabel(Constants.OUT_BOUND_PORT_LABEL);
        inBoundPortField = new JTextField(20);
        outBoundPortField = new JTextField(20);
        /*Grid X = 0, Grid Y = 0*/
        this.add(inBoundPortLabel, constraints);
        constraints.gridx++;
        this.add(inBoundPortField, constraints);
        constraints.gridy++;
        /*Grid X = 1, Grid Y = 1*/
        constraints.gridx = 0;  //reset gridX
        this.add(outBoundPortLabel, constraints);
        constraints.gridx++;
        this.add(outBoundPortField, constraints);
        constraints.gridy++;
        /*Reset Grid X for IP*/
        constraints.gridx = 0;
    }

    /**
     * Create label and text field for ip address.
     */
    private void setUpIPInput(){
        JLabel ipAddressLabel = new JLabel(Constants.IP_ADDRESS_LABEL);
        ipAddressField = new JTextField(20);
        /*Grid X = 0, Grid Y = 2*/
        this.add(ipAddressLabel, constraints);
        constraints.gridx++;
        this.add(ipAddressField, constraints);
        constraints.gridy++;
        /*Reset Grid X*/
        constraints.gridx = 0;
    }

    /**
     * Create done button and assign action listener.
     */
    private void setUpDoneButton(){
        doneButton = new JButton(Constants.DONE);
        this.add(doneButton, constraints);
        constraints.gridy++;
        assignDoneListener();
    }

    /**
     * Assign done button action listener to the done button.
     */
    private void assignDoneListener(){
        doneButton.addActionListener(new DoneActionListener(this));
    }

    /**
     * Action listener for querying the text fields for
     * ip address and port numbers. Assign the port numbers
     * and ip address to the Connection Manager.
     */
    private static class DoneActionListener implements ActionListener{
        private final WeakReference<ConnectionPanel> wConnectionPanel;

        DoneActionListener(ConnectionPanel connectionPanel){
            this.wConnectionPanel = new WeakReference<>(connectionPanel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ConnectionPanel connectionPanel = this.wConnectionPanel.get();
            if(connectionPanel == null){
                return;
            }
            String userInPort = connectionPanel.inBoundPortField.getText();
            String userOutPort = connectionPanel.outBoundPortField.getText();
            if(userInPort.equals("") || userOutPort.equals("")){
                return;
            }
            String ipAddress = connectionPanel.ipAddressField.getText();
            String userKey = connectionPanel.encryptionKeyField.getText();
            if(ipAddress.equals("") || userKey.equals("")){
                return;
            }

            int inBoundPort = Integer.parseInt(userInPort);
            int outBoundPort = Integer.parseInt(userOutPort);

            ConnectionManager.setPorts(inBoundPort, outBoundPort);
            ConnectionManager.setHostName(ipAddress);
            try {
                connectionPanel.frame.assignController(KeyGen.generateKey(userKey.getBytes()),KeyGen.generateIV());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            connectionPanel.frame.showPanel(Constants.USER_PANEL);
        }
    }

    /**
     * Assign default values to the text fields.
     */
    private void setDefaults(){
        inBoundPortField.setText(Integer.toString(Constants.DEFAULT_RECEIVING_PORT));
        outBoundPortField.setText(Integer.toString(Constants.DEFAULT_SENDING_PORT));
        ipAddressField.setText(Constants.DEFAULT_IP_ADDRESS);
        encryptionKeyField.setText(Constants.DEFAULT_ENCRYPTION_KEY);
    }

    /**
     * Set up the user password input.
     */
    private void setUpUserPasswordInput(){
        JLabel encryptionLabel = new JLabel(Constants.ENCRYPTION_LABEL);
        this.add(encryptionLabel, constraints);
        constraints.gridx++;
        encryptionKeyField = new JTextField(20);
        this.add(encryptionKeyField, constraints);
        constraints.gridy++;

        constraints.gridx = 0;
    }
}
