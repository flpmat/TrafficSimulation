package Screens;

import java.awt.Canvas;
import java.awt.Color;
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



public class MainScreen extends Canvas implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	
	private BufferStrategy strategy;


	private DrawPanel panel = new DrawPanel();

	private Image bgSpr;

	//whether keys are pressed
	private boolean DOWN = false;
	private boolean UP = false;
	private boolean LEFT = false;
	private boolean RIGHT = false;
	
	//current player xy coordinates
	private static int playerX;
	private static int playerY;
	
	static GameBoard map = new GameBoard(10);

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public static void main(String[] args) throws IOException,
			InterruptedException {
		//EventQueue.invokeLater(new Runnable() {
		//	public void run() {
		//		try {
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
					window.road();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//	}
		//});
	}
	//ADD gameboard depois
	public void road() throws IOException, InterruptedException{
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
			while(true){
				Graphics2D drawing = (Graphics2D) strategy.getDrawGraphics();
				drawing.setColor(Color.white);
				//drawing.fillRect(0, 0, 60 * ((GameBoard)raft.RaftNode.getStateObject()).boardSize, 60 * ((GameBoard)raft.RaftNode.getStateObject()).boardSize + 50);
				
				logic();
				//coinTimer++;
				//if(coinTimer >= coinTimerLimit){
				//	spawn(0,COIN);
				//	coinTimer = 0;
			//	}
		//		scoreString = "";
			//	for(Entry<String, Integer> entry:((GameBoard)raft.RaftNode.getStateObject()).scores.entrySet()){
				//	scoreString = scoreString + String.format("%s: %d, ",entry.getKey(),entry.getValue()); 
				//}
			//	scores = ((GameBoard)raft.RaftNode.getStateObject()).scores;
				
				Thread.sleep(20);
				
			    render(drawing);
			    drawing.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			    drawing.setColor(Color.red);
				//drawing.drawString(scoreString, 10, 60 * ((GameBoard)raft.RaftNode.getStateObject()).boardSize + 10);
			    drawing.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				//drawing.drawString(winner, 60*((GameBoard)raft.RaftNode.getStateObject()).boardSize/4, 60 * ((GameBoard)raft.RaftNode.getStateObject()).boardSize/2);
				drawing.dispose();
				strategy.show();
				//debugging - uncomment if you want to see map + movement;
			//	System.out.println(c);
			//	c++;
			//	printMap(mp);
			}
		}
		
		
	
	private void logic(){
		// check if player needs to be moved
		int x = 0;
		int y = 0;
		if(UP){
			y -= 1;
			UP = false;
		}
		if(DOWN){
			y += 1;
			DOWN = false;
		}
		if(LEFT){
			x -= 1;
			LEFT = false;
		}
		if(RIGHT){
			x += 1;
			RIGHT = false;
		}
		if((x != 0) || (y != 0)){
			//save old positions
			int oldX = playerX;
			int oldY = playerY;
			
			// update new position
			playerX += x;
			playerY += y;
			
			System.out.printf("Old: %d,%d - New: %d,%d \n",oldX,oldY,playerX,playerY);
			
			// if player goes over boundaries, move to other side
			if(playerX < 0){
				playerX = map.boardSize + playerX;
			}
			if(playerX >= map.boardSize){
				playerX = playerX - map.boardSize;
			}
			if(playerY < 0){
				playerY = map.boardSize + playerY;
			}
			if(playerY >= map.boardSize){
				playerY = playerY - map.boardSize;
			}
			//send or process instruction (note, coords are reversed)
		//	raft.API.pushInstruction(new Instruction(playerY,playerX,PLAYER, oldY, oldX, raft.RaftNode.myAddress));
		}
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
			setBounds(40, 50, 500, 500);
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
		frame.add(this);
		frame.addKeyListener(this);
		addKeyListener(this);
		
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

	private void render(Graphics2D drawing) throws IOException {
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
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					DOWN = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					UP = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					LEFT = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					RIGHT = true;
				//	printMap(map);
				}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					DOWN = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					UP = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					LEFT = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					RIGHT = false;
				}
		// TODO Auto-generated method stub
		
	}
}
