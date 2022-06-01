package calc;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IncorrectInputException {

		System.out.print("Type expression to calculate: ");
		Scanner str = new Scanner(System.in);
		System.out.println(calc(str.nextLine()));
		str.close();
	}

	public static String calc(String input) throws IncorrectInputException {

		String output = "";
		String numeralSystem = "";

		// check whether there is more than one operation
		int totalSignCount = 0;
		for (char ch : "+-/*".toCharArray()) {

			int signCount = input.length() - input.replace(ch + "", "").length();
			totalSignCount += signCount;
			if (totalSignCount > 1) {
				throw new IncorrectInputException("Invalid input format: more than one operation!");
			}

		}
		
		// check whether the input contains arithmetic operation
		if ( !input.contains("+") && !input.contains("-") && !input.contains("*") && !input.contains("/") ) {
			throw new IncorrectInputException("Invalid input format: you must specify arithmetic operation!");
		}

		for (char ch : "+-/*".toCharArray()) {
		
			if (input.contains(ch + "")) {
	
				String delimiter = "";
				
				switch (ch) {
					case '+': delimiter = "\\+";
						break;
					case '-': delimiter = "-";
						break;
					case '/': delimiter = "/";
						break;
					case '*': delimiter = "\\*";
						break;
				}
				
				String[] elements = input.split(delimiter);
				
				// check whether there are exactly two operands in the input
				if (elements.length != 2) {
					throw new IncorrectInputException("Invalid input format: it must contain 2 operands and 1 operation!");
				}
				
				Integer operand1 = 0;
				Integer operand2 = 0;
				
				try {
					operand1 = Integer.parseInt(elements[0].trim());
					operand2 = Integer.parseInt(elements[1].trim());
					numeralSystem = "arabic";
				} catch (NumberFormatException e) {	
					operand1 = Roman.valueOf(elements[0].trim()).getArabic();
					operand2 = Roman.valueOf(elements[1].trim()).getArabic();
					numeralSystem = "roman";
				}
				
				// check whether the range is correct
				if ( operand1 > 10 || operand1 < 1 || operand2 > 10 || operand2 < 1 ) {
					throw new IncorrectInputException("Invalid input format: you can use numbers only from 1(I) to 10(X)!");
				}
				
				switch (delimiter) {
					case "\\+": output = "" + ( operand1 + operand2 );
						break;
					case "-": 	output = "" + ( operand1 - operand2 );
						break;
					case "/": 	output = "" + ( operand1 / operand2 );
						break;
					case "\\*": output = "" + ( operand1 * operand2 );
						break;
				}
				
				break;
	
			} else if (!input.contains(ch + "")) {
				continue;
			}
		}
		
		if (numeralSystem == "roman") {
			if ( Integer.parseInt(output) < 1) {
				throw new IncorrectInputException("Invalid input format: Roman numerals can not be negative or zero!");
			}
			output = "" + Roman.findRomanByArabic(Integer.parseInt(output));
		}

		return output;

	}

}
