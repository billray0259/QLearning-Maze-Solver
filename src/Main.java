import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private static Maze maze;
	private static JFrame frame;
	private static Artist artist;
	
	public static void main(String[] args) {
		maze = new Maze(0);
		frame = new JFrame();
		artist = new Artist();
		frame.add(artist);
		
		
	}

	static class Artist extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			
		}
	}
}
