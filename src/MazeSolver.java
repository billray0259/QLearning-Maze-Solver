import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeSolver extends Agent {
	private Location location;
	private Location startLocation;
	private Maze maze;

	public MazeSolver(Maze maze, Location location) {
		memory = new HashMap<String, Double>();
		actionHistory = new ArrayList<String>();

		this.location = location;
		startLocation = location;
		this.maze = maze;
	}

	@Override
	public void update() {
		stepsToReward++;
		Location lastLocation = location;
		switch (nextAction(new State(location.getRow(), location.getCol()))) {
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
		if (maze.isValid(location) && maze.get(location).equals(Color.YELLOW)) {
			giveReward(100);
			actionHistory.clear();
			System.out.println(stepsToReward);
			stepsToReward = 0;
			location = startLocation;
			maze.set(lastLocation, Color.WHITE);
			maze.set(startLocation, Color.BLUE);
		} else if (!maze.isValid(location) || !maze.isEmpty(location)) {
			// giveShortTermReward(-0.1);
			location = lastLocation;
		} else {
			// giveShortTermReward(-0.025);
			maze.set(lastLocation, Color.WHITE);
			maze.set(location, Color.BLUE);
		}
	}

	public Location getLocation() {
		return location;
	}
}
