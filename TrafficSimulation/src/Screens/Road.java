package Screens;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Road extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final int PLAYER = 1;

	static RoadBoard map = new RoadBoard(10);
	private Image bgSpr;

	private BufferStrategy strategy;

	private JFrame frame;

	// Constructor.
	public Road(RoadBoard r) throws InterruptedException, IOException {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		setBounds(0, 0, 60 * r.roadSize, 60 * r.roadSize + 50);

		frame.add(this);
		frame.setSize(60 * r.roadSize, 60 * r.roadSize + 50);


		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {

				System.exit(0);
			}
		});

		createBufferStrategy(2);
		strategy = getBufferStrategy();

		boardLoopTimer();

	}

	private Timer timer;

	public void boardLoopTimer() {
		timer = new Timer();
		timer.schedule(new boardLoop(), 0, 1000 / 60);
	}

	private class boardLoop extends java.util.TimerTask {
		public void run() {
			Graphics2D drawing = (Graphics2D) strategy.getDrawGraphics();
			drawing.setColor(Color.white);

			try {
				render(drawing);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strategy.show();
			// debugging - uncomment if you want to see map + movement;
			// System.out.println(c);
			// c++;
			// printMap(mp);

		}
	}

	/*
	 * Responsible for rendering the board screen based on the RoadBoard.
	 */
	private void render(Graphics2D drawing) throws IOException {
		//if newstate available
		// change to false, wait for next instruction
		
		
		/*
		 *  HERE THE CIRCLE SHAPE IS ADDED TO REPRESENT THE CAR
		 *  
		 *  AS AN INITIAL ITERACTION OF THE SIMULATOR, PLAIN COLOR MAY BE 
		 *  USED TO REPRESENT THE CARS POSITION
		 */
		
		
		bgSpr = ImageIO.read(getClass().getResource("4way.jpg"));
		drawing.setColor(Color.black);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				int xpos = 60 * j;
				int ypos = 60 * i;
				drawing.drawImage(bgSpr, xpos, ypos, null);

			}
		}
	}

	

}
