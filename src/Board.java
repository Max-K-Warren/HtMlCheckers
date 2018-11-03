import java.awt.Point;

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
	
	// selects playable pieces. add 10 to all playable pieces.
	private int[][] genPlayablePieces(int[][] matrix) {
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
	
	// make swap and remove taken pieces
	private boolean make_move(int[][] matrix, Point p, Point q) {
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
	
	public void play() {
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
			another_move = make_move(matrix, p, q);
			matrix = unhilight(matrix);
			matrix = genPlayablePieces(matrix);
			if (matrix[q.x][q.y] > 9) { // allows a single move
				another_move = false;
		}
		print();
		}
		
		
	}
}
