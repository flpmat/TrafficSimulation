package Screens;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	/*
	 * Road position. From 0 to 10, actual positions. From 11 to 18, exit
	 * positions.
	 */
	static final double[] position = { 4, 5, 5, 4, 5, 3, 4, 2, 4, 4, 4, 3, 3,
			2, 2, 3, 2, 4, 3, 5, 3, 3, 3, 4, 4, 7, 8, 4, 7, 3, 4, -1, 3, 0, -1,
			3, -1, 4, 3, 8, 4, 5.5, 4, 6 };

	static HashMap<Integer, Car> cars;
	static HashMap<Integer, ShapeComponent> carShapes;
	static HashMap<Integer, Boolean> completed;
	static Queue<Integer> bottleneckP0;
	static Queue<Integer> bottleneckP0Wait;

	static RoadBoard road = new RoadBoard(10);

	private Image bgSpr;
	private BufferStrategy strategy;
	private Graphics2D drawing;

	private JFrame frame;

	private Timer timer;

	public Road(RoadBoard r) throws InterruptedException, IOException {

		road = r;

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		this.setBounds(0, 0, 480, 483);

		frame.add(this);
		frame.setSize(60 * 8, 60 * 8 + 20);
		frame.setResizable(false);

		frame.setVisible(true);
		setIgnoreRepaint(true);

		createBufferStrategy(2);
		strategy = getBufferStrategy();

		drawing = (Graphics2D) strategy.getDrawGraphics();
		bgSpr = ImageIO.read(getClass().getResource("road.png"));
		drawing.drawImage(bgSpr, -10, -10, null);

		Road.cars = new HashMap<Integer, Car>();
		carShapes = new HashMap<Integer, ShapeComponent>();
		completed = new HashMap<Integer, Boolean>();
		bottleneckP0 = new LinkedList<Integer>();
		bottleneckP0Wait = new LinkedList<Integer>();

		new Thread(new Instructions()).start();

	}

	/*
	 * This thread guarantees each instruction is executed in a FIFO fashion.
	 */
	public class Instructions implements Runnable {

		public void run() {
			Queue<int[]> teste = new LinkedList<int[]>();
			for (Entry<Integer, Car> entry : cars.entrySet()) {
				completed.put(entry.getKey(), true);

			}

			InstructionApplier inst = new InstructionApplier();

			int[] teste1 = { 2, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 3, 0, 0, 1, 0,
					1, 0, 1, 0, 1 };
			int[] teste2 = { 4, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
					1, 0, 1, 3, 0 };
			int[] teste3 = { 5, 0, 0, 1, 0, 1, 0, 1, 2, 0, 0, 1, 0, 1, 3, 0, 0,
					1, 0, 1, 0, 1 };
			int[] teste4 = { 0, 1, 0, 1, 0, 1, 0, 1, 2, 0, 0, 1, 0, 1, 0, 1, 3,
					0, 0, 1, 0, 1 };
			int[] teste5 = { 0, 1, 0, 1, 0, 1, 2, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0,
					1, 0, 1, 0, 1 };

			teste.add(teste1);
			teste.add(teste2);
			teste.add(teste3);
			 //teste.add(teste4);
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

	public void boardLoopTimer() {
		timer = new Timer();
		timer.schedule(new boardLoop(), 0, 1000 / 30);
	}

	private class boardLoop extends java.util.TimerTask {
		public void run() {
			drawing.setColor(Color.white);
			try {
				render();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strategy.show();
		}
	}

	/*
	 * Responsible for rendering the board screen based on the RoadBoard.
	 */
	private boolean render() throws IOException {

		/*
		 * Every time a new state is available, the shapes are rendered on the
		 * screen. A new state is available when a new instruction is pushed. A
		 * single render() call handles all the animation of any shape from the
		 * actual position to the final position (as defined by the current
		 * instruction.
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
								new ShapeComponent(
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

			road.newStateAvailable = false;
		}
		return true;

	}

	/*
	 * Animation is done here.
	 */
	public class ShapeComponent extends JComponent implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Rectangle r;
		int key;
		double currentPosition, nextPosition, nextPositionX, nextPositionY,
				currentPosX, currentPosY;

		int xSpeed = 1;
		int ySpeed = 1;
		Color color;

		javax.swing.Timer tm = new javax.swing.Timer(10, this);

		public ShapeComponent(int key, double currentPosition,
				double nextPosition, double currentPosX, double currentPosY,
				double nextPositionX, double nextPositionY, Color color,
				Graphics2D drawing) {

			super();
			r = new Rectangle(0, 0, 50, 50);

			setBounds((int) currentPosX + 20, (int) currentPosY + 20, 20, 20);
			this.color = color;
			this.key = key;
			this.currentPosition = currentPosition;
			this.nextPosition = nextPosition;
			this.currentPosX = currentPosX;
			this.currentPosY = currentPosY;
			this.nextPositionX = nextPositionX + 30;
			this.nextPositionY = nextPositionY + 20;
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
					|| currentPosition == 12 || currentPosition == 20
					|| currentPosition == 21) {
				y = y - 3;
				if (getY() != nextPositionY) {
					setLocation(x, y);
					repaint();
				} else {
					tm.stop();
					completed.put(key, true);
					cars.get(key).currentPos = cars.get(key).nextPosition;
				}
			}
			if (currentPosition == 6 || currentPosition == 9
					|| currentPosition == 16) {

				y = y + 3;
				if (getY() != nextPositionY) {
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
					this.nextPositionX = position[13 * 2] * 60;
					this.nextPositionY = position[13 * 2 + 1] * 60;
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
					this.nextPositionX = position[15 * 2] * 60;
					this.nextPositionY = position[15 * 2 + 1] * 60;
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
					this.nextPositionX = position[17 * 2] * 60;
					this.nextPositionY = position[17 * 2 + 1] * 60;
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
					this.nextPositionX = position[19 * 2] * 60;
					this.nextPositionY = position[19 * 2 + 1] * 60;
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
