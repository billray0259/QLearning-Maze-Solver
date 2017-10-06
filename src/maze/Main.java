package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static int row = 100;
	public static int col = 100;

	private static Maze maze;
	private static JFrame frame;
	private static Artist artist;
	private static MazeSolver agent;
	private static boolean fastMode;

	public static void main(String[] args) {
		maze = new Maze(0);
		frame = new JFrame();
		artist = new Artist();
		frame.add(artist);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == ' ') {
					fastMode = !fastMode;
				}

			}
		});
		frame.setVisible(true);
		maze.set(new Location(row / 2, col / 2), Color.YELLOW);
		// maze.set(new Location(50 + (int) (Math.random() * 50), 50 + (int)
		// (Math.random() * 50)), Color.YELLOW);
		// maze.set(new Location(50 + (int) (Math.random() * 50), 50 + (int)
		// (Math.random() * 50)), Color.YELLOW);
		agent = new MazeSolver(maze, new Location(0, 0));
		maze.setAgentLocation(agent.getLocation());

		while (true) {
			if (!fastMode) {
				try {
					Thread.sleep(16 * 4);
				} catch (InterruptedException e) {

				}
			}
			artist.repaint();
			artist.revalidate();
			agent.update();
		}

	}

	static class Artist extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			maze.draw(g, new Dimension((int) frame.getSize().getWidth() - 50, (int) frame.getSize().getHeight() - 50));
		}
	}
}
