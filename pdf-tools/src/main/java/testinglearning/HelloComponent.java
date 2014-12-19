package testinglearning;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

public class HelloComponent extends JComponent implements MouseMotionListener, ActionListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String theMessage;
	int messageX = 125, messageY = 95; // Coordinates of the message

	JButton theButton;

	int colorIndex; // Current index into someColors
	static Color[] someColors = { Color.black, Color.red, Color.green, Color.blue, Color.magenta, Color.darkGray};
	
	 boolean blinkState;

	public HelloComponent(String message) {
		theMessage = message;
		theButton = new JButton("Change Color");
		setLayout(new FlowLayout());
		add(theButton);
		theButton.addActionListener(this);
		addMouseMotionListener(this);
		 Thread t = new Thread( this );
		 t.start();
	}

	public void paintComponent(Graphics g) {
		g.setColor(blinkState ? getBackground() : currentColor( ));
		g.drawString(theMessage, messageX, messageY);
	}

	public void mouseDragged(MouseEvent e) {
		// Save the mouse coordinates and paint the message.
		messageX = e.getX();
		messageY = e.getY();
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}
	
	 public void actionPerformed( ActionEvent e ) {
		// Did somebody push our button?
		if (e.getSource() == theButton)
		changeColor();
		}
	 
	 synchronized private void changeColor() {
		// Change the index to the next color, awkwardly.
		if (++colorIndex == someColors.length)
		colorIndex = 0;
		setForeground(currentColor( )); // Use the new color.
		repaint( ); // Paint again so we can see the change.
		}
		synchronized private Color currentColor() {
		return someColors[colorIndex];
		}
		
		public void run( ) {
			try {
			while(true) {
			blinkState = !blinkState; // Toggle blinkState.
			repaint(); // Show the change.
			Thread.sleep(300);
			}
			} catch (InterruptedException ie) { }
			}


}
