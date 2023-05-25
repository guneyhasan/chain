import java.util.Random;

public class Board {

	static char[][] boardSeed(int seed){
		Random random = new Random(seed);
		
		char[][] board = new char[19][31];
		//if i and j both are even numbers then a random number is added from the specified seed
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(i%2 == 0 && j %2 == 0) {
					int randNum = random.nextInt(4) + 1;
					board[i][j] =Character.forDigit(randNum, 10);
				}
				else {
					board[i][j] =' ';
				}
				
			}
		}
		
		return board;
	}
}
