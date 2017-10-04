
public class Util {
	public static double[] softmax(double[] values) {
		double[] softmax = new double[values.length];
		double sum = 0;
		for (int i = 0; i < values.length; i++) {
			sum += Math.pow(Math.E, values[i]);
		}
		if (Double.isInfinite(sum)) {
			sum = Double.MAX_VALUE;
		}
		for (int i = 0; i < values.length; i++) {
			softmax[i] = Math.pow(Math.E, values[i]) / sum;
		}
		return softmax;
	}
}
