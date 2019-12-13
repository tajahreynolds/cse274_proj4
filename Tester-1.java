import java.util.Scanner;
public class Tester {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String userInfix = "";
		int input;
		InfixExpression infix;

		System.out.print("Infix Expression: ");
		
		userInfix = in.nextLine();
		try {
			infix = new InfixExpression(userInfix);

			System.out.print("Enter 1 to get postfix expression, "
					+ "2 to evaluate postfix expression or "
					+ "3 to directly evaluate the infix expression: ");
			input = in.nextInt();
			
			switch (input) {
				case (1):
					System.out.println(infix.getPostfixExpression());
					break;
				case (2):
					System.out.println(infix.evaluatePostfix());
					break;
				case (3):
					System.out.println(infix.evaluate());

				default: break;
			}
		} catch (IllegalArgumentException e){
			System.out.println("Invalid infix expression.");
		} finally {
			in.close();
		}
	}
}