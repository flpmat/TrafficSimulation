package Screens;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Road extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO CHANGE POSITIONS OF EXIT TO ACTUAL EXIT PLACE. AFTER EXITING, DROP
	// CAR AND SHAPE FROM THEIR RESPECTIVE LISTS.
	// RE-CHECK POSITIONS.

	static final int[] position = { 4, 5, 5, 4, 5, 3, 4, 2, 4, 4, 4, 3, 3, 2,
			2, 3, 2, 4, 3, 5, 3, 3, 3, 4, 4, 8, 8, 4, 8, 3, 4, -1, 3, -1, -1, 3,
			-1, 4, 3, 7 };
	static final int PLAYER = 1;

	static HashMap<Integer, Car> cars;
	static HashMap<Integer, BallComponent> carShapes;
	static HashMap<Integer, Boolean> completed;

	static RoadBoard road = new RoadBoard(10);
	private Image bgSpr;

	private BufferStrategy strategy;

	Graphics2D drawing;

	private JFrame frame;

	// Constructor.
	public Road(RoadBoard r) throws InterruptedException, IOException {
		road = r;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		this.setBounds(0, 0, 60 * 10, 60 * 10);

		frame.add(this);
		frame.setSize(60 * 10, 60 * 10);

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {

				System.exit(0);
			}
		});

		createBufferStrategy(2);
		strategy = getBufferStrategy();
		setIgnoreRepaint(true);

		drawing = (Graphics2D) strategy.getDrawGraphics();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int xpos = 60 * i;
				int ypos = 60 * j;
				// drawing.drawImage(bgSpr, xpos, ypos, null);
				if ((i == 3 || i == 4) || (j == 3 || j == 4)) {
					drawing.setColor(Color.DARK_GRAY);
					drawing.fillRect(xpos, ypos, 60, 60);
					drawing.setColor(new Color(25, 255, 0));
					drawing.drawOval(xpos + 30, ypos + 30, 10, 10);
				} else {
					drawing.setColor(Color.green);
					drawing.fillRect(xpos, ypos, 60, 60);
				}

			}
		}
		Road.cars = new HashMap<Integer, Car>();
		carShapes = new HashMap<Integer, Road.BallComponent>();
		completed = new HashMap<Integer, Boolean>();

		new Thread(new Instructions()).start();

	}

	public class Instructions implements Runnable {

		public void run() {
			Queue<int[]> teste = new LinkedList<int[]>();

			for (Entry<Integer, Car> entry : cars.entrySet()) {
				completed.put(entry.getKey(), true);

			}

			InstructionApplier inst = new InstructionApplier();

			int[] teste1 = { 2, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 3, 0, 0, 1, 0,
					1, 0, 1, 0, 1 };
			int[] teste2 = { 0, 1, 0, 1, 0, 1, 0, 1, 2, 0, 0, 1, 0, 1, 0, 1, 0,
					1, 0, 1, 3, 0 };
			int[] teste3 = { 0, 1, 2, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 3, 0, 0,
					1, 0, 1, 0, 1 };
			int[] teste4 = { 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 3,
					0, 0, 1, 0, 1 };
			int[] teste5 = { 0, 1, 0, 1, 0, 1, 2, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0,
					1, 0, 1, 0, 1 };

			teste.add(teste1);
			teste.add(teste2);
			teste.add(teste3);
			// teste.add(teste4);
			// teste.add(teste5);
			boolean loop = false;
			while (!teste.isEmpty()) {
				if (!completed.containsValue(false)) {
					System.out.println("inst");
					inst.ApplyInstruction(teste.poll());
					completed.clear();
					for (Entry<Integer, Car> entry : cars.entrySet()) {
						completed.put(entry.getKey(), false);
					}
					System.out.println("false");

				}
				if (!loop) {
					boardLoopTimer();
					loop = true;
				}
			}
		}

	}

	private Timer timer;

	public void boardLoopTimer() {
		timer = new Timer();
		timer.schedule(new boardLoop(), 0, 1000 / 30);
	}

	private class boardLoop extends java.util.TimerTask {
		public void run() {
			// drawing = (Graphics2D) strategy.getDrawGraphics();
			drawing.setColor(Color.white);

			try {
				render();
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
	private boolean render() throws IOException {

		// if newstate available
		// change to false, wait for next instruction

		/*
		 * HERE THE CIRCLE SHAPE IS ADDED TO REPRESENT THE CAR
		 * 
		 * AS AN INITIAL ITERACTION OF THE SIMULATOR, PLAIN COLOR MAY BE USED TO
		 * REPRESENT THE CARS POSITION
		 */

		if (road.newStateAvailable) {
			for (Entry<Integer, Car> entry : cars.entrySet()) {
				// if (!carShapes.containsKey(entry.getKey())) {
				if (carShapes.containsKey(entry.getKey())) {
					carShapes.get(entry.getKey()).remove(
							carShapes.get(entry.getKey()));
					carShapes.get(entry.getKey()).setVisible(false);

				}
				System.out.println("x "
						+ position[2 * entry.getValue().currentPos]);
				System.out.println("y "
						+ position[2 * entry.getValue().currentPos + 1]);
				carShapes
						.put(entry.getKey(),
								new BallComponent(
										entry.getKey(),
										entry.getValue().currentPos,
										entry.getValue().nextPosition,
										position[2 * entry.getValue().currentPos] * 60,
										position[2 * entry.getValue().currentPos + 1] * 60,
										position[2 * entry.getValue().nextPosition] * 60,
										position[2 * entry.getValue().nextPosition + 1] * 60,
										entry.getValue().color, drawing));
				carShapes.get(entry.getKey()).setVisible(true);
				carShapes.get(entry.getKey()).setIgnoreRepaint(true);
				this.frame.add(carShapes.get(entry.getKey()), 0);
				// }
			}

			// carShapes.put(1, new BallComponent(20, 30, 1, drawing));
			// carShapes.put(2, new BallComponent(50, 60, 2, drawing));
			// /
			// carShapes.get(1).setVisible(true);
			// carShapes.get(1).setIgnoreRepaint(true);
			// this.frame.add(carShapes.get(1), 0);
			// / carShapes.get(2).setVisible(true);
			// carShapes.get(2).setIgnoreRepaint(true);
			// this.frame.add(carShapes.get(2), 0);
			road.newStateAvailable = false;
		}
		return true;

		// bgSpr = ImageIO.read(getClass().getResource("4way.jpg"));
		// drawing.setColor(Color.black);

	}

	public class BallComponent extends JComponent implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Rectangle r;
		int key, currentPosition, nextPosition, nextPositionX, nextPositionY,
				currentPosX, currentPosY;

		int xSpeed = 1;
		int ySpeed = 1;
		Color color;

		javax.swing.Timer tm = new javax.swing.Timer(10, this);

		public BallComponent(int key, int currentPosition, int nextPosition,
				int currentPosX, int currentPosY, int nextPositionX,
				int nextPositionY, Color color, Graphics2D drawing) {
			super();
			setBounds(currentPosX + 30, currentPosY + 20, 20, 20);
			this.color = color;
			r = new Rectangle(0, 0, 50, 50);
			this.key = key;
			this.currentPosition = currentPosition;
			this.nextPosition = nextPosition;
			this.currentPosX = currentPosX;
			this.currentPosY = currentPosY;
			this.nextPositionX = nextPositionX + 30;
			this.nextPositionY = nextPositionY + 20;

			System.out.println("inicializou");

		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(color);
			g2.fill(r);
			g2.draw(r);
			tm.start();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int x = getX();
			int y = getY();

			if (currentPosition == 0 || currentPosition == 3
					|| currentPosition == 12) {
				y = y - 3;
				if (getY() != nextPositionY) {
					setLocation(x, y);
					repaint();
				} else {
					tm.stop();
					completed.put(key, true);
				}
			}
			if (currentPosition == 6 || currentPosition == 9
					|| currentPosition == 16) {

				y = y + 3;
				if (getY() != nextPositionY) {
					// System.out.println("NEXT POS: " + (nextPositionY - 40));
					// System.out.println("Y: " + getY());
					setLocation(x, y);
					repaint();
				} else {
					tm.stop();
					completed.put(key, true);
				}
			}
			if (currentPosition == 1 || currentPosition == 8
					|| currentPosition == 18) {
				x = x + 3;
				if (getX() != nextPositionX) {
					setLocation(x, y);
					repaint();
				} else {
					tm.stop();
					completed.put(key, true);
				}

			}
			if (currentPosition == 2 || currentPosition == 7
					|| currentPosition == 14) {
				x = x - 3;
				if (getX() != nextPositionX) {
					setLocation(x, y);
					repaint();
				} else {
					tm.stop();
					completed.put(key, true);
				}

			}
			if (currentPosition == 4) {
				if (nextPosition == 1) {
					this.nextPositionX = position[13*2] * 60;
					this.nextPositionY = position[13*2 + 1] * 60;
					x = x + 3;
					if (getX() != nextPositionX) {
						setLocation(x, y);
						repaint();
					} else {
						tm.stop();
						completed.put(key, true);
					}
				} else {
					y = y - ySpeed;
					if (getY() != nextPositionY) {
						setLocation(x, y);
						repaint();
					} else {
						tm.stop();
						completed.put(key, true);
					}
				}
			}
			if (currentPosition == 5) {
				if (nextPosition == 10) {
					x = x - xSpeed;
					if (getX() != nextPositionX) {
						setLocation(x, y);
						repaint();
					} else {
						tm.stop();
						completed.put(key, true);
					}
				} else {
					this.nextPositionX = position[15*2] * 60;
					this.nextPositionY = position[15*2 + 1] * 60;
					y = y - 3;
					if (getY() != nextPositionY) {
						setLocation(x, y);
						repaint();
					} else {
						tm.stop();
						completed.put(key, true);
					}
				}
			}
			if (currentPosition == 10) {
				if (nextPosition == 7) {
					this.nextPositionX = position[17*2] * 60;
					this.nextPositionY = position[17*2 + 1] * 60;
					x = x - 3;
					if (getX() != nextPositionX) {
						setLocation(x, y);
						repaint();
					} else {
						tm.stop();
						completed.put(key, true);
					}
				} else {
					y = y + ySpeed;
					if (getY() != nextPositionY) {
						setLocation(x, y);
						repaint();
					} else {
						tm.stop();
						completed.put(key, true);
					}
				}
			}
			if (currentPosition == 11) {
				if (nextPosition == 4) {
					x = x + xSpeed;
					if (getX() != nextPositionX) {
						setLocation(x, y);
						repaint();
					} else {
						tm.stop();
						completed.put(key, true);
					}
				} else {
					this.nextPositionX = position[19*2] * 60;
					this.nextPositionY = position[19*2 + 1] * 60;
					y = y + 3;
					if (getY() != nextPositionY) {
						setLocation(x, y);
						repaint();
					} else {
						tm.stop();
						completed.put(key, true);

					}
				}
			}
		}
	}

}
