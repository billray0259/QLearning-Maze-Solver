import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private static Maze maze;
	private static JFrame frame;
	private static Artist artist;
	private static Agent agent;
	private static boolean fastMode;

	public static void main(String[] args) {
		maze = new Maze(6);
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
		maze.set(new Location(0, 0), Color.BLUE);
		maze.set(new Location(10, 10), Color.YELLOW);
		agent = new Agent(maze, new Location(0, 0));

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
