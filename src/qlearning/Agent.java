package qlearning;

import java.util.List;
import java.util.Map;

public abstract class Agent {
	// TODO change memory to private... I only have it like this so Maze.java can
	// see it.
	public static Map<String, Double> memory;
	protected List<String> actionHistory;
	protected int stepsToReward;
	protected double stepsToRewardMovingAverage;

	public abstract void update();

	public Action nextAction(State state) {
		double[] actionScores = new double[Action.values().length];
		for (int i = 0; i < actionScores.length; i++) {
			Action action = Action.values()[i];
			String entry = state.toString() + action.toString();
			if (memory.get(entry) == null) {
				memory.put(entry, getInitialScore());
			}
			actionScores[i] = memory.get(entry);
		}
		double[] actionProbabilities = Util.softmax(actionScores);
		// System.out.println(Arrays.toString(actionProbabilities));
		double choice = Math.random();
		int actionIndex = Action.values().length - 1;
		for (int i = 0; i < actionProbabilities.length; i++) {
			choice -= actionProbabilities[i];
			if (choice <= 0) {
				actionIndex = i;
				break;
			}
		}
		Action action = Action.values()[actionIndex];
		addEntryToHistory(state.toString() + action.toString());
		return action;
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
				memory.put(entry, getInitialScore());
			}
			memory.put(entry, memory.get(entry) * 0.9 + 0.2 * reward * Math.pow(i / (double) actionHistory.size(), 2));
		}
	}

	public void giveShortTermReward(double reward) {
		if (actionHistory.size() > 0) {
			String entry = actionHistory.get(actionHistory.size() - 1);
			if (memory.get(entry) == null) {
				memory.put(entry, getInitialScore());
			}
			memory.put(entry, memory.get(entry) + reward);
		}
	}

	private double getInitialScore() {
		return 0;
	}
}
