import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agent {
	public static Map<String, Double> memory;
	private List<String> actionHistory;

	private Location location;
	private Location startLocation;
	private Maze maze;

	private int stepsToReward;

	public Agent(Maze maze, Location location) {
		memory = new HashMap<String, Double>();
		actionHistory = new ArrayList<String>();

		this.location = location;
		startLocation = location;
		this.maze = maze;
	}

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
			// giveReward(-.1);
			location = lastLocation;
		} else {
			maze.set(lastLocation, Color.WHITE);
			maze.set(location, Color.BLUE);
		}
	}

	public Action nextAction(State state) {
		double maxScore = Integer.MIN_VALUE;
		List<Action> bestActions = new ArrayList<>();
		for (Action action : Action.values()) {
			String entry = state.toString() + action.toString();
			if (memory.get(entry) == null) {
				memory.put(entry, 0.0);
			}
			if (memory.get(entry) > maxScore) {
				maxScore = memory.get(entry);
				bestActions.clear();
				bestActions.add(action);
			} else if (memory.get(entry) == maxScore) {
				bestActions.add(action);
			}
		}
		Action bestAction = bestActions.get((int) (Math.random() * bestActions.size()));
		if (bestAction != null && Math.random() < 0.9) {
			// System.out.println(maxScore);
			addEntryToHistory(state.toString() + bestAction.toString());
			return bestAction;
		} else {
			Action randomAction = Action.values()[(int) (Math.random() * Action.values().length)];
			addEntryToHistory(state.toString() + randomAction.toString());
			return randomAction;
		}
	}

	public void addEntryToHistory(String entry) {
		for (int i = actionHistory.size() - 1; i >= 0; i--) {
			if (actionHistory.get(i).equals(entry)) {
				actionHistory.remove(i);
			}
		}
		actionHistory.add(entry);
	}

	public void giveReward(double reward) {
		String spaces = "";
		for (int i = actionHistory.size() - 1; i >= 0; i--) {
			String entry = actionHistory.get(i);
			if (memory.get(entry) == null) {
				memory.put(entry, 0.0);
			}
			memory.put(entry, memory.get(entry) + reward * Math.pow(i / (double) actionHistory.size(), 2));
		}
	}

	public Location getLocation() {
		return location;
	}
}
