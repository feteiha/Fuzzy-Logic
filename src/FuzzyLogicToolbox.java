import java.util.ArrayList;

public class FuzzyLogicToolbox {
	ArrayList<Variable> variables;
	ArrayList<Rule> rules;
	ArrayList<Double> outputMemberships = new ArrayList<Double>();
	ArrayList<Double> outputCentroids = new ArrayList<Double>();
	ArrayList<String> outputMembershipCategory = new ArrayList<String>();
	
	public FuzzyLogicToolbox() {
		variables = new ArrayList<Variable>();
		rules = new ArrayList<Rule>();
	}
	
	public void addVariable(String name, Type type) {
		variables.add(new Variable(name, type));
	}
	
	public void addRule(String rule) {
		rules.add(new Rule(rule));
	}
	
	Variable getVariable(String name) {
		for (Variable v : variables) {
			if (v.getName().equals(name))
				return v;
		}
		return null;
	}
	
	public void run() {
		fuzzification();
		inference();
		defuzzification();
	}

	private void fuzzification() {
		for (int i=0 ; i<variables.size() ; i++) {
			double crisp = variables.get(i).crisp;
			
			for (int j=0 ; j < variables.get(i).sets.size() ; j++) {
				FuzzySet set = variables.get(i).sets.get(j);
				int index = set.values.indexOf(crisp);
				if (index != -1) {
					set.membershipValue = getY(set.type, index);
				} else {
					double x1 = -1, x2 = -1;
					double y1 = -1, y2 = -1;
					for (int k=0 ; k<set.values.size()-1; k++) {
						if (crisp > set.values.get(k) && crisp < set.values.get(k+1)) {
							x1 = set.values.get(k);
							x2 = set.values.get(k+1);
							y1 = getY(set.type, k);
							y2 = getY(set.type, k+1);
						}
					}
					if (x1 == -1 && x2 == -1 && y1 == -1 && y2 == -1)
						set.membershipValue = 0;
					else 
						set.membershipValue = getValue(x1, y1, x2, y2, crisp);	
				}
			}
		}
			
		System.out.println("\nMemeberships");
		for (int i=0 ; i<variables.size() ; i++) {
			if (variables.get(i).getType() == Type.OUTPUT)
				continue;
			System.out.println("\n"+variables.get(i).getName()+ " : ");
			for (int j=0 ; j < variables.get(i).sets.size() ; j++) {
				FuzzySet set = variables.get(i).sets.get(j);
				System.out.println(set.name + " : " + set.membershipValue);
			}
		}
	}
	
	private double getValue(double x1, double y1, double x2, double y2, double crisp) {
		// y = mx + c
		double slope = (y2 - y1) / (x2 - x1);
		double c = y1 - (slope * x1);		
		return (slope * crisp) + c;
	}


	private double getY(Type type, int index) {
		if (type == Type.TRAP) {
			if (index == 0 || index == 3) {
				return 0;
			} else {
				return 1;
			}
		} else {
			if (index == 1) {
				return 1;
			} else {
				return 0;
			}
		}
	}


	private void inference() {
		for (int i=0 ; i<rules.size() ; i++) {
			double result = 0;
			if (rules.get(i).operations.size() == 0) {
				Variable v = getVariable(rules.get(i).variableNames.get(0));
				result = v.getMembership(rules.get(i).memberships.get(0));
			}
			for (int j=0 ; j<rules.get(i).operations.size() ; j++) {
				Variable v1 = getVariable(rules.get(i).variableNames.get(j));
				Variable v2 = getVariable(rules.get(i).variableNames.get(j+1));
				if (rules.get(i).operations.get(j) == '^') {
					result = Math.min(v1.getMembership(rules.get(i).memberships.get(j)), 
									  v2.getMembership(rules.get(i).memberships.get(j+1)));
				} else {
					result = Math.max(v1.getMembership(rules.get(i).memberships.get(j)), 
									  v2.getMembership(rules.get(i).memberships.get(j+1)));
				}
			}
			Variable output = getVariable(rules.get(i).outputVariable);
			for (FuzzySet s : output.sets) {
				if (s.name.equals(rules.get(i).membershipOutputName))
					outputCentroids.add(s.centroid);
			}
			outputMemberships.add(result);
			outputMembershipCategory.add(rules.get(i).membershipOutputName);
		}
		

		System.out.println("\nInference Results");
		for (int i=0 ; i<outputMemberships.size() ; i++) {
			System.out.println(outputMembershipCategory.get(i) + " : " + outputMemberships.get(i));
		}
	}

	private void defuzzification() {
		double resultUp = 0, resultDown = 0, result = 0;
		for (int i=0 ; i<outputMemberships.size() ; i++) {
			resultUp += outputMemberships.get(i) * outputCentroids.get(i);
			resultDown += outputMemberships.get(i);
		}
		result = resultUp / resultDown;
		System.out.println("\nOutput: " + result);
		int max=0;
		for (int i=1 ; i<outputMemberships.size() ; i++) {
			if (outputMemberships.get(i) > outputMemberships.get(max))
				max = i;
		}
		System.out.println(variables.get(variables.size()-1).getName() + " will be " + outputMembershipCategory.get(max));
	}
}
