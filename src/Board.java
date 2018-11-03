


public class Board {
	private Tile[][] board;
	private final int SIZE = 8;
	private enum Turn {BLACK, WHITE}
	private Turn turn;
	private int black_pieces;
	private int white_pieces;
	
	public Board() {
		
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
	public boolean check_loss(Turn turn) {
		if (turn == Turn.BLACK) {
			return (white_pieces == 0);
		} else {
			return (black_pieces == 0);
		}
	}
	
	// convert board into 2d int array
	public int[][] int_board() {
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
		print (temp_board);
		return temp_board;
		
	}
	
	// selects playable pieces. add 10 to all playable pieces.
	public void genPlayablePieces() {
		int[][] matrix = int_board();
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
		print (matrix);
	}
	
	//given a piece. show how it can move. It can make at least 1 move.
	
	// print int board given to it
	public void print (int[][] temp_board) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(temp_board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	// print board
	public void print() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
}
