
public class Keypad extends Puzzle{

	Keypad(String name, String description, String solution, int maxAttempts){
		super(name, description, solution, maxAttempts);
	}
	
	public boolean check(String input){
		if(input.toLowerCase().equals(solution)){
			
			return true;
		}
		else{
			attempts--;
			return false;
		}
	}

}
