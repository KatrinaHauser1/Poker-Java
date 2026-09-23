import java.util.Random;
import java.util.Arrays;


public class cards implements Comparable<cards> {

	final int CARDS_IN_DECK = 52;
	final int CARDS_DRAWN = 7;
	final int NUM_COMBINATIONS = 21;

	String card;
	int value;
	String suit;
	cards[] myHand;
		
	public cards(String card, int value, String suit){
		this.card = card;
		this.value = value;
		this.suit = suit;
	}

	@Override
	public int compareTo(cards other) {
		if (this.value != other.value) {
			return Integer.compare(this.value, other.value); 
		}
		return Integer.compare(getSuitLevel(this.suit), getSuitLevel(other.suit));
	}

	public int getValue() {
		return this.value;
	}

	public String getSuit() {
		return this.suit;
	}

	public cards[] getMyHand() {
	    return this.myHand;
	}
	
	// order of the suits for tie-breaking
	public int getSuitLevel(String suit) {

		switch (suit) {
	        case "D":
	        	return 1;
	        case "C":
	                return 2;
	        case "H":
	                return 3;
	        case "S":
	                return 4;
	        }

	        return 0;
	        }


	public cards[] initializeStack(){

		String[] cardStrings = {" 2H", " 3H", " 4H", " 5H", " 6H", " 7H", " 8H", " 9H", "10H", " JH", " QH", " KH", " AH", 
	      			   " 2D", " 3D", " 4D", " 5D", " 6D", " 7D", " 8D", " 9D", "10D", " JD", " QD", " KD", " AD", 
	    			   " 2C", " 3C", " 4C", " 5C", " 6C", " 7C", " 8C", " 9C", "10C", " JC", " QC", " KC", " AC", 
	   			   " 2S", " 3S", " 4S", " 5S", " 6S", " 7S", " 8S", " 9S", "10S", " JS", " QS", " KS", " AS"
		};
		
		cards[]	myStack = new cards[CARDS_IN_DECK];
		for (int i = 0; i < cardStrings.length;i++){
			int value = 0;
			switch ( cardStrings[i].substring(0,2)){
				case " J":
					value = 11;
					break;
				case " Q":
					value = 12;
					break;
				case " K":
					value = 13;
					break;
				case " A":
					value = 14;
					break;
				case "10":
				 	value = 10;
					break;
				default:
					value = Integer.parseInt(cardStrings[i].substring(1,2));
					break;
			}
			myStack[i] = new cards(cardStrings[i], value, cardStrings[i].substring(2));

		}


		return myStack;
	}


	public cards[] shuffleStack(cards[] myStack) {

		cards[] myShuffledStack = new cards[CARDS_IN_DECK];
		for (int i = 0; i < CARDS_IN_DECK; i++){
			while (true){
				int newPos = (int)(Math.random() * CARDS_IN_DECK);
				if (myShuffledStack[newPos] == null){
					myShuffledStack[newPos] = myStack[i];
					break;
				}
			}
		}
		return myShuffledStack;

	}


	public cards[] drawHand(cards[] myStack) {

	        cards[] myHand = new cards[CARDS_DRAWN];
		
		for (int i = 0; i < CARDS_DRAWN; i++) {
			myHand[i] = myStack[i];
		}	
	        return myHand;

	}



	public void printStack(cards[] myStack){

		System.out.println("        ✦✦✦ Shuffled " + CARDS_IN_DECK + " card deck: ✦✦✦");
		for (int i = 0; i< myStack.length;i++) {
			System.out.print(myStack[i].card + " ");
			if ((i+1)%9 == 0) { System.out.println(""); }
		}
		System.out.println("\n✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");

	}

	public void printHand(cards[] myHand){

	        System.out.println("\n✦✦✦✦✦✦✦✦✦✦✦✦✦✦ Your Hand: ✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
	        for (int i = 0; i< CARDS_DRAWN;i++) {
	                System.out.print(myHand[i].card + " ");
	        }
	        System.out.println("\n✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");

	}

	public void deliverStackandHand(){

		cards[] myStack = initializeStack();
		myStack = shuffleStack(myStack);
		myHand = drawHand(myStack);
		printStack(myStack);
		printHand(myHand);

	}

	// grade every hand and break ties
	public void evaluateHand(cards[] myHand) {
		cards[][] myCombinations = getCombinations(myHand);
		printCombinations(myCombinations);


		ratings[] handRatings = new ratings[NUM_COMBINATIONS];
	        for (int i = 0; i < NUM_COMBINATIONS; i++) {
	        	handRatings[i] = new ratings(myCombinations[i]);
		}
		
		Arrays.sort(handRatings);

		System.out.println("\n✦✦✦✦✦✦✦✦✦✦✦✦✦HIGH HAND ORDER✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
		for (int i = 0; i < NUM_COMBINATIONS; i++) {
	    
	    		for (int j = 0; j < 5; j++) {
	        		System.out.print(handRatings[i].playedHand[j].card + " ");
	    		}
			System.out.print(" | "  + handRatings[i].extraCards[0].card + " " + handRatings[i].extraCards[1].card);
			String Hand = handRatings[i].convertScoreToHand(handRatings[i].score);
			System.out.print(" --- " + Hand);
	    		System.out.println();
		}
		
		System.out.println("\n✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
		
	}


	// get all x chose y combinations for all card hands
	public cards[][] getCombinations(cards[] myHand) {

	    cards[][] myCombinations = new cards[NUM_COMBINATIONS][CARDS_DRAWN];

	    int combination = 0;

	    for (int i = 0; i < CARDS_DRAWN; i++) {
	        for (int j = i + 1; j < CARDS_DRAWN; j++) {

	            int index = 0;

	            for (int k = 0; k < CARDS_DRAWN; k++) {
	                if (k != i && k != j) {
	                    myCombinations[combination][index] = myHand[k];
	                    index++;
	                }
	            }

	            myCombinations[combination][5] = myHand[i];
	            myCombinations[combination][6] = myHand[j];

	            combination++;
	        }
	    }

	    return myCombinations;
	}


	     
	public void printCombinations(cards[][] myCombinations) {

		System.out.println("\n✦✦✦✦✦✦✦✦✦✦ Hand Combinations: ✦✦✦✦✦✦✦✦✦✦✦✦✦");
		for (int i = 0; i < myCombinations.length; i++) {
			for (int j = 0; j < myCombinations[i].length; j++) {
				System.out.print(myCombinations[i][j].card + " ");
				if (j == 4) {
					System.out.print(" | ");
				}
			}
			System.out.println("");
		}
	}

}
