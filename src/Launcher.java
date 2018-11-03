import java.io.IOException;
import java.net.*;

public class Launcher {
	
	ServerSocket jSConnection;
	PlayerConnection readerOne;
	PlayerConnection readerTwo;
	
	public static void main(String[] args) {
		System.out.println("Hello, World!");
		Launcher me = new Launcher();
		me.go();
		//board.print();
	}
	
	public void go() {
		Board board = new Board();
		board.init();
		try {
			jSConnection = new ServerSocket(8080);
			Socket pOneSocket = jSConnection.accept();
			Socket pTwoSocket = jSConnection.accept();
			readerOne = new PlayerConnection(pOneSocket, this, Tile.BLACK);
			readerOne = new PlayerConnection(pOneSocket, this, Tile.WHITE);
		} catch (IOException e) {
			System.out.println("Error opening socket + setup");
			e.printStackTrace();
			return;
		}
	}
	
	public void updateData() {
		
	}
}
