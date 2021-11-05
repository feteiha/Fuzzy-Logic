import java.util.ArrayList;

public class Variable {
	private String name;
	private Type type;
	public ArrayList<FuzzySet> sets;
	public double crisp;

	public Variable(String name, Type type) {
		this.name = name;
		this.type = type;
		sets = new ArrayList<FuzzySet>();
		crisp = 0.0;
	}
	
	public void addFuzzySet(String name, Type type, ArrayList<Double> values) {
		sets.add(new FuzzySet(name, type, values));
	}
	
	public double getMembership(String setName) {
		for (FuzzySet v : sets) {
			if (v.name.equals(setName))
				return v.membershipValue;
		}
		return 0;
	}
	
	public void setMembership(String setName, double membership) {
		for (FuzzySet v : sets) {
			if (v.name.equals(setName))
				v.membershipValue = membership;
		}
	}
	
	void setCrispValue(double crisp){
		this.crisp = crisp;
	}
	
	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}
	
}
