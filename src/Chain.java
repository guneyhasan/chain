import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;



public class Chain  implements KeyListener{
	//Needed variables:
		private Player player;
		private boolean gameOver = false;
		public Board boardObject;
		public static int seed = 0;
		public char[][] board;
		public Console console;
		private boolean keyPressed = false;
		private int keyCode = 0;
		SingleLinkedList chain;
		private int[][] coordinates = new int[200][2];
		boolean canBeConstructed = true;
		int score = 0;

		private MultiLinkedList TableMLL=new MultiLinkedList();
		
		public Chain() throws Exception {
			//Creating the instance of player class
		 	player = new Player();
	        // Create the enigma console
	        console = Enigma.getConsole("Chain", 90, 26, 20);
	        console.getTextWindow().addKeyListener(this);
			runGame();
	    }
	 
	   
	    public void runGame() throws Exception {
	    	// Duration of a frame based on FPS constant.
	        long frameTime = 1000 / 10;
	        //Taking valid input from the user
	    	Scanner scanner = new Scanner(System.in);
	        boolean validInput = false;	        
	        while (!validInput) {
	            try {
	                System.out.print("Enter the seed: ");
	                seed = scanner.nextInt();
	                validInput = true;
	                
	            } catch (Exception e) {
	                System.out.println("Invalid input. Please enter the seed.");
	                scanner.nextLine();
	            }
	        }

			//animation part
			Animation();

	        for (int i = 0; i < 100; i++) {
	            System.out.println();
	        }
	        
	        //Printing the board
	        board = boardObject.boardSeed(seed);
	        setCursorPos(0, 0);
	        for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					System.out.print(board[i][j]);
				}
				System.out.println();
			}
	        //Printing the Board Seed:
	        setCursorPos(38, 0);
	        System.out.println("Board Seed: " + seed);
	        
	        setCursorPos(38, 2);
	        System.out.println("Score: "+score);

			console.getTextWindow().setCursorPosition(38,4);
			console.getTextWindow().output("-----------------------------------------");
			console.getTextWindow().setCursorPosition(38,6);
			console.getTextWindow().output("Table: ");
	        
	        setCursorPos(0, 0);
	        
	        //The main loop of the game:
	        while(!gameOver) {
	        	long frameStart = System.currentTimeMillis();
	        	// Handle player inputs
	            if (keyPressed) {
	                // Handle player inputs
	                switch (keyCode) {
	                    case KeyEvent.VK_RIGHT:
							movePlayer(1, 0);
	                        break;
	                    case KeyEvent.VK_LEFT:
							movePlayer(-1, 0);
	                        break;
	                    case KeyEvent.VK_DOWN:
							movePlayer(0, 1);
	                        break;
	                    case KeyEvent.VK_UP:
							movePlayer(0, -1);
	                        break;
	                    case KeyEvent.VK_SPACE:
	                    	insertRemovePlus();
	                        break;
						case KeyEvent.VK_Q:
							getPlusCoordinates();   
	                    	chainControl();
	                    	constructChain();
	                    	if(canBeConstructed) {
	                    		gainScore(chain);
								addTable(chain);
								displayTable();
	                    	}
	                    	
							break;
						case KeyEvent.VK_E:
							break;
	                    default:
	                        break;
	                }

	                keyPressed = false;
	            }
	            
	            if(!canBeConstructed) {
	            	gameOver();
	            	break;
	            }
	            
	            nextRound();
	        	// Frame end time
	            long frameEnd = System.currentTimeMillis();

	            // Elapsed time in milliseconds
	            long elapsedTime = frameEnd - frameStart;

	            // Sleep the rest of the frameTime
	            long sleepTime = frameTime - elapsedTime;

	            if (sleepTime > 0) {
	                Thread.sleep(sleepTime);
	            }
		    	
	    	}
	     
	    }
	    
	    private void movePlayer(int moveX, int moveY) throws Exception {

	        // Calculate the new position of the player
	        int newPlayerX = player.x + moveX;
	        int newPlayerY = player.y + moveY;
	        
	        // Check if the new position is within the board boundaries
	        if (newPlayerX >= 0 && newPlayerX < 31 && newPlayerY >= 0 && newPlayerY < 19) {
	            char square = board[newPlayerY][newPlayerX];
	            
	            // Check if the square at the new position is empty (not occupied by '1', '2', '3', or '4')
	            if (square != '1' && square != '2' && square != '3' && square != '4' && square!='.') {
	                // Check if the square is not a '+'
	                if (square != '+') {
	                    // Set the cursor position and print '|'
	                    setCursorPos(newPlayerX, newPlayerY);
	                    System.out.print("|");
	                    
	                    // Check if the current player position is not a '+'
	                    if (board[player.y][player.x] != '+') {
	                        // Set the cursor position and print empty space
	                        setCursorPos(player.x, player.y);
	                        System.out.println(" ");
	                    }
	                    // Check if the current player position is a '+'
	                    else if (board[player.y][player.x] == '+') {
	                        // Set the console color to white on black and print '+'
	                        setConsoleColor(Color.WHITE, Color.BLACK);
	                        setCursorPos(player.x, player.y);
	                        System.out.print("+");
	                    }
	                    
	                    // Set the cursor position to (0, 0) and print the value at board[0][0]
	                    setCursorPos(0, 0);
	                    System.out.println(board[0][0]);
	                    
	                    // Update the player position
	                    player.x = newPlayerX;
	                    player.y = newPlayerY;
	                }
	                // If the square is a '+'
	                else if (square == '+') {
	                    // Set the console color to white on red and print '+'
	                    setConsoleColor(Color.WHITE, Color.RED);
	                    setCursorPos(newPlayerX, newPlayerY);
	                    System.out.print("+");
	                    
	                    // Reset the console color to white on black
	                    setConsoleColor(Color.WHITE, Color.BLACK);
	                    
	                    // Set the cursor position to the current player position and print empty space
	                    setCursorPos(player.x, player.y);
	                    System.out.println(" ");
	                    
	                    // Check if the current player position is a '+'
	                    if (board[player.y][player.x] == '+') {
	                        // Set the console color to white on black and print '+'
	                        setConsoleColor(Color.WHITE, Color.BLACK);
	                        setCursorPos(player.x, player.y);
	                        System.out.print("+");
	                    }
	                    
	                    // Update the player position
	                    player.x = newPlayerX;
	                    player.y = newPlayerY;
	                }
	            }
	        }
	        // If the new position is out of bounds, reset the new position to the current player position
	        else {
	            newPlayerX = player.x;
	            newPlayerY = player.y;
	        }
	    }

	    private void insertRemovePlus() {
	        // Check if the current position on the board is empty (' ')
	        if (board[player.y][player.x] == ' ') {
	            // Insert a '+' at the current position
	            board[player.y][player.x] = '+';
	            
	            // Set the console color to white on red and print '+'
	            setConsoleColor(Color.WHITE, Color.RED);
	            setCursorPos(player.x, player.y);
	            System.out.println("+");
	            
	            // Reset the console color to white on black
	            setConsoleColor(Color.WHITE, Color.BLACK);
	        }
	        // If the current position on the board is a '+'
	        else if (board[player.y][player.x] == '+') {
	            // Remove the '+' at the current position
	            board[player.y][player.x] = ' ';
	            
	            // Set the cursor position and print empty space
	            setCursorPos(player.x, player.y);
	            System.out.println(" ");
	        }
	    }
	    
	    
	    
	    
	    
	    //get all coordinates of pluses and store them in an array.
	    void getPlusCoordinates() {
	    	int plusCount = 0;
	    	for(int i = 0;i < 19;i++) {
	    		for(int j = 0;j < 31;j++) {
	    			if(board[i][j] == '+') {
	    				coordinates[plusCount][0] = j;
	    				coordinates[plusCount][1] = i;
	    				plusCount++;
	    			}
	    		}
	    	}
	    }
	    
	    
	    
	    void chainControl() {
	    	for(int i = 0;i < 20;i++) {
	    		
	    		if(coordinates[i][0] != 0 || coordinates[i][1] != 0) {
	    			if((coordinates[i][1] == 0 || board[coordinates[i][1]-1][coordinates[i][0]] == ' ') && (coordinates[i][0] == 0 || board[coordinates[i][1]][coordinates[i][0]-1] == ' ')) {	
	    				canBeConstructed = false;
	    			}
	    			else if(coordinates[i][1] == 0 || board[coordinates[i][1]-1][coordinates[i][0]] == ' ') {
	    				if(Math.abs((Character.getNumericValue(board[coordinates[i][1]][coordinates[i][0]+1]))-(Character.getNumericValue(board[coordinates[i][1]][coordinates[i][0]-1]))) != 1) {
	    					canBeConstructed = false;
	    				}
	    			}else if(coordinates[i][1] != 0 && board[coordinates[i][1]-1][coordinates[i][0]] != ' ') {
	    				if(Math.abs((Character.getNumericValue(board[coordinates[i][1]-1][coordinates[i][0]]))-(Character.getNumericValue(board[coordinates[i][1]+1][coordinates[i][0]]))) != 1) {
	    					canBeConstructed = false;
	    				}
	    			}
	    			else if((coordinates[i][1] != 30 && board[coordinates[i][1]+1][coordinates[i][0]] == '.' )|| (coordinates[i][0] !=18 && board[coordinates[i][1]][coordinates[i][0]+1] == '.' )||
	    					  (coordinates[i][1] != 0 && board[coordinates[i][1]][coordinates[i][0]] == '.') || (coordinates[i][0]!= 0&&board[coordinates[i][1]][coordinates[i][0]-1] == '.') ) {
	    					canBeConstructed = false;
	    					    			}
	    		}
	    	}
	    	int headCount = 0;
	    	boolean WPChain = false;           
	    	for(int a = 0;a < coordinates.length;a++) {
	    		if(coordinates[a][0] != 0 || coordinates[a][1] != 0) {
	    			int result = plusCounter(coordinates[a][0],coordinates[a][1]);
		    		if(coordinates[a][0] == 0 && coordinates[a][1] == 0) {break;}
		    		else if(result == 1 || (result==0)) {
	    				{
	    					headCount++;
		    			}
		    		}
		    		else if (result > 2) {
		    			WPChain = true;
		    		}
	    		}
	    		
	    		
	    	}
	    	if(headCount > 2 || WPChain) {canBeConstructed = false;}
	    	
	    }
	    
	    
	    
	    SingleLinkedList constructChain() {
	    	chain = new SingleLinkedList();
	    	int startX = 0;
	    	int startY = 0;
	    	//find the starting point of chain.
	    	for(int i = 0;i < coordinates.length;i++) {
	    		if(plusCounter(coordinates[i][0],coordinates[i][1]) == 1){
	    			startX = coordinates[i][0];
	    			startY = coordinates[i][1];
	    			break;
	    		}
	    	}
	    	
	    	getFirstElement(chain,startX,startY);
	    	for(int p = 0; p < coordinates.length;p++) {
	    		board[startY][startX] = ' ';setCursorPos(startX,startY);
	    		System.out.println(" ");
	    		
	    		boolean isFound = false;

	    		if((startY == 18) || board[startY+1][startX] == ' ') {
	    			
		    		for(int i = -1;i< 2;i++) {
		    			for(int j = -2; j<3;j++) {
		    				//boundary control
		    				if(startY+i >= 0 && startY+i <= 18 && startX+j >= 0 && startX+j <= 30 && board[startY+i][startX+j] == '+') {
		    					if((startY-1>=0&&startX+1<31 && board[startY-1][startX+1] == '+') || (startY+1<=18&&startX+1<=30&&board[startY+1][startX+1] == '+')) {
		    						chain.add(board[startY][startX+1]);
		    						board[startY][startX+1] = '.';setCursorPos(startX+1,startY);System.out.println(".");
		    						startY += i;startX += j;isFound = true;
		    						
		    						
		    					}else if((startY-1>=0&&startX-1>=0 &&board[startY-1][startX-1] == '+' )||(startY+1<=18&&startX-1>=0 &&board[startY+1][startX-1] == '+')) {
		    						chain.add(board[startY][startX-1]);
		    						board[startY][startX-1] = '.';setCursorPos(startX-1,startY);System.out.println(".");
		    						startY += i;startX += j;isFound = true;
		    						
		    						
		    					}else if(startX-2>=0 &&board[startY][startX-2] == '+') {
		    						chain.add(board[startY][startX-1]);
		    						board[startY][startX-1] = '.';setCursorPos(startX-1,startY);System.out.println(".");
		    						startY += i;startX += j;isFound = true;
		    						
		    					}else if(startX+2<31 &&board[startY][startX+2] == '+') {
		    						chain.add(board[startY][startX+1]);
		    						board[startY][startX+1] = '.';setCursorPos(startX+1,startY);System.out.println(".");
		    						startY += i;startX += j;isFound = true;
		    						
		    					}
		    					
		    						
		    				}
		    				if(isFound)break;
		    			}
		    			if(isFound)break;
		    		}
		    	}
		    	else if((startX != 0 || startY != 0) && board[startY+1][startX] != ' ') {
		    		for(int a = -2;a< 3;a++) {
		    			for(int b = -1; b<2;b++) {
		    				//boundary control.
		    				if(startY+a >= 0 && startY+a <= 18 && startX+b >= 0 && startX+b <= 30 &&board[startY+a][startX+b] == '+') {
		    					if(startY-1>=0&&startX+1<31 &&(board[startY-1][startX+1] == '+')||(startY-1>=0&&startX-1>=0 &&board[startY-1][startX-1] == '+')) {
		    						chain.add(board[startY-1][startX]);
		    						board[startY-1][startX] = '.';setCursorPos(startX,startY-1);System.out.println(".");
		    						startY += a;startX += b;isFound = true;
		    						
		    					}else if((startY+1<=18&&startX-1>=0 &&board[startY+1][startX-1] == '+')||(startY+1<=18&&startX+1<31&&board[startY+1][startX+1] == '+')) {
		    						chain.add(board[startY+1][startX]);
		    						board[startY+1][startX] = '.';setCursorPos(startX,startY+1);System.out.println(".");
		    						startY += a;startX += b;isFound = true;
		    						
		    					}else if(startY-2>=0&&board[startY-2][startX] == '+') {
		    						chain.add(board[startY-1][startX]);
		    						board[startY-1][startX] = '.';setCursorPos(startX,startY-1);System.out.println(".");
		    						startY += a;startX += b;isFound = true;
		    						
		    					}else if(startY+2<=18&&board[startY+2][startX] == '+') {
		    						chain.add(board[startY+1][startX]);
		    						board[startY+1][startX] = '.';setCursorPos(startX,startY+1);System.out.println(".");
		    						startY += a;startX += b;isFound = true;
		    					}
		    					
		    				}
		    				if(isFound)break;
		    				
		    			}
		    			if(isFound)break;
		    		}
		    	}	
	    	}
	    	if(startX-1 >=0 && startX+1<31&&board[startY][startX-1] == '.') {chain.add(board[startY][startX+1]);setCursorPos(startX+1,startY);System.out.println(".");}
	    	else if(startX-1 >= 0&&startX+1<31&&board[startY][startX+1] == '.') {chain.add(board[startY][startX-1]);
	    	setCursorPos(startX-1,startY);System.out.println(".");}
	    	else if(startY-1 >= 0&&startY+1<=18&&board[startY-1][startX] == '.') {chain.add(board[startY+1][startX]);setCursorPos(startX,startY+1);System.out.println(".");}
	    	else {if(startY-1>=0) {chain.add(board[startY-1][startX]);}setCursorPos(startX,startY-1);System.out.println(".");
	    	}
	    	
	    	
	    	if(chain.size() < 4 ) {
	    		canBeConstructed = false;
	    	}	    	
	    	return chain;
    	}	    
	    	
	     
	    void getFirstElement(SingleLinkedList chain,int x , int y) {
	    	if(y == 0 ||board[y-1][x] == ' ') {
	    		if((x+2 < 30&&board[y][x+2] == '+' )|| (y-1>=0&&x+1<31&&board[y-1][x+1] == '+') ||(y+1<19&&x+1<31 &&board[y+1][x+1] == '+')) {
	    			chain.add(board[y][x-1]);setCursorPos(x-1,y);System.out.println(".");
	    		}else {chain.add(board[y][x+1]);setCursorPos(x+1,y);System.out.println(".");}
	    	}
	    	else if(y == 0 ||board[y-1][x] != ' ') {
	    		if((y-2>=0&&board[y-2][x] == '+' )|| (y-1>=0&&x+1<31&&board[y-1][x+1] == '+') || (y-1>=0&&x-1>=0&&board[y-1][x-1] == '+')) {
	    			chain.add(board[y+1][x]);setCursorPos(x,y+1);System.out.println(".");
	    		}else {chain.add(board[y-1][x]);setCursorPos(x,y-1);System.out.println(".");}
	    	}
	    }
	    
	    
	    
	    void nextRound() {
	    	canBeConstructed = true;
	    	int a = 0;
	    	while(coordinates[a][0]!=0 || coordinates[a][1] != 0) {
	    		coordinates[a][0] = 0;
	    		coordinates[a][1] = 0;
	    		a++;
	    	}
	    }
	    
	    
	    
	    int plusCounter(int x,int y) {
	    	int plusCount = 0;
	    	if(y == 0 || board[y-1][x] == ' ') {
	    		for(int i = -1;i< 2;i++) {
	    			for(int j = -2; j<3;j++) {
	    				if(y+i >= 0 && y+i <= 18 && x+j >= 0 && x+j <= 30 && board[y+i][x+j] == '+') {
	    					plusCount++;
	    				}
	    			}
	    		}
	    		}
	    	else if(y != 0 && board[y-1][x] != ' ') {
	    		for(int a = -2;a< 3;a++) {
	    			for(int b = -1; b<2;b++) {
	    				if(y+a >= 0 && y+a <= 18 && x+b >= 0 && x+b <= 30 &&board[y+a][x+b] == '+') {
	    					plusCount++;
	    				}
	    			}
	    		}
	    	}	
	    	return plusCount-1;}
	    
	    
	    
	    
	    void gameOver() {
	    	setCursorPos(50,20);
        	System.out.println("ERROR in Chain!");
        	setCursorPos(52,21);
        	System.out.println("-GAME OVER-");
	    }
	    
	    void gainScore(SingleLinkedList chain) {
	    	score += chain.size() * chain.size();
	    	setCursorPos(38,2);
	    	System.out.println("Score: "+score);
	    }
	    
	    

	    
	    private void setCursorPos(int x, int y) {
	        // Set the cursor position in the console's text window
	        this.console.getTextWindow().setCursorPosition(x, y);
	    }

	    private void setConsoleColor(Color foreground, Color background) {
	        // Set the console text attributes with the specified foreground and background colors
	        this.console.setTextAttributes(new TextAttributes(foreground, background));
	    }

      

	    // Key event callbacks
	    public void keyTyped(KeyEvent event) {

	    }

	    public void keyPressed(KeyEvent event) {

	        this.keyPressed = true;
	        this.keyCode = event.getKeyCode();
	    }

	    public void keyReleased(KeyEvent event) {

	    }

		///////Table Part/////////
		public void addTable(SingleLinkedList chain){
			TableMLL.addChain(chain);
		}

		public void displayTable(){
			String output = "";
			MLLNode head=TableMLL.getHead();
			if (head != null){
				MLLNode temp = head;
				int tempWhileCounter=0;
				while (temp != null){
					console.getTextWindow().setCursorPosition(38,7+tempWhileCounter);
					MLLNode temp2 = temp;
					int temp2WhileCounter=1;
					while(temp2.getRight() != null){
						console.getTextWindow().output( temp2.getChainElement()+" + ");
						console.getTextWindow().setCursorPosition(38+temp2WhileCounter+3,7+tempWhileCounter);
						temp2 = temp2.getRight();
						temp2WhileCounter+=4;
					}
					console.getTextWindow().setCursorPosition(38+temp2WhileCounter-1,7+tempWhileCounter);
					console.getTextWindow().output(Integer.toString(temp2.getChainElement()));

					if(temp.getDown() != null) {

						console.getTextWindow().setCursorPosition(38,7 + tempWhileCounter + 1);
						console.getTextWindow().output('+');
					}
					temp = temp.getDown();
					tempWhileCounter+=2;
				}
			}

		}

		public void Animation() throws InterruptedException {
			/////Animation Starts////////

			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");
			System.out.println("  ");



			System.out.println(
					" __|  \\                         \n" +
							"/  \\. |                         \n" +
							"    \\.|                         \n" +
							"\\    \\|                         \n" );

			Thread.sleep(100);

			System.out.println("\\\\    \\                         \n" +
					"  \\    \\                        \n" +
					"   \\    \\                       \n" +
					"    \\    \\-   \\                 \n" +
					"     \\    \\.   \\                \n" );

			Thread.sleep(100);
			System.out.println(
					"      \\    \\.   \\               \n" +
							"      |\\    \\\\   \\              \n" +
							"      | \\   | \\   \\             \n" +
							"      |  \\ /   \\   \\            \n" );

			Thread.sleep(100);
			System.out.println(
					"      |  \\      \\   \\           \n" +
							"      \\   \\      \\   \\          \n" +
							"       \\   \\    __|  |          \n" +
							"        \\   \\  /  \\. |          \n" );

			Thread.sleep(100);
			System.out.println(
					"         \\   \\ |   \\.|          \n" +
							"          \\   \\\\    \\|          \n" +
							"           \\   \\\\    \\          \n" +
							"            \\    \\    \\         \n" );

			Thread.sleep(100);
			System.out.println(
					" __|  \\                         \n" +
							"/  \\. |                         \n" +
							"    \\.|                         \n" +
							"\\    \\|                         \n" );

			Thread.sleep(100);

			System.out.println("\\\\    \\                         \n" +
					"  \\    \\                        \n" +
					"   \\    \\                       \n" +
					"    \\    \\-   \\                 \n" +
					"     \\    \\.   \\                \n" );

			Thread.sleep(100);
			System.out.println(
					"      \\    \\.   \\               \n" +
							"      |\\    \\\\   \\              \n" +
							"      | \\   | \\   \\             \n" +
							"      |  \\ /   \\   \\         BOARD IS BEING CREATED !!   \n" );

			Thread.sleep(100);


			System.out.println(
					"      |  \\      \\   \\           \n" +
							"      \\   \\      \\   \\          \n" +
							"       \\   \\    __|  |          \n" +
							"        \\   \\  /  \\. |          \n" );

			Thread.sleep(100);
			System.out.println(
					"         \\   \\ |   \\.|          \n" +
							"          \\   \\\\    \\|          \n" +
							"           \\   \\\\    \\          \n" +
							"            \\    \\    \\         \n" );

			Thread.sleep(100);

			System.out.println(
					" __|  \\                         \n" +
							"/  \\. |                         \n" +
							"    \\.|                         \n" +
							"\\    \\|                         \n" );

			Thread.sleep(100);

			System.out.println("\\\\    \\                         \n" +
					"  \\    \\                        \n" +
					"   \\    \\                       \n" +
					"    \\    \\-   \\                 \n" +
					"     \\    \\.   \\                \n" );

			Thread.sleep(100);
			System.out.println(
					"      \\    \\.   \\               \n" +
							"      |\\    \\\\   \\              \n" +
							"      | \\   | \\   \\             \n" +
							"      |  \\ /   \\   \\            \n" );

			Thread.sleep(100);
			System.out.println(
					"      |  \\      \\   \\           \n" +
							"      \\   \\      \\   \\          \n" +
							"       \\   \\    __|  |          \n" +
							"        \\   \\  /  \\. |          \n" );

			Thread.sleep(100);
			System.out.println(
					"         \\   \\ |   \\.|          \n" +
							"          \\   \\\\    \\|          \n" +
							"           \\   \\\\    \\          \n" +
							"            \\    \\    \\        IT IS ALMOST FINISHED !!! \n" );

			Thread.sleep(100);

			System.out.println(
					" __|  \\                         \n" +
							"/  \\. |                         \n" +
							"    \\.|                         \n" +
							"\\    \\|                         \n" );

			System.out.println("\\\\    \\                         \n" +
					"  \\    \\                        \n" +
					"   \\    \\                       \n" +
					"    \\    \\-   \\                 \n" +
					"     \\    \\.   \\                \n" );

			Thread.sleep(100);
			System.out.println(
					"      \\    \\.   \\               \n" +
							"      |\\    \\\\   \\              \n" +
							"      | \\   | \\   \\             \n" +
							"      |  \\ /   \\   \\            \n" );

			Thread.sleep(100);
			System.out.println(
					"      |  \\      \\   \\           \n" +
							"      \\   \\      \\   \\          \n" +
							"       \\   \\    __|  |          \n" +
							"        \\   \\  /  \\. |          \n" );

			Thread.sleep(100);
			System.out.println(
					"         \\   \\ |   \\.|          \n" +
							"          \\   \\\\    \\|          \n" +
							"           \\   \\\\    \\          \n" +
							"            \\    \\    \\         \n" );

			Thread.sleep(280);



			System.out.println("                                     GAME IS STARTING !!");
			System.out.println("                                         GOOD LUCK !!");
			Thread.sleep(1250);
			/////Animation Finishes////////
		}

}


