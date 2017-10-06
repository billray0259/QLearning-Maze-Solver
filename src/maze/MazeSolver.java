package maze;

import java.awt.Color;

import qlearning.Agent;
import qlearning.State;

public class MazeSolver extends Agent {
	private Location location;
	private Location startLocation;
	private Maze maze;
	private int stepsToGoal;
	private double lastReward;

	public MazeSolver(Maze maze, Location location) {
		this.location = location;
		startLocation = location;
		this.maze = maze;
	}

	public void update() {
		double score = 0;
		stepsToGoal++;
		Location lastLocation = location;
		switch (nextAction(new State(location.getRow(), location.getCol()), lastReward)) {
		case UP:
			location = new Location(location.getRow(), location.getCol() - 1);
			break;
		case DOWN:
			location = new Location(location.getRow(), location.getCol() + 1);
			break;
		case LEFT:
			location = new Location(location.getRow() - 1, location.getCol());
			break;
		case RIGHT:
			location = new Location(location.getRow() + 1, location.getCol());
			break;
		}
		if (!maze.isValid(location) || maze.get(location).equals(Color.BLACK)) {
			location = lastLocation;
			lastReward = -2 * 0;
		} else if (maze.get(location).equals(Color.YELLOW)) {
			location = startLocation;
			System.out.println(stepsToGoal);
			lastReward = 1 * 1;
			stepsToGoal = 0;
		} else {
			maze.setAgentLocation(location);
			lastReward = -1 * 0;
		}
	}

	public Location getLocation() {
		return location;
	}
}
