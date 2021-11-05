import java.util.ArrayList;

public class Rule {
	
	String rule;
	ArrayList<String> variableNames;
	ArrayList<String> memberships;
	ArrayList<Character> operations;
	
	String outputVariable, membershipOutputName;
	
	
	public Rule(String rule) {
		this.rule = rule;
		variableNames = new ArrayList<String>();
		memberships = new ArrayList<String>();
		operations = new ArrayList<Character>();
		parse();
	}

	public void parse() {
		String [] split = rule.split(" ");
		
		String [] output = split[split.length-1].split("=");
		outputVariable = output[0];
		membershipOutputName = output[1];
		
		
		for (int i=0 ; i<split.length-2 ; i++) {
			if (split[i].equals("and")) {
				operations.add('^');
			} else if (split[i].equals("or")) {
				operations.add('|');
			} else {
				String[] temp = split[i].split("=");
				variableNames.add(temp[0]);
				memberships.add(temp[1]);
			}
		}
	}
	
	
}
