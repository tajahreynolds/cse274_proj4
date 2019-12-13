
public class InfixExpression {

	private String infix;

	/**
	 * Creates an infix expression.
	 * 
	 * @param infix the expression.
	 * @throws IllegalArgumentException if the infix String parameter is invalid.
	 */
	public InfixExpression(String infix) {
		this.infix = infix;
		clean();
		if (!isValid()) throw new IllegalArgumentException("Invalid infix expression.");
	}

	/**
	 * Returns the infix expression.
	 * 
	 * @return the infix expression.
	 */
	public String toString() {
		return infix;
	}

	/**
	 * Checks if the infix expression has balanced parentheses.
	 * 
	 * @return true if the infix expression has balanced parentheses, and false
	 *         otherwise.
	 */
	private boolean isBalanced() {
		ArrayStack<Character> stack = new ArrayStack<Character>();
		
		int count = infix.length();
		boolean balanced = true;
		int index = 0;
		char nextCharacter = ' ';
		
		while (balanced && (index < count)) {
			nextCharacter = infix.charAt(index);
			switch (nextCharacter) {
				case '(':
					stack.push(nextCharacter);
					break;
				case ')':
					if (stack.isEmpty()) {
						balanced = false;
					} else { 
						balanced = (stack.pop() == '(');
					}
					break;
				default:
					break;
			}
			index++;
		}
		
		if(!stack.isEmpty())
			balanced = false;
		return balanced;
		
	}

	/**
	 * Checks if the infix expression is valid in all respects.
	 * 
	 * @return true if the infix expression is valid, and false otherwise.
	 */
	private boolean isValid() {
		if (!isBalanced()) return false;
		
		for (int i = 0; i < infix.length(); i++) {
			if (i == 0 && !(isValidNum(i) || infix.charAt(i) == '(')) return false; // expression must start with number or (
			if (i == infix.length()-1 && !(isValidNum(i) || infix.charAt(i) == ')')) return false; // must end with number or )
			
			if (isValidNum(i)) {
				if ((i+1 < infix.length()) && (infix.charAt(i+1) != ' ' && !isValidNum(i+1))) {
					return false;
				}
			} else if (isValidOpr(i)) {
				if (i+1 < infix.length() && (infix.charAt(i+1) != ' ')) {
					return false;
				}
				if (i+2 < infix.length() && (!isValidNum(i+2) && !(infix.charAt(i+2) == '(' || infix.charAt(i+2) == ')'))) {
					if (infix.charAt(i) != ')')
						return false;
				}
			} else if (infix.charAt(i) == ' ') {
				if (i+1 < infix.length() && !(isValidNum(i+1) || isValidOpr(i+1))) {
					return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}

	private boolean isValidNum(int i, String str) {
		boolean flag = false;
		char curr = str.charAt(i);
		if (curr == '0' || curr == '1' || curr == '2' || curr == '3' || curr == '4' || curr == '5' || curr == '6'
				|| curr == '7' || curr == '8' || curr == '9') {
			flag = true;
		}
		return flag;
	}
	
	private boolean isValidNum(int i) {
		boolean flag = false;
		char curr = infix.charAt(i);
		if (curr == '0' || curr == '1' || curr == '2' || curr == '3' || curr == '4' || curr == '5' || curr == '6'
				|| curr == '7' || curr == '8' || curr == '9') {
			flag = true;
		}
		return flag;
	}
	
	private boolean isValidOpr(int i) {
		boolean flag = false;
		char curr = infix.charAt(i);
		if (curr == '+' || curr == '-' || curr == '*' || curr == '/' || curr == '%' || curr == '^' || curr == '(' || curr == ')') {
			if (curr == '(' && i-2 >= 0 && !(isValidOpr(i-2)))
				flag = false;
			else
				flag = true;
		}
		return flag;
	}
	
	private boolean isValidOpr(int i, String str) {
		boolean flag = false;
		char curr = str.charAt(i);
		if (curr == '+' || curr == '-' || curr == '*' || curr == '/' || curr == '%' || curr == '^' || curr == '(' || curr == ')') {
			if (curr == '(' && (i-2 >= 0 && !(isValidOpr(i-2, str))))
				flag = false;
			else if (curr == ')' && (i-2 >= 0 && !(isValidNum(i-2, str))))
				flag = false;
			else
				flag = true;
		}
		return flag;
	}

	/**
	 * Cleans the expression.
	 */
	private void clean() {
		String letter;
		for (int i = 0; i < infix.length(); i++) {
			letter = infix.substring(i, i + 1);

			switch (letter) {
			case " ":
				removeSpaces(i);
				break;

			case "+":
			case "-":
			case "/":
			case "*":
			case "%":
			case "^":
			case "(":
			case ")":
				if (!(i + 1 >= infix.length()) && infix.charAt(i + 1) != ' ')
					infix = infix.substring(0, i + 1) + " " + infix.substring(++i);
				break;

			case "0":
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
			case "6":
			case "7":
			case "8":
			case "9":
				addSpaceAfterNum(i);
			}
		}

		infix = infix.trim();
	}

	private void removeSpaces(int i) {
		int count = 0;
		for (int j = i + 1; j < infix.length(); j++) {
			if (infix.charAt(j - count) == ' ') {
				infix = infix.substring(0, i) + infix.substring(j - count);
				count++;
			} else {
				break;
			}
		}
	}

	private void addSpaceAfterNum(int i) {
		if (!(i + 1 >= infix.length()) && (infix.charAt(i + 1) == '+' || infix.charAt(i + 1) == '-'
				|| infix.charAt(i + 1) == '/' || infix.charAt(i + 1) == '*' || infix.charAt(i + 1) == '%'
				|| infix.charAt(i + 1) == '^' || infix.charAt(i + 1) == '(' || infix.charAt(i + 1) == ')')) {
			infix = infix.substring(0, i + 1) + " " + infix.substring(++i);
		}
	}

	/**
	 * Returns the postfix expression that corresponds to the infix expression.
	 * 
	 * @return the corresponding postfix expression.
	 */
	public String getPostfixExpression() {
		ArrayStack<Character> stack = new ArrayStack<Character>();
		String postfix = "";
		int count = infix.length();
		int index = 0;
		char topOperator =  ' ';
		
		while (index < count) {
			char next = infix.charAt(index);
			
			if (isValidNum(index)) { // add str to params
				postfix += next;
				
			} else if (next == '^' || next == '(') {
				postfix += " ";
				stack.push(next);
				
			} else if (next == '+' || next == '-' || next == '*' || next == '/' || next == '%') {
				while (!stack.isEmpty() && getPrecedence(next) <= getPrecedence(stack.peek())) {
					postfix += " " + stack.pop();
				}
				
				stack.push(next);
				postfix += " ";
			} else if (next == ')') {
				topOperator = stack.pop();
				while (topOperator != '(') {
					postfix += " " + topOperator;
					topOperator = stack.pop();
				}
			} else {
				// Do nothing for spaces
			}
			index++;
		}
		
		while (!stack.isEmpty()) {
			topOperator = stack.pop();
			postfix += " " + topOperator;
		}
		
		return postfix;
	}
	
	private int getPrecedence(char c) {
		int precedence;
		
		if (c == '+' || c == '-') {
			precedence = 0;
		} else if (c == '*' || c == '/' || c == '%') {
			precedence = 1;
		} else {
			precedence = -1;
		}
		
		return precedence;
	}

	/**
	 * Evaluates the postfix expression.
	 * 
	 * @return the result of the calculation.
	 */
	public int evaluatePostfix() {
		ArrayStack<Integer> valueStack = new ArrayStack<Integer>();
		int index = 0;
		String postfix = this.getPostfixExpression();
		int count = postfix.length();
		char curr = ' ';
		int operandTwo;
		int operandOne;
		int result = 0;
		String temp = "";
		
		while (index < count) {
			curr = postfix.charAt(index);
			if (isValidNum(index, postfix)) {
				while (index < count && isValidNum(index, postfix)) {
					temp += postfix.charAt(index++);
				}
				valueStack.push((Integer.parseInt(temp)));
				temp = "";
			} else if (isValidOpr(index, postfix)) {
				if (curr != '(' && curr != ')') {
					operandTwo = valueStack.pop();
					operandOne = valueStack.pop();
					switch (curr) {
						case '+':
							result = operandOne + operandTwo;
							break;
						case '-':
							result = operandOne - operandTwo;
							break;
						case '*':
							result = operandOne * operandTwo;
							break;
						case '/':
							result = operandOne / operandTwo;
							break;
						case '^':
							result = operandOne ^ operandTwo;
							break;
						case '%':
							result = operandOne % operandTwo;
							break;
						default: break;
					}
					valueStack.push(result);
				}
			}
			index++;
		}		
		return valueStack.peek();
	}

	/**
	 * Evaluates the infix expression.
	 * 
	 * @return the result of the calculation.
	 */
	public int evaluate() {
		ArrayStack<Character> operatorStack = new ArrayStack<Character>();
		ArrayStack<Integer> valueStack = new ArrayStack<Integer>();
		int index = 0;
		int count = infix.length();
		char curr;
		String temp = "";
		int secondOperand;
		int firstOperand;
		char topOperator;
		
		while (index < count) {
			
			curr = infix.charAt(index);
			if(isValidNum(index)) {
				
				while (index < count && isValidNum(index)) {
					temp += infix.charAt(index++);
				}
				
				valueStack.push((Integer.parseInt(temp)));
				temp = "";
				
			} else if (isValidOpr(index)) {
				
				if (curr == '^' || curr == '(') {
					operatorStack.push(curr);
				} else if (curr == ')') {
					
					topOperator = operatorStack.pop();
					while (topOperator != '(') {
						secondOperand = valueStack.pop();
						firstOperand = valueStack.pop();
						switch (topOperator) {
							case '+': 
								valueStack.push(firstOperand + secondOperand);
								break;
							case '-': 
								valueStack.push(firstOperand - secondOperand);
								break;
							case '*': 
								valueStack.push(firstOperand * secondOperand);
								break;
							case '/': 
								valueStack.push(firstOperand / secondOperand);
								break;
							case '%': 
								valueStack.push(firstOperand % secondOperand);
								break;
							case '^': 
								valueStack.push(firstOperand ^ secondOperand);
								break;
							
						}
						topOperator = operatorStack.pop();
					}
					
				} else {
					
					while (!operatorStack.isEmpty() && (getPrecedence(curr) <= getPrecedence(operatorStack.peek()))) {
						secondOperand = valueStack.pop();
						firstOperand = valueStack.pop();
						switch (operatorStack.pop()) {
						case '+': 
							valueStack.push(firstOperand + secondOperand);
							break;
						case '-': 
							valueStack.push(firstOperand - secondOperand);
							break;
						case '*': 
							valueStack.push(firstOperand * secondOperand);
							break;
						case '/': 
							valueStack.push(firstOperand / secondOperand);
							break;
						case '%': 
							valueStack.push(firstOperand % secondOperand);
							break;
						case '^': 
							valueStack.push(firstOperand ^ secondOperand);
							break;
						
						}
					}
					operatorStack.push(curr);
				}
			}
			index++;
		}
		
		while (!operatorStack.isEmpty()) {
			topOperator = operatorStack.pop();
			secondOperand = valueStack.pop();
			firstOperand = valueStack.pop();
			switch (topOperator) {
			case '+': 
				valueStack.push(firstOperand + secondOperand);
				break;
			case '-': 
				valueStack.push(firstOperand - secondOperand);
				break;
			case '*': 
				valueStack.push(firstOperand * secondOperand);
				break;
			case '/': 
				valueStack.push(firstOperand / secondOperand);
				break;
			case '%': 
				valueStack.push(firstOperand % secondOperand);
				break;
			case '^': 
				valueStack.push(firstOperand ^ secondOperand);
				break;
			
			}
		}
		
		return valueStack.peek();
	}
}
