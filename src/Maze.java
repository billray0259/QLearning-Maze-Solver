import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

public class Maze {
	private Random random;
	private Color[][] map;

	public Maze(long seed) {
		random = new Random(seed);
		map = new Color[8][8];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = random.nextDouble() < 0.5 ? Color.WHITE : Color.BLACK;
			}
		}
	}

	public void draw(Graphics graphics, Dimension size) {
		Graphics g = graphics.create();
		for (int r = 0; r < map.length; r++) {
			for (int c = 0; c < map[r].length; c++) {
				g.setColor(map[r][c]);
				int width = (int) (size.getWidth() / map.length);
				int height = (int) (size.getHeight() / map[r].length);
				g.fillRect(r * width, (map[r].length - c) * height, width, height);
			}
		}
	}
}
