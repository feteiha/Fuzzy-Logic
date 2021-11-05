import java.util.ArrayList;

public class FuzzySet {
	public String name;
	public Type type;
	public ArrayList<Double> values;
	public double membershipValue;
	public double centroid;
	
	public FuzzySet(String name, Type type, ArrayList<Double> values) {
		this.name = name;
		this.type = type;
		this.values = values;
		double sum = 0;
		for (Double d : values) {
			sum += d;
		}
		centroid = sum / values.size();
	}
}
