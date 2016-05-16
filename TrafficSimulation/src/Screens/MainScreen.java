package Screens;

import java.awt.Canvas;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.io.IOException;
import javax.swing.JMenuBar;
import javax.swing.JMenu;



public class MainScreen extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	




	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public static void main(String[] args) throws IOException,
			InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
					RoadBoard r = new RoadBoard(8);
					Road main = new Road(r);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public MainScreen() throws IOException, InterruptedException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void initialize() throws IOException, InterruptedException {
		frame = new JFrame();
		frame.setBounds(500, 00, 300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		frame.getContentPane().setLayout(null);
	}
}
