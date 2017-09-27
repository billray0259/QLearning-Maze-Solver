import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Agent {
	private Map<Entry<State, Action>, Double> memory;
	private List<Entry<State, Action>> actionHistory;

	private Location location;
	private Location startLocation;
	private Maze maze;

	private int stepsToReward;

	public Agent(Maze maze, Location location) {
		memory = new HashMap<Map.Entry<State, Action>, Double>();
		actionHistory = new ArrayList<Entry<State, Action>>();

		this.location = location;
		startLocation = location;
		this.maze = maze;
	}

	public void update() {
		stepsToReward++;
		Location lastLocation = location;
		switch (nextAction(new State(location.getRow(), location.getCol()))) {
		case UP:
			location = new Location(location.getRow() - 1, location.getCol());
			break;
		case DOWN:
			location = new Location(location.getRow() + 1, location.getCol());
			break;
		case LEFT:
			location = new Location(location.getRow(), location.getCol() - 1);
			break;
		case RIGHT:
			location = new Location(location.getRow(), location.getCol() + 1);
			break;
		}
		if (maze.isValid(location) && maze.get(location).equals(Color.YELLOW)) {
			giveReward(100);
			actionHistory.clear();
			System.out.println(stepsToReward);
			stepsToReward = 0;
			location = startLocation;
			maze.set(lastLocation, Color.WHITE);
		} else if (!maze.isValid(location) || !maze.isEmpty(location)) {
			giveReward(-1);
			location = lastLocation;
		} else {
			maze.set(lastLocation, Color.WHITE);
			maze.set(location, Color.BLUE);
		}
	}

	public Action nextAction(State state) {
		double maxScore = Integer.MIN_VALUE;
		Action bestAction = null;
		for (Action action : Action.values()) {
			Entry<State, Action> entry = new MyEntry<State, Action>(state, action);
			if (memory.get(entry) == null) {
				memory.put(entry, Math.random());
			}
			if (memory.get(entry) > maxScore) {
				maxScore = memory.get(entry);
				bestAction = action;
			}
		}
		if (bestAction != null && Math.random() < 0.75) {
			// System.out.println(maxScore);
			actionHistory.add(new MyEntry<>(state, bestAction));
			return bestAction;
		} else {
			Action randomAction = Action.values()[(int) (Math.random() * Action.values().length)];
			actionHistory.add(new MyEntry<>(state, randomAction));
			return randomAction;
		}
	}

	public void giveReward(double reward) {
		double rewardLossPerAction = reward / actionHistory.size();
		for (int i = actionHistory.size() - 1; i >= 0; i--) {
			Entry<State, Action> entry = actionHistory.get(i);
			if (memory.get(entry) == null) {
				memory.put(entry, Math.random());
			}
			if (reward > 0)
				System.out.println(memory.get(entry));
			memory.put(entry, memory.get(entry) + reward);
			reward -= rewardLossPerAction;
		}

	}

	public Location getLocation() {
		return location;
	}
}
