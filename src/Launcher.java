import java.io.IOException;
import java.net.ServerSocket;

public class Launcher {
	public static void main(String[] args) {
		System.out.println("Hello, World!");
		try {
			ServerSocket jSConnection = new ServerSocket(8080);
		} catch (IOException e) {
			System.out.println("Error opening socket");
			e.printStackTrace();
		}
		
		Board board = new Board();
		board.init();
		board.print();
	}
}
