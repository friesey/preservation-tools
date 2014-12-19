package testinglearning;

import javax.swing.JFrame;

public class Mouselistener {

	public static void main(String args[]) throws Exception {

		JFrame frame = new JFrame("Testing Mouseclicks");
		frame.add(new HelloComponent("Hello Java"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);

	}

}
