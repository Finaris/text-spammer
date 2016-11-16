/**
 * @author Joseph Torres
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.text.NumberFormatter;

public class Interface extends JFrame{

	private static final long serialVersionUID = 1L;
	private static boolean isRunning = false;
	private Handler handle;
	private static Timer timer;
	private static Robot bot;
	
	//Panel Interface
	private JPanel panel;
	private static JButton start;
	private JButton clear, info;
	private JTextField message;
	private JFormattedTextField messageNum, timeAmountField;
	private JLabel messageLabel, messageNumLabel, timeSelectLabel, timeAmountLabel, messageSelectLabel;
	@SuppressWarnings("rawtypes")
	private JComboBox timeSelect, timeAmountSelect, messageSelect;
	BufferedImage bgImg = null;
	
	//Info dialog override
	private JButton ok = new JButton(new ImageIcon(getClass().getResource("ok.png")));
	private Object[] options = {ok};
	
	//Drop-down menu options
	final int DEFAULT = 0;
	int customTime;
	boolean isDefault = true;
	
	//Repeat size -> messageNum
	private String spam = "";
	private static int buffer = 1;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Interface()
	{
		super("Text Spammer");
		handle = new Handler();
		timer = new Timer(DEFAULT, Interface::run);
		
		//Formatter to prevent characters in Integer field
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setAllowsInvalid(false);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setCommitsOnValidEdit(true);
		
		//Grabs the background image
		try {
			bgImg = ImageIO.read(getClass().getResourceAsStream("bg.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		panel = new JPanel(new FlowLayout()){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {

			    super.paintComponent(g);
			    g.drawImage(bgImg, 0, 0, null);
			}
		};
		
		//Sets icon image
		ImageIcon img = new ImageIcon(getClass().getResource("T.png"));
		setIconImage(img.getImage());
		
		//Drop-down configure for message
		String[] messageSelectArray = {"Text", "Picture"};
		messageSelect = new JComboBox(messageSelectArray);
		messageSelect.addActionListener(handle);
		messageSelectLabel = new JLabel("Time Between Messages");
		messageSelectLabel.setFont(new Font("Serif", Font.BOLD, 25));
		messageSelectLabel.setForeground(Color.WHITE);
		
		//Message area String reception
		message = new JTextField("", 30);
		message.setFont(new Font("Serif", Font.PLAIN, 15));
		message.setMaximumSize(message.getPreferredSize());
		messageLabel = new JLabel("Message");
		messageLabel.setFont(new Font("Serif", Font.BOLD, 25));
		messageLabel.setForeground(Color.WHITE);
		
		//Message number String reception
		messageNum = new JFormattedTextField(formatter);
		messageNum.setColumns(6);
		messageNum.setFont(new Font("Serif", Font.PLAIN, 15));
		messageNumLabel = new JLabel("Number of Messages");
		messageNumLabel.setFont(new Font("Serif", Font.BOLD, 25));
		messageNumLabel.setForeground(Color.WHITE);
		
		//Drop-down configure
		String[] timeSelectArray = {"Default", "Custom"};
		timeSelect = new JComboBox(timeSelectArray);
		timeSelect.addActionListener(handle);
		timeSelectLabel = new JLabel("Time Between Messages");
		timeSelectLabel.setFont(new Font("Serif", Font.BOLD, 25));
		timeSelectLabel.setForeground(Color.WHITE);
		
		//Conditional time intervals
		String[] timeAmountSelectArray = {"Milliseconds", "Seconds", "Minutes", "Hours"};
		timeAmountSelect = new JComboBox(timeAmountSelectArray);
		timeAmountSelect.addActionListener(handle);
		
		timeAmountField = new JFormattedTextField(formatter);
		timeAmountField.setColumns(6);
		timeAmountField.setFont(new Font("Serif", Font.PLAIN, 15));
		
		timeAmountLabel = new JLabel("Configure Time Between");
		timeAmountLabel.setFont(new Font("Serif", Font.BOLD, 25));
		timeAmountLabel.setForeground(Color.WHITE);
		
		timeAmountSelect.setVisible(false);
		timeAmountLabel.setVisible(false);
		timeAmountField.setVisible(false);
		
		//Configures the start button
		start = new JButton(new ImageIcon(getClass().getResource("start.png")));
		start.setRolloverIcon(new ImageIcon(getClass().getResource("startHover.png")));
		start.setPressedIcon(new ImageIcon(getClass().getResource("startPressed.png")));
		start.setOpaque(false);
		start.setContentAreaFilled(false);
		start.setBorderPainted(false);
		start.setFocusPainted(false);
		start.addActionListener(handle);
		start.addActionListener(e -> timer.start());
		
		//Reset the state
		clear = new JButton(new ImageIcon(getClass().getResource("clear.png")));
		clear.setRolloverIcon(new ImageIcon(getClass().getResource("clearHover.png")));
		clear.setPressedIcon(new ImageIcon(getClass().getResource("clearPressed.png")));
		clear.setOpaque(false);
		clear.setContentAreaFilled(false);
		clear.setBorderPainted(false);
		clear.setFocusPainted(false);
		clear.addActionListener(handle);
		
		//Provides info
		info = new JButton(new ImageIcon(getClass().getResource("info.png")));
		info.setRolloverIcon(new ImageIcon(getClass().getResource("infoHover.png")));
		info.setPressedIcon(new ImageIcon(getClass().getResource("infoPressed.png")));
		info.setOpaque(false);
		info.setContentAreaFilled(false);
		info.setBorderPainted(false);
		info.setFocusPainted(false);
		info.addActionListener(handle);
		
		//Configure layout
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 45));
		Box verticalSpace = Box.createVerticalBox();
		verticalSpace.add(Box.createRigidArea(new Dimension(80, 0)));
		verticalSpace.add(Box.createRigidArea(new Dimension(100 ,0)));
		
		//Layout cheat buttons
		JButton empty1 = new JButton(new ImageIcon(getClass().getResource("empty.png")));
		empty1.setOpaque(false);
		empty1.setContentAreaFilled(false);
		empty1.setBorderPainted(false);
		empty1.setFocusPainted(false);
		JButton empty2 = new JButton(new ImageIcon(getClass().getResource("empty.png")));
		empty2.setOpaque(false);
		empty2.setContentAreaFilled(false);
		empty2.setBorderPainted(false);
		empty2.setFocusPainted(false);
		
		//Adds everything to the JFrame
		add(panel);
		panel.add(messageLabel);
		panel.add(message);
		panel.add(timeSelectLabel);
		panel.add(timeSelect);
		panel.add(timeAmountLabel);
		panel.add(timeAmountSelect);
		panel.add(timeAmountField);
		panel.add(messageNumLabel);
		panel.add(messageNum);
		panel.add(verticalSpace);
		panel.add(clear);
		panel.add(empty1);
		panel.add(start);
		panel.add(empty2);
		panel.add(info);
        
	}
	
	//Configures the spam before beginning
	public void configSpam() throws AWTException, InterruptedException
	{
		
		bot = new Robot();
		isRunning = true;
		
		int delayTime;
		if(isDefault)
			delayTime = DEFAULT;
		else
			delayTime = customTime;
		
		timer.setDelay(delayTime);
		
		StringSelection selection = new StringSelection(spam);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	    changeToStop();
	    
	    Thread.sleep(1250);
	
	}
	
	//Runs the spam
	private static void run(final ActionEvent e) {
		if(buffer <= 1) {
			timer.stop();
			changeToStart();
			isRunning = false;
		}
		bot.keyPress(KeyEvent.VK_CONTROL);
		bot.keyPress(KeyEvent.VK_V);
		bot.keyRelease(KeyEvent.VK_CONTROL);
		bot.keyRelease(KeyEvent.VK_V);
		bot.keyPress(KeyEvent.VK_ENTER);
		bot.keyRelease(KeyEvent.VK_ENTER);
		buffer--;
    }
	
	//Calculates the custom time from the fields
	public void calculateCustomTime() {
		
		String unit = (String)timeAmountSelect.getSelectedItem();
		String timeString = timeAmountField.getText();
		int time = Integer.parseInt(timeString);
		switch(unit) {
			case "Milliseconds": 
				customTime = time;
				break;
			case "Seconds":
				customTime = time * 1000;
				break;
			case "Minutes":
				customTime = time * 60000;
				break;
			case "Hours":
				customTime = time * 3600000;
				break;
			default:
				customTime = 0;
				break;
		}
		
	}
	
	//Changes the start button to stop
	public void changeToStop() {
		start.setIcon(new ImageIcon(getClass().getResource("stop.png")));
		start.setRolloverIcon(new ImageIcon(getClass().getResource("stopHover.png")));
		start.setPressedIcon(new ImageIcon(getClass().getResource("stopPressed.png")));
		start.removeActionListener(e -> timer.start());
		start.addActionListener(e -> timer.stop());
	}
	
	//Changes the stop button to start
	public static void changeToStart() {
		start.setIcon(new ImageIcon(Interface.class.getResource("start.png")));
		start.setRolloverIcon(new ImageIcon(Interface.class.getResource("startHover.png")));
		start.setPressedIcon(new ImageIcon(Interface.class.getResource("startPressed.png")));
		start.removeActionListener(e -> timer.stop());
		start.addActionListener(e -> timer.start());
	}
	
	//Creates handlers for the objects in the panel
	private class Handler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == start) {
				
				if(!isRunning) {
					spam = message.getText();
					String messageNumText = messageNum.getText();
					messageNumText = messageNumText.replace(",", "");
					buffer = Integer.parseInt(messageNumText);
					
					if(isDefault) {
						try {
							configSpam();
						} catch (AWTException | InterruptedException e1) {
							e1.printStackTrace();
						}
					} else {
						try {
							calculateCustomTime();
							configSpam();
						} catch (AWTException | InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					timer.stop();
					changeToStart();
					isRunning = false;
				}

			}
			else if(e.getSource() == clear) {
				isDefault = true;
				timeAmountSelect.setVisible(false);
				timeAmountLabel.setVisible(false);
				timeAmountField.setVisible(false);
				timeSelect.setSelectedItem("Default");
				message.setText("");
				messageNum.setValue(null);
				timeAmountField.setValue(null);
			}
			else if(e.getSource() == timeSelect) {
				String choice = (String)timeSelect.getSelectedItem();
				if (choice=="Default") {
					isDefault = true;
					timeAmountSelect.setVisible(false);
					timeAmountLabel.setVisible(false);
					timeAmountField.setVisible(false);
				} else {
					isDefault = false;
					timeAmountSelect.setVisible(true);
					timeAmountLabel.setVisible(true);
					timeAmountField.setVisible(true);
				}
			}
			else if(e.getSource() == messageSelect) {
				String choice = (String)timeSelect.getSelectedItem();
				if (choice=="Text") {
					isDefault = true;
					timeAmountSelect.setVisible(false);
					timeAmountLabel.setVisible(false);
					timeAmountField.setVisible(false);
				} else {
					isDefault = false;
					timeAmountSelect.setVisible(true);
					timeAmountLabel.setVisible(true);
					timeAmountField.setVisible(true);
				}
			}
			else if(e.getSource() == info) {
				ok.setContentAreaFilled(false);
				ok.setBorderPainted(false);
				ok.setFocusPainted(false);
				ok.setRolloverIcon(new ImageIcon(getClass().getResource("okHover.png")));
				ok.setPressedIcon(new ImageIcon(getClass().getResource("okPressed.png")));
				ok.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent evt) {
					    Window w = SwingUtilities.getWindowAncestor(ok);

					    if (w != null) {
					      w.setVisible(false);
					    }
					  }
					});
				String instructions = "Text Spammer Instructions v1.1.0:\n\n1. Fill out the message at the top of the panel.\n"
						+ "2. Decide whether or not you would like to send spam as quickly as possible or at intervals\n"
						+ "   (longer intervals may circumvent spam detection systems).\n"
						+ "3. If you chose a custom time interval, adjust it as desired. If not, move on.\n"
						+ "4. Select how many messages you would like to send in total.\n"
						+ "5. When ready, find the window you'd like to spam. After pressing start, you will have\n"
						+ "   1.25 seconds to place your cursor in the textfield. From there, sit back and enjoy.\n\n"
						+ "Note I: You can stop the spam at any time by pressing the \"Stop\" button. Also, there is a\n"
						+ "\"Clear\" button on the bottom left which will wipe out all entries.\n"
						+ "Note II: If you cannot edit a number field, try highlighting and typing over the text, or \n"
						+ "clearing all the values and typing in the blank field.";
				JOptionPane.showOptionDialog(null, instructions, "Text Spammer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, 
						new ImageIcon(getClass().getResource("info.png")), options, options[0]);
				
			}
		}

	}
	
}
