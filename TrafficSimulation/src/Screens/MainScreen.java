package Screens;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class MainScreen extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame frame;

	private DrawPanel panel = new DrawPanel();

	private Image bgSpr;

	private BufferStrategy strategy;

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

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class BallComponent extends JComponent implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		int id;
		Rectangle r;
		int xSpeed = 0;
		int ySpeed = 1;

		javax.swing.Timer tm = new javax.swing.Timer(5, this);

		public BallComponent(int x, int y, int _id) {
			 super();
			setBounds(x, y, 10, 10);
			id = _id;
			r = new Rectangle(0,0,100,100);

		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(Color.RED);
			g2.fill(r);
			g2.draw(r);
			tm.start();
		}

		public void checkOverlap(BallComponent b2) throws InterruptedException {
			
			if (this.getBounds().intersects(b2.getBounds())){
				System.out.println("Colidiu");
				b2.stopY();
			}
			 //System.out.println(" me: " + getBounds());
		//	System.out.println("you: " + b2.getBounds());

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			tick();
			try {
				// System.out.println(id);
				if (id == 0)
					checkOverlap(panel.shapes.get(id + 1));
				else
					checkOverlap(panel.shapes.get(id - 1));

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public void moveLeft() {
			xSpeed = -1;
		}

		public void moveRight() {
			xSpeed = 1;
		}

		public void moveUp() {
			ySpeed = -1;
		}

		public void moveDown() {
			ySpeed = 1;
		}

		public void tick() {
			int x = getX() + xSpeed;
			int y = getY() + ySpeed;

			if (y > 500 - 30 || y < 0 + 30)
				ySpeed = -ySpeed;
			y = y + ySpeed;

			setLocation(x, y);

			repaint();
		}

		public void stopY() {
			ySpeed = 0;
		}

		public void stopX() {
			xSpeed = 0;
		}

	}

	public class DrawPanel extends JPanel {

		/**
		 * 
		 */
		ArrayList<BallComponent> shapes = new ArrayList<MainScreen.BallComponent>();

		private static final long serialVersionUID = 1L;

		// javax.swing.Timer tm = new javax.swing.Timer(5, this);

		// int y = 500-30, velY = 1;
		/*
		 * public void paint(Graphics g) {
		 * 
		 * Graphics2D g2 = (Graphics2D) g;
		 * 
		 * super.paint(g); Rectangle r1 = new Rectangle(250, 200, 30, 30);
		 * g2.setColor(Color.red); g2.fill(r1); Rectangle r2 = new
		 * Rectangle(250, y+30, 30, 30); g2.setColor(Color.green); g2.fill(r2);
		 * tm.start(); }
		 */

		public void start() throws IOException, InterruptedException {
			/*
			 * EventQueue.invokeLater(new Runnable() { public void run() { try {
			 * MainScreen window = new MainScreen(); window.setVisible(true); }
			 * catch (Exception e) { e.printStackTrace(); } } });
			 */
			panel.setLayout(null);
			setBounds(50, 50, 500, 500);
			// panel.add(this);
			panel.setSize(500, 500);
			// panel.setBackground(Color.black);;
			panel.setVisible(true);

			BufferedImage img = ImageIO
					.read(getClass().getResource("road.png"));
			ImageIcon icon = new ImageIcon(img);

			JLabel jLBackgroundImage = new JLabel(icon);
			jLBackgroundImage.setBounds(0, 0, 500, 500);
			this.add(jLBackgroundImage, -1);

			BallComponent b = new BallComponent(10, 40, 0);

			shapes.add(b);
			b.setVisible(true);
			this.add(b, 0);

			BallComponent b2 = new BallComponent(10, 150, 1);

			shapes.add(b2);
			b2.setVisible(true);
			this.add(b2, 1);

		}

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

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnOi = new JMenu("Oi");
		menuBar.add(mnOi);
		frame.getContentPane().setLayout(null);

		// panel.setBounds(309, 74, 30, 30);
		// panel.setPreferredSize(new Dimension(800, 420));

		frame.getContentPane().add(panel);

		// JLabel lblNewLabel = new JLabel("New label");
		// lblNewLabel.setIcon(new
		// ImageIcon("/Users/FelipeCosta/Documents/workspace/TrafficVisualization/src/Screens/4way.jpg"));
		// lblNewLabel.setBounds(17, 23, 682, 414);
		// frame.getContentPane().add(lblNewLabel);

		// frame.createBufferStrategy(2);
		// strategy = frame.getBufferStrategy();
		panel.start();

	}

	private void render(Graphics2D drawing) throws IOException {
		// bgSpr = ImageIO.read(getClass().getResource("bg.jpg"));
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