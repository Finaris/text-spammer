/**
 * @author Madelyn Torres
 */

import java.awt.Dimension;
import javax.swing.*;

public class Display {
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
            	Interface surveyBot = new Interface();
        		Dimension size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        		
        		surveyBot.pack();
        		surveyBot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		surveyBot.setLocation( ((int)size.getWidth()/2) - 275, ((int)size.getHeight()/2) - 225);
        		surveyBot.setSize(550, 450);
        		surveyBot.setVisible(true);
        		surveyBot.setResizable(false);
        		
        		
            }
        });
	
	}
	
}
