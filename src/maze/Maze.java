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
	private Location agentLocation;

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
		int width = (int) (size.getWidth() / map.length);
		int height = (int) (size.getHeight() / map[0].length);
		for (int r = 0; r < map.length; r++) {
			for (int c = 0; c < map[r].length; c++) {
				g.setColor(map[r][c]);
				g.fillRect(r * width, c * height, width, height);
				g.setColor(Color.RED);
				if (false && map.length <= 10 && map[r].length <= 10)
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
						g.drawString("U " + actionScores[0], r * width, c * height + 10);
						g.drawString("D " + actionScores[1], r * width, c * height + 20);
						g.drawString("L " + actionScores[2], r * width, c * height + 30);
						g.drawString("R " + actionScores[3], r * width, c * height + 40);
					} catch (NullPointerException e) {
					}
			}
		}
		g.setColor(Color.BLUE);
		g.fillRect(agentLocation.getRow() * width, agentLocation.getCol() * height, width, height);
		g.dispose();
	}

	public void setAgentLocation(Location location) {
		agentLocation = location;
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
