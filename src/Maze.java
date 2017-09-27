import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

public class Maze {
	private Random random;
	private Color[][] map;

	public Maze(long seed) {
		random = new Random(seed);
		map = new Color[16][16];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = random.nextDouble() < 0.75 ? Color.WHITE : Color.BLACK;
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
				g.fillRect(r * width, c * height, width, height);
			}
		}
	}

	public boolean isEmpty(Location location) {
		return map[location.getRow()][location.getCol()].equals(Color.WHITE);
	}

	public Color get(Location location) {
		if (isValid(location)) {
			return map[location.getRow()][location.getCol()];
		} else {
			return null;
		}
	}

	public boolean isValid(Location location) {
		return location.getRow() >= 0 && location.getRow() < map.length && location.getCol() >= 0
				&& location.getCol() < map[location.getRow()].length;
	}

	public void set(Location location, Color color) {
		if (isValid(location)) {
			map[location.getRow()][location.getCol()] = color;
		}
	}
}
