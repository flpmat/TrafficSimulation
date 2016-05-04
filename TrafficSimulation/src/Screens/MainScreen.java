package Screens;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;



public class MainScreen extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	



	//private DrawPanel panel = new DrawPanel();

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
					
					RoadBoard r = new RoadBoard(10);
					
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
		frame.setBounds(100, 100, 950, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnOi = new JMenu("Oi");
		menuBar.add(mnOi);
		frame.getContentPane().setLayout(null);

		// panel.setBounds(309, 74, 30, 30);
		// panel.setPreferredSize(new Dimension(800, 420));

		//frame.getContentPane().add(panel);

		// JLabel lblNewLabel = new JLabel("New label");
		// lblNewLabel.setIcon(new
		// ImageIcon("/Users/FelipeCosta/Documents/workspace/TrafficVisualization/src/Screens/4way.jpg"));
		// lblNewLabel.setBounds(17, 23, 682, 414);
		// frame.getContentPane().add(lblNewLabel);

		// frame.createBufferStrategy(2);
		// strategy = frame.getBufferStrategy();
		//	panel.start();
	
	}

	
	
}
