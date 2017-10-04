package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import qlearning.Action;
import qlearning.Agent;
import qlearning.State;
import qlearning.Util;

public class Maze {
	private Random random;
	private Color[][] map;

	public Maze(long seed) {
		random = new Random(seed);
		map = new Color[Main.row][Main.col];
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
				int rCenter = r * width / 2;
				int cCenter = c * height / 2;
				g.setColor(Color.RED);
				if (false)
				try {
						double[] actionScores = new double[Action.values().length];
						for (int i = 0; i < actionScores.length; i++) {
							Action action = Action.values()[i];
							String entry = new State(r, c).toString() + action.toString();
							if (Agent.memory.get(entry) == null) {
								actionScores[i] = 0;
							}
							actionScores[i] = Agent.memory.get(entry);
						}
						double[] actionProbabilities = Util.softmax(actionScores);
						g.drawString("U " + actionProbabilities[0], r * width, c * height + 10);
						g.drawString("D " + actionProbabilities[1], r * width, c * height + 20);
						g.drawString("L " + actionProbabilities[2], r * width, c * height + 30);
						g.drawString("R " + actionProbabilities[3], r * width, c * height + 40);
					} catch (NullPointerException e) {
					}
				// try {
				// g.drawString(Math.round(Agent.memory.get(new State(r, c).toString() + "UP"))
				// + "", rCenter,
				// c * height - 20);
				// } catch (NullPointerException e) {
				// g.drawString("UP", rCenter,
				// c * height - 20);
				// }
				// try {
				// g.drawString(Math.round(Agent.memory.get(new State(r, c).toString() +
				// "DOWN")) + "", rCenter,
				// (1 + c) * height);
				// } catch (NullPointerException e) {
				// g.drawString("DOWN", rCenter,
				// (1 + c) * height);
				// }
				// try {
				// g.drawString(Math.round(Agent.memory.get(new State(r, c).toString() +
				// "LEFT")) + "", r * width,
				// cCenter);
				// } catch (NullPointerException e) {
				// g.drawString("LEFT", r * width,
				// cCenter);
				// }
				// try {
				// g.drawString(Math.round(Agent.memory.get(new State(r, c).toString() +
				// "RIGHT")) + "",
				// (r + 1) * width - 25, cCenter);
				// } catch (NullPointerException e) {
				// g.drawString("RIGHT",
				// (r + 1) * width - 25, cCenter);
				// }
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
