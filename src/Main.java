import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		FuzzyLogicToolbox toolbox = new FuzzyLogicToolbox();
		Scanner in = new Scanner(System.in);
		System.out.print("Variables: ");
		/*
Variables: 
2
size: 2
weight: 2.5
		 */
		
		
		
		toolbox.addVariable("size", Type.INPUT);
		toolbox.getVariable("size").addFuzzySet("small", Type.TRI,  new ArrayList<Double>( Arrays.asList(0.0, 0.0, 10.0) ) );
		toolbox.getVariable("size").addFuzzySet("large", Type.TRI,  new ArrayList<Double>( Arrays.asList(0.0, 10.0, 10.0) ) );
//		toolbox.getVariable("project_funding").addFuzzySet("Medium", Type.TRAP,  new ArrayList<Double>( Arrays.asList(40.0, 60.0, 70.0, 90.0) ) );
//		toolbox.getVariable("project_funding").addFuzzySet("High", Type.TRAP,  new ArrayList<Double>( Arrays.asList(70.0, 90.0, 100.0, 100.0) ) );
//		
		toolbox.addVariable("weight", Type.INPUT);
		toolbox.getVariable("weight").addFuzzySet("small", Type.TRI,  new ArrayList<Double>( Arrays.asList(0.0, 0.0, 10.0) ) );
		toolbox.getVariable("weight").addFuzzySet("large", Type.TRI,  new ArrayList<Double>( Arrays.asList(0.0, 10.0, 10.0) ) );
//		toolbox.getVariable("team_experience_level").addFuzzySet("Expert", Type.TRI,  new ArrayList<Double>( Arrays.asList(30.0, 60.0, 60.0) ) );
		

		toolbox.addVariable("quality", Type.OUTPUT);
		toolbox.getVariable("quality").addFuzzySet("bad", Type.TRI,  new ArrayList<Double>( Arrays.asList(0.0, 0.0, 50.0) ) );
		toolbox.getVariable("quality").addFuzzySet("medium", Type.TRI,  new ArrayList<Double>( Arrays.asList(0.0, 50.0, 100.0) ) );
		toolbox.getVariable("quality").addFuzzySet("good", Type.TRI,  new ArrayList<Double>( Arrays.asList(50.0, 100.0, 100.0) ) );
		
		toolbox.addRule("size=small and weight=small -> quality=bad");
		toolbox.addRule("size=small and weight=large -> quality=medium");
		toolbox.addRule("size=large and weight=small -> quality=medium");
		toolbox.addRule("size=large and weight=large -> quality=good");
		
		int n = Integer.parseInt(in.nextLine());
		
		for (int i=0 ; i<n ; i++) {
			String line = in.nextLine();
			String [] split = line.split(": ");
			toolbox.getVariable(split[0]).setCrispValue(Double.parseDouble(split[1]));
		}
		
		toolbox.run();
		in.close();
	}

}
