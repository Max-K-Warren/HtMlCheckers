import java.awt.Point;
import java.util.Scanner;

public class Board {
	private Tile[][] board;
	private final int SIZE = 8;
	private enum Turn {BLACK, WHITE}
	private Turn turn;
	private int black_pieces;
	private int white_pieces;
	
	public Board() {
		init();
	}
	
	public void init() {
		// Initialise board
		board = new Tile[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if ((i + j) % 2 == 1) {
					board[i][j] = Tile.VOID;
				} else if (i < 3) {
					board[i][j] = Tile.WHITE;	
				} else if (i > 4) {
					board[i][j] = Tile.BLACK;
				} else {
					board[i][j] = Tile.EMPTY;
				}
			}
		}
		//set number of pieces
		black_pieces = 12;
		white_pieces = 12;
		
		//set player turn
		turn = Turn.BLACK;
	}
	
	// checks for winner based on whose turn it is. Black can't win on white's turn
	private boolean check_loss(Turn turn) {
		if (turn == Turn.BLACK) {
			return (white_pieces == 0);
		} else {
			return (black_pieces == 0);
		}
	}
	
	// convert board into 2d int array
	private int[][] int_board() {
		int[][] temp_board = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				switch (board[i][j]) {
					case VOID: temp_board[i][j] = 0; break;
					case EMPTY: temp_board[i][j] = 1; break;
					case BLACK: temp_board[i][j] = 2; break;
					case BLACKCROWN: temp_board[i][j] = 3; break;
					case WHITE: temp_board[i][j] = 4; break;
					case WHITECROWN: temp_board[i][j] = 5; break;
				}
			}
		}
		
		// prints int board. not needed
		return temp_board;
		
	}
	
	
	
	private boolean checkForwardLeft(int i, int j) {
		boolean playable = false;
		if (i-1 >= 0 && j-1 >= 0) {
			if (board[i-1][j-1] == Tile.EMPTY) playable = true;
		}
		return playable;
	}
	
	private boolean checkForwardRight(int i, int j) {
		boolean playable = false;
		if (i-1 >= 0 && j+1 < SIZE) {
			if (board[i-1][j+1] == Tile.EMPTY) playable = true;
		}
		return playable;
	}
	private boolean checkBackwardLeft(int i, int j) {
		boolean playable = false;
		if (i+1 < SIZE && j-1 >= 0) {
			if (board[i+1][j-1] == Tile.EMPTY) playable = true;
		}
		return playable;
	}
	private boolean checkBackwardRight(int i, int j) {
		boolean playable = false;
		if (i+1 < SIZE && j+1 < SIZE) {
			if (board[i+1][j+1] == Tile.EMPTY) playable = true;
		}
		return playable;
	}
	private boolean checkJumpForwardLeft(int i, int j) {
		boolean playable = false;
		if (i-2 >= 0 && j-2 >= 0) {
			if (turn == Turn.BLACK) {
				if ((board[i-1][j-1] == Tile.WHITE || board[i-1][j-1] == Tile.WHITECROWN) && board[i-2][j-2] == Tile.EMPTY) playable = true;
			}
			else if ((board[i-1][j-1] == Tile.BLACK || board[i-1][j-1] == Tile.BLACKCROWN) && board[i-2][j-2] == Tile.EMPTY) playable = true;
		}
		return playable;
	}
	
	private boolean checkJumpForwardRight(int i, int j) {
		boolean playable = false;
		if (i-2 >= 0 && j+2 < SIZE) {
			if (turn == Turn.BLACK) {
				if ((board[i-1][j+1] == Tile.WHITE || board[i-1][j-1] == Tile.WHITECROWN) && board[i-2][j+2] == Tile.EMPTY) playable = true;
			}
			else if ((board[i-1][j+1] == Tile.BLACK || board[i-1][j-1] == Tile.BLACKCROWN) && board[i-2][j+2] == Tile.EMPTY) playable = true;
		}
		return playable;
	}
	private boolean checkJumpBackwardLeft(int i, int j) {
		boolean playable = false;
		if (i+2 < SIZE && j-2 >= 0) {
			if (turn == Turn.BLACK) {
				if ((board[i+1][j-1] == Tile.WHITE || board[i+1][j-1] == Tile.WHITECROWN) && board[i+2][j-2] == Tile.EMPTY) playable = true;
			}
			else if ((board[i+1][j-1] == Tile.BLACK || board[i+1][j-1] == Tile.BLACKCROWN) && board[i+2][j-2] == Tile.EMPTY) playable = true;
		}
		return playable;
	}
	private boolean checkJumpBackwardRight(int i, int j) {
		boolean playable = false;
		if (i+2 < SIZE && j+2 < SIZE) {
			if (turn == Turn.BLACK) {
				if ((board[i+1][j+1] == Tile.WHITE || board[i+1][j+1] == Tile.WHITECROWN) && board[i+2][j+2] == Tile.EMPTY) playable = true;
			}
			else if ((board[i+1][j+1] == Tile.BLACK || board[i+1][j+1] == Tile.BLACKCROWN) && board[i+2][j+2] == Tile.EMPTY) playable = true;
		}
		return playable;
	}
	
	
	// selects playable pieces. add 10 to all playable pieces
	private int[][] genPlayablePieces(int[][] matrix) {
		if (turn == Turn.BLACK) {
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					boolean playable = false;
					if (
							checkForwardLeft(i, j) || 
							checkForwardRight(i, j) ||
							checkJumpForwardLeft(i, j) ||
							checkJumpForwardRight(i, j)
							) 
						playable = true;
					if (board[i][j] == Tile.BLACKCROWN) {
						if (
								checkBackwardLeft(i, j) || 
								checkBackwardRight(i, j) ||
								checkJumpBackwardLeft(i, j) ||
								checkJumpBackwardRight(i, j)
								) 
							playable = true;
					}
					if (playable) matrix[i][j] = matrix[i][j] + 10;
				}
			}
		}
		
		else { //is white
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					boolean playable = false;
					if (
							checkBackwardLeft(i, j) || 
							checkBackwardRight(i, j) ||
							checkJumpBackwardLeft(i, j) ||
							checkJumpBackwardRight(i, j)
							) 
						playable = true;
					if (board[i][j] == Tile.WHITECROWN) {
						if (
								checkForwardLeft(i, j) || 
								checkForwardRight(i, j) ||
								checkJumpForwardLeft(i, j) ||
								checkJumpForwardRight(i, j)
								) 
							playable = true;
					}
					if (playable) matrix[i][j] = matrix[i][j] + 10;
				}
			}
		}
		return matrix;
	}
	
	
	
	
	// selects playable pieces. add 10 to all playable pieces --> long version.
	private int[][] genPlayablePiecesLong(int[][] matrix) {
		if (turn == Turn.BLACK) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					boolean playable = false;
					if (board[i][j] == Tile.BLACK || board[i][j] == Tile.BLACKCROWN) {
						//check in front
						if (i-1 >= 0 && j-1 >= 0) {
							if (board[i-1][j-1] == Tile.EMPTY) playable = true;
							if (board[i-1][j-1] == Tile.WHITE || board[i-1][j-1] == Tile.WHITECROWN) {
								if (i-2>=0 && j-2>=0 && board[i-2][j-2]==Tile.EMPTY) playable = true;
							}
						}
						
						if (i-1 >= 0 && j+1 < SIZE) {
							if (board[i-1][j+1] == Tile.EMPTY) playable = true;
							if (board[i-1][j+1] == Tile.WHITE || board[i-1][j+1] == Tile.WHITECROWN) {
								if (i-2>=0 && j+2<SIZE && board[i-2][j+2]==Tile.EMPTY) playable = true;
							}
						}
						
					}
					if (board[i][j] == Tile.BLACKCROWN) {
						//check behind
						if (i+1 < SIZE && j-1 >= 0) {
							if (board[i+1][j-1] == Tile.EMPTY) playable = true;
							if (board[i+1][j-1] == Tile.WHITE || board[i+1][j-1] == Tile.WHITECROWN) {
								if (i+2<SIZE && j-2>=0 && board[i+2][j-2]==Tile.EMPTY) playable = true;
							}
						}
						
						if (i+1 < SIZE && j+1 < SIZE) {
							if (board[i+1][j+1] == Tile.EMPTY) playable = true;
							if (board[i+1][j+1] == Tile.WHITE || board[i+1][j+1] == Tile.WHITECROWN) {
								if (i+2<SIZE && j+2<SIZE && board[i+2][j+2]==Tile.EMPTY) playable = true;
							}
						}
					}
					if (playable) matrix[i][j] = matrix[i][j]+10;
				}
			}
		}
		
		if (turn == Turn.WHITE) {
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < 8; j++) {
					boolean playable = false;
					if (board[i][j] == Tile.WHITE || board[i][j] == Tile.WHITECROWN) {
						//check in front
						if (i+1 < SIZE && j+1 < SIZE) {
							if (board[i+1][j+1] == Tile.EMPTY) playable = true;
							if (board[i+1][j+1] == Tile.BLACK || board[i+1][j+1] == Tile.BLACKCROWN) {
								if (i+2<SIZE && j+2<SIZE && board[i+2][j+2]==Tile.EMPTY) playable = true;
							}
						}
						
						if (i+1 <SIZE && j-1 < 0) {
							if (board[i+1][j-1] == Tile.EMPTY) playable = true;
							if (board[i+1][j-1] == Tile.BLACK || board[i+1][j-1] == Tile.BLACKCROWN) {
								if (i+2<SIZE && j-2<0 && board[i+2][j-2]==Tile.EMPTY) playable = true;
							}
						}
					}
					if (board[i][j] == Tile.WHITECROWN) {
						//check behind
						if (i-1 < 0 && j-1 >= 0) {
							if (board[i-1][j-1] == Tile.EMPTY) playable = true;
							if (board[i-1][j-1] == Tile.BLACK || board[i-1][j-1] == Tile.BLACKCROWN) {
								if (i-2<0 && j-2>=0 && board[i-2][j-2]==Tile.EMPTY) playable = true;
							}
						}
						
						if (i-1 < 0 && j+1 < SIZE) {
							if (board[i-1][j+1] == Tile.EMPTY) playable = true;
							if (board[i-1][j+1] == Tile.BLACK || board[i-1][j+1] == Tile.BLACKCROWN) {
								if (i-2<0 && j+2<SIZE && board[i-2][j+2]==Tile.EMPTY) playable = true;
							}
						}
					}
					if (playable) matrix[i][j] = matrix[i][j]+10;
				}
			}
		}
		return matrix;
	}
	
	//given a piece. show how it can move. It can make at least 1 move.
	
	// print int board given to it
	private void print (int[][] temp_board) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(temp_board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	// print board
	private void print() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	// takes point being clicked.
	public void clicked(Point p) {
		
	}

	// returns integer grid
	public int[][] get_int_grid() {
		return int_board();
	}
	
	public int[][] unhilight(int[][]matrix) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				matrix[i][j] = matrix[i][j]%10;
			}
		}
		return matrix;
	}
	
	private int[][] generate_possible_moves(int[][] matrix, Point p) {
		boolean jump = false;
		matrix[p.x][p.y] += 10; //highlights the black piece selected
		if (turn == Turn.BLACK) {
			if (p.x > 1) {
				if (p.y > 1) {
					if (board[p.x-1][p.y-1] == Tile.WHITE || board[p.x-1][p.y-1] == Tile.WHITECROWN) {
						if (board[p.x-2][p.y-2] == Tile.EMPTY) {
							matrix[p.x-2][p.y-2] += 10;
							jump = true;
						}	
					}
				}
				if (p.y < 6) {
					if (board[p.x-1][p.y+1] == Tile.WHITE || board[p.x-1][p.y+1] == Tile.WHITECROWN) {
						if (board[p.x-2][p.y+2] == Tile.EMPTY) {
							matrix[p.x-2][p.y+2] += 10;
							jump = true;
						}
					}	
				}
			}
			if (board[p.x][p.y] == Tile.BLACKCROWN) {
				if (p.x < 6) {
					if (p.y > 1) {
						if (board[p.x+1][p.y+1] == Tile.WHITE || board[p.x+1][p.y-1] == Tile.WHITECROWN) {
							if (board[p.x+2][p.y-2] == Tile.EMPTY) {
								matrix[p.x+2][p.y-2] += 10;
								jump = true;
							}
						}
					}
					
					if (p.y < 6) {
						if (board[p.x+1][p.y+1] == Tile.WHITE || board[p.x+1][p.y+1] == Tile.WHITECROWN) {
							if (board[p.x+2][p.y+2] == Tile.EMPTY) {
								matrix[p.x+2][p.y+2] += 10;
								jump = true;
							}
						}	
					}
					
				}
			}
			
			if (!jump) {
				if (p.x > 0) {
					if (p.y > 0) {
						if (board[p.x-1][p.y-1] == Tile.EMPTY) {
							matrix[p.x-1][p.y-1] += 10;
						}
					}
					if (p.y < 6) {
						if (board[p.x-1][p.y+1] == Tile.EMPTY) {
							matrix[p.x-1][p.y+1] += 10;
						}
					}
				}
				if (board[p.x][p.y] == Tile.BLACKCROWN) {
					if (p.x < 7) {
						if (p.y > 0) {
							if (board[p.x+1][p.y-1] == Tile.EMPTY) {
								matrix[p.x+1][p.y-1] += 10;
							}
						}
						if (p.y < 6) {
							if (board[p.x+1][p.y+1] == Tile.EMPTY) {
								matrix[p.x+1][p.y+1] += 10;
							}
						}
					}
				}
			}
			
		} else {
			if (p.x < 6) {
				if (p.y > 1) {
					if (board[p.x+1][p.y-1] == Tile.BLACK || board[p.x+1][p.y-1] == Tile.BLACKCROWN) {
						if (board[p.x+2][p.y-2] == Tile.EMPTY) {
							matrix[p.x+2][p.y-2] += 10;
							jump = true;
						}	
					}
				}
				if (p.y < 6) {
					if (board[p.x+1][p.y+1] == Tile.BLACK || board[p.x+1][p.y+1] == Tile.BLACKCROWN) {
						if (board[p.x+2][p.y+2] == Tile.EMPTY) {
							matrix[p.x+2][p.y+2] += 10;
							jump = true;
						}
					}	
				}
			}
			if (board[p.x][p.y] == Tile.WHITECROWN) {
				if (p.x > 1) {
					if (p.y > 1) {
						if (board[p.x-1][p.y+1] == Tile.BLACK || board[p.x-1][p.y-1] == Tile.BLACKCROWN) {
							if (board[p.x-2][p.y-2] == Tile.EMPTY) {
								matrix[p.x-2][p.y-2] += 10;
								jump = true;
							}
						}
					}
					
					if (p.y < 6) {
						if (board[p.x-1][p.y+1] == Tile.BLACK || board[p.x-1][p.y+1] == Tile.BLACKCROWN) {
							if (board[p.x-2][p.y+2] == Tile.EMPTY) {
								matrix[p.x-2][p.y+2] += 10;
								jump = true;
							}
						}	
					}
					
				}
			}
			
			if (!jump) {
				if (p.x < 7) {
					if (p.y > 0) {
						if (board[p.x+1][p.y-1] == Tile.EMPTY) {
							matrix[p.x+1][p.y-1] += 10;
						}
					}
					if (p.y < 6) {
						if (board[p.x+1][p.y+1] == Tile.EMPTY) {
							matrix[p.x+1][p.y+1] += 10;
						}
					}
				}
				if (board[p.x][p.y] == Tile.WHITECROWN) {
					if (p.x > 0) {
						if (p.y > 0) {
							if (board[p.x-1][p.y-1] == Tile.EMPTY) {
								matrix[p.x-1][p.y-1] += 10;
							}
						}
						if (p.y < 6) {
							if (board[p.x-1][p.y+1] == Tile.EMPTY) {
								matrix[p.x-1][p.y+1] += 10;
							}
						}
					}
				}
			}
		}
		
		return matrix;
	}
	
	private boolean isJump(Point souce, Point goal) {
		//returns true if move 'jumps' over an opposing piece to take it
		if (Math.abs(souce.x - goal.x) == 2) return true;
		else return false;
	}
	
	private boolean canJumpAgain(Point source, Point goal) {
		boolean jumpAgain = false;
		if (turn == Turn.BLACK) {
			if (board[source.x][source.y] == Tile.BLACKCROWN || board[source.x][source.y] == Tile.WHITECROWN) {
				jumpAgain = (
						checkJumpForwardLeft(goal.x, goal.y) ||
						checkJumpForwardRight(goal.x, goal.y) ||
						checkJumpBackwardLeft(goal.x, goal.y) ||
						checkJumpBackwardRight(goal.x, goal.y));
			}
			if (board[source.x][source.y] == Tile.BLACK) {
				jumpAgain = (
						checkJumpForwardLeft(goal.x, goal.y) ||
						checkJumpForwardRight(goal.x, goal.y)
				);
			}
			if (board[source.x][source.y] == Tile.WHITE) {
				jumpAgain = (
						checkJumpBackwardLeft(goal.x, goal.y) ||
						checkJumpBackwardRight(goal.x, goal.y)
				);
			}
		}
		return jumpAgain;
	}
	
	private void switchPlayers() {
		if (turn == Turn.BLACK) turn = Turn.WHITE;
		else turn = Turn.BLACK;
		
	}
	
	private void deductNonPlayerTally() {
		if (turn == Turn.WHITE) black_pieces--;
		else white_pieces--;
	}
	
	private Point getSkipped(Point goal, Point source) {
		int i = source.x + ((goal.x - source.x)/2);
		int j = source.y + ((goal.y - source.y)/2);
		return new Point(i, j);
	}
	
	private int[][] make_move(int[][] matrix, Point source, Point goal) {
		board[goal.x][goal.y] = board[source.x][source.y];
		matrix[goal.x][goal.y] = matrix[source.x][source.y];
		board[source.x][source.y] = Tile.EMPTY;
		matrix[source.x][source.y] = 1;
		if (isJump(source, goal)) {
			Point skipped = getSkipped(goal, source);
			board[skipped.x][skipped.y] = Tile.EMPTY;
			matrix[skipped.x][skipped.y] = 1;
			deductNonPlayerTally();
			if (canJumpAgain(source, goal)) {
				return matrix;
			}
		}
		switchPlayers();
		return matrix;
	}
	
	// make swap and remove taken pieces
	private boolean make_move_old(int[][] matrix, Point p, Point q) {
		matrix[q.x][q.y] = matrix[p.x][p.y];
		board[q.x][q.y] = board[p.x][p.y];
		matrix[p.x][p.y] = 1;
		board[p.x][p.y] = Tile.EMPTY;
		
		if (Math.abs(p.x - q.x) == 2) {
			if (board[(p.x+q.x)/2][(p.y+q.y)/2] == Tile.BLACK || board[(p.x+q.x)/2][(p.y+q.y)/2] == Tile.BLACKCROWN) {
				black_pieces--;
			} else {
				white_pieces--;
			}
			board[(p.x+q.x)/2][(p.y+q.y)/2] = Tile.EMPTY;
			matrix[(p.x+q.x)/2][(p.y+q.y)/2] = 1;
		}
		if (Math.abs(p.x - q.x) == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkWinCondition() {
		if (black_pieces == 0 || white_pieces == 0) return true;
		else return false;
	}
	
	public Point inputPoint() {
		Scanner s = new Scanner(System.in);
		int x = s.nextInt();
		int y = s.nextInt();
		return new Point(x,y);
	}
	
	public void play() {
		boolean gameWon = false;
		while (!gameWon) {
			int[][] matrix = genPlayablePieces(int_board());
			//send matrix
			Point sourcePoint = inputPoint();
			//Point sourcePoint = new Point(5, 3); //change this when possible to recieve point from client
			matrix = unhilight(matrix);
			matrix = generate_possible_moves(matrix, sourcePoint);
			print(matrix);
			System.out.println();
			Point goalPoint = inputPoint();
			//Point goalPoint = new Point(4,2); //change this when poss to receive point from client
			matrix = make_move(matrix, sourcePoint, goalPoint);
			gameWon = checkWinCondition();
			//panic
		} 
	}
	
	public void playOld() {
		boolean another_move = true;
		int[][] matrix = int_board();
		matrix = genPlayablePieces(matrix);
		//send to Max
		//Get point back
		while (another_move) {
			Point p = new Point(5,3); // 5 down then 3 along
			matrix = unhilight(matrix);
			matrix = generate_possible_moves(matrix, p);
			print (matrix);
			System.out.println();
			Point q = new Point(4,2);
			//another_move = make_move(matrix, p, q);
			matrix = unhilight(matrix);
			matrix = genPlayablePieces(matrix);
			if (matrix[q.x][q.y] > 9) { // allows a single move
				another_move = false;
		}
		print();
		}
		
		
	}
}
