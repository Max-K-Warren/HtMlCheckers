import java.io.IOException;
import java.net.*;

public class Launcher {
	
	public static void main(String[] args) {
		System.out.println("Hello, World!");
		ServerSocket jSConnection;
		Board board = new Board();
		board.init();
		try {
			jSConnection = new ServerSocket(8080);
			Socket pOneSocket = jSConnection.accept();
			Socket pTwoSocket = jSConnection.accept();
			
		} catch (IOException e) {
			System.out.println("Error opening socket");
			e.printStackTrace();
			return;
		}
		//board.print();
	}
}
