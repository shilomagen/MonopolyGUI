package com.monopoly.engine;

public class PairOfDice {

	private static int firstDie; 
	private static int secondDie; 

	public static void roll() {
		firstDie = (int) (Math.random() * 6) + 1;
		secondDie = (int) (Math.random() * 6) + 1;
	}

	public static int getFirstDice() {
		// Return the number showing on the first die.
		return firstDie;
	}

	public static int getSecondDice() {
		// Return the number showing on the second die.
		return secondDie;
	}

	public static int getTotal() {
		// Return the total showing on the two dice.
		return firstDie + secondDie;
	}
	public static boolean isDouble(){
		return firstDie == secondDie;
	}

}
