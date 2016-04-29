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

public class Road extends Canvas implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// whether keys are pressed
	private boolean DOWN = false;
	private boolean UP = false;
	private boolean LEFT = false;
	private boolean RIGHT = false;
	
	static final int PLAYER = 1;

	// current player xy coordinates
	private static int playerX;
	private static int playerY;

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

		frame.addKeyListener(this);
		addKeyListener(this);
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

			logic();

			try {
				render(drawing);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			drawing.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			drawing.setColor(Color.red);
			// drawing.drawString(scoreString, 10, 60 *
			// ((GameBoard)raft.RaftNode.getStateObject()).boardSize + 10);
			drawing.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			// drawing.drawString(winner,
			// 60*((GameBoard)raft.RaftNode.getStateObject()).boardSize/4, 60 *
			// ((GameBoard)raft.RaftNode.getStateObject()).boardSize/2);
			drawing.dispose();
			strategy.show();
			// debugging - uncomment if you want to see map + movement;
			// System.out.println(c);
			// c++;
			// printMap(mp);

		}
	}

	/*
	 * Reads the variables altered by the listener and updates the RoadBoard.
	 * 
	 */

	private void logic() {
		// check if player needs to be moved
		int x = 0;
		int y = 0;
		if (UP) {
			y -= 1;
			UP = false;
		}
		if (DOWN) {
			y += 1;
			DOWN = false;
		}
		if (LEFT) {
			x -= 1;
			LEFT = false;
		}
		if (RIGHT) {
			x += 1;
			RIGHT = false;
		}
		if ((x != 0) || (y != 0)) {
			// save old positions
			int oldX = playerX;
			int oldY = playerY;

			// update new position
			playerX += x;
			playerY += y;

			System.out.printf("Old: %d,%d - New: %d,%d \n", oldX, oldY, playerX, playerY);

			// if player goes over boundaries, move to other side
			if (playerX < 0) {
				playerX = map.roadSize + playerX;
			}
			if (playerX >= map.roadSize) {
				playerX = playerX - map.roadSize;
			}
			if (playerY < 0) {
				playerY = map.roadSize + playerY;
			}
			if (playerY >= map.roadSize) {
				playerY = playerY - map.roadSize;
			}
			// send or process instruction (note, coords are reversed)
			// raft.API.pushInstruction(new Instruction(playerY,playerX,PLAYER,
			// oldY, oldX, raft.RaftNode.myAddress));
		}
	}

	/*
	 * Responsible for rendering the board screen based on the RoadBoard.
	 */
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
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			DOWN = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			UP = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			LEFT = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			RIGHT = true;
			// printMap(map);
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			DOWN = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			UP = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			LEFT = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			RIGHT = false;
		}
		// TODO Auto-generated method stub

	}

}