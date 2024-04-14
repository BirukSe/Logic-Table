package bura;

import java.util.ArrayList;
import java.util.Scanner;

public class Truthtable {

	

	
	  
	    	    public static void main(String[] args) {
	    	        
	    	        Scanner buraa=new Scanner(System.in);
	    	        System.out.println("PLEASE ENTER THE EXPRESSION");
	    	        String expression=buraa.nextLine();
	    	        
	    	        
	    	            
	    	         
	    	       
	    	            System.out.println("Truth Table for: " + expression);
	    	            Truthtable.generateTruthTable(expression);
	    	            System.out.println();
	    	        
	    	    }
	    	

	    	
	     
	    
	



    
    public static void generateTruthTable(String expression) {
        // Remove whitespace from the expression
        expression = expression.replaceAll("\\s", "");

        
        ArrayList<String> variables = extractVariables(expression);

        
        ArrayList<String> header = generateTableHeader(variables, expression);
        System.out.println("Truth Table:");
        printRow(header);

        
        ArrayList<ArrayList<Integer>> values = generateTruthValues(variables);

        
        for (ArrayList<Integer> row : values) {
            ArrayList<String> rowOutput = new ArrayList<>();
            rowOutput.addAll(row.stream().map(String::valueOf).toList());
            int result = evaluateExpression(expression, variables, row);
            rowOutput.add(String.valueOf(result));
            printRow(rowOutput);
        }
    }

    
    private static ArrayList<String> extractVariables(String expression) {
        ArrayList<String> variables = new ArrayList<>();
        String[] parts = expression.split("[&|!()^]+");
        for (String part : parts) {
            if (!part.isEmpty() && !variables.contains(part)) {
                variables.add(part);
            }
        }
        return variables;
    }

    
    private static ArrayList<String> generateTableHeader(ArrayList<String> variables, String expression) {
        ArrayList<String> header = new ArrayList<>(variables);
        header.add(expression);
        return header;
    }

    
    private static ArrayList<ArrayList<Integer>> generateTruthValues(ArrayList<String> variables) {
        ArrayList<ArrayList<Integer>> values = new ArrayList<>();
        int rows = (int) Math.pow(2, variables.size());

        for (int i = 0; i < rows; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < variables.size(); j++) {
                int value = (i >> j) & 1;
                row.add(value);
            }
            values.add(row);
        }
        return values;
    }

    
    private static int evaluateExpression(String expression, ArrayList<String> variables, ArrayList<Integer> values) {
        String evaluation = expression;
        for (int i = 0; i < variables.size(); i++) {
            String variable = variables.get(i);
            int value = values.get(i);
            evaluation = evaluation.replaceAll(variable, String.valueOf(value));
        }
        return eval(evaluation);
    }

    // Function to evaluate the expression
    private static int eval(String expression) {
        // Evaluate the expression using a custom boolean expression evaluator
        // Here, we'll use a simple approach to evaluate the expression as a boolean value
        return evaluateBooleanExpression(expression) ? 1 : 0;
    }

    // Function to evaluate the boolean expression
    private static boolean evaluateBooleanExpression(String expression) {
        // Negation
        if (expression.startsWith("!")) {
            return expression.charAt(1) == '0';
        }

        // Conjunction
        if (expression.contains("&")) {
            String[] terms = expression.split("&");
            for (String term : terms) {
                if (term.equals("0")) {
                    return false;
                }
            }
            return true;
        }

        // Disjunction
        if (expression.contains("|")) {
            String[] terms = expression.split("\\|");
            for (String term : terms) {
                if (term.equals("1")) {
                    return true;
                }
            }
            return false;
        }

        // Exclusive OR (XOR)
        if (expression.contains("^")) {
            String[] terms = expression.split("\\^");
            int countOnes = 0;
            for (String term : terms) {
                if (term.equals("1")) {
                    countOnes++;
                }
            }
            return countOnes % 2 == 1;
        }

        // Implication
        if (expression.contains("->")) {
            String[] terms = expression.split("->");
            return terms[0].equals("0") || terms[1].equals("1");
        }

        // Biconditional
        if (expression.contains("<->")) {
            String[] terms = expression.split("<->");
            return terms[0].equals(terms[1]);
        }

        return false;
    }

    
    private static void printRow(ArrayList<String> row) {
        for (String value : row) {
            System.out.print(value + "\t");
        }
        System.out.println();
    }

   
}
