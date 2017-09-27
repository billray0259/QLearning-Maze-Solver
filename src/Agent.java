import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class Agent {
	private Map<Entry<State, Action>, Double> memory;
	private Stack<Entry<State, Action>> actionHistory;

	public Agent() {
		memory = new HashMap<Map.Entry<State, Action>, Double>();
		actionHistory = new Stack<Entry<State, Action>>();
	}

	public Action nextAction(State state) {
		double maxScore = Integer.MIN_VALUE;
		Action bestAction = null;
		for (Action action : Action.values()) {
			Entry<State, Action> entry = new SimpleEntry<State, Action>(state, action);
			if (memory.get(entry) == null) {
				memory.put(entry, Math.random());
			}
			if (memory.get(entry) > maxScore) {
				maxScore = memory.get(entry);
				bestAction = action;
			}
		}
		if (bestAction != null || Math.random() < 0.25) {
			actionHistory.push(new SimpleEntry<>(state, bestAction));
			return bestAction;
		} else {
			Action randomAction = Action.values()[(int) (Math.random() * Action.values().length)];
			actionHistory.push(new SimpleEntry<>(state, randomAction));
			return randomAction;
		}
	}

	public void giveReward(double reward) {
		double rewardLossPerAction = reward / actionHistory.size();
		for (Entry<State, Action> entry : actionHistory) {
			if (memory.get(entry) == null) {
				memory.put(entry, Math.random());
			}
			memory.put(entry, memory.get(entry) + (reward -= rewardLossPerAction));
		}
	}
}
