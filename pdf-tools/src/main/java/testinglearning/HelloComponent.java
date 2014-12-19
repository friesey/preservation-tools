package testinglearning;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

public class HelloComponent extends JComponent implements MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String theMessage;
	int messageX = 125, messageY = 95; // Coordinates of the message

	public HelloComponent(String message) {
		theMessage = message;
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
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

}
