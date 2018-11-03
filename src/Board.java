


public class Board {
	private Tile[][] board;
	private final int SIZE = 8;
	
	public void init() {
		board = new Tile[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if ((i + j) % 2 == 1) {
					board[j][i] = Tile.VOID;
				} else if (i < 3) {
					board[j][i] = Tile.WHITE;	
				} else if (i > 4) {
					board[j][i] = Tile.BLACK;
				} else {
					board[j][i] = Tile.EMPTY;
				}
			}
		}
	}
	
	public void print() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(board[j][i] + " ");
			}
			System.out.println();
		}
	}
}
