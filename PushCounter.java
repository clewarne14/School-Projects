/**
 * PushCounter.java
 *
 * This class is a subclass of JFrame that implements the main
 * functionality for the program.  It provides a simple GUI with
 * a button and label.  The label displays the number of times
 * that the button has been pressed.
 *
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PushCounter extends JFrame {

    // The "Push Me" button
    private JButton pushButton;
    // Label that shows the number of pushes
    private JLabel pushCountLabel;
    // Counter that counts the number of pushes of the button.
    private int pushCount;

    /**
     * Constructs the GUI components for this frame and makes
     * the window visible on the screen.
     */
    public PushCounter() {
        super("Push Counter");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel
        JPanel mainPanel = new JPanel();

        // Create the necessary components, and place them within
        // the panel.
        pushButton = new JButton( "Push Me!");
        pushCountLabel = new JLabel( "Pushes: " + pushCount );

        // Add an instance of the inner class to be the action listener
        // for the pushButton.
        pushButton.addActionListener(new PushCounterButtonListener());

        mainPanel.add(pushButton);
        mainPanel.add(pushCountLabel);

        // Add the panel to this JFrame
        this.add(mainPanel);

        // Size this JFrame so that it is just big enough to hold the components.
        this.setSize(300,70);

        // Make this JFrame visible on the screen
        this.setVisible(true);
    }

    /**
     * This is a private inner class that is responsible for handling events
     * from the button in this GUI.
     */
    private class PushCounterButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            pushCount++;
            pushCountLabel.setText("Pushes: " + pushCount);
        }

    }
    
    /** 
    * main method to instantiate the GUI frame
    */
    
    public static void main( String[] args )
    {
        PushCounter frame = new PushCounter();
    }


}
