import java.io.IOException;
import java.net.*;

import com.sun.net.httpserver.HttpServer;

public class Launcher {
	
	Board board;
	HttpServer server;
	ServerSocket jSConnection;
	PlayerConnection readerOne;
	PlayerConnection readerTwo;
	int nextPlayer;
	
	public static void main(String[] args) {
		//System.out.println("Hello, World!");
		Launcher me = new Launcher();
		me.go();
	}
	
	public void go() {
		board = new Board();
		board.init();
		nextPlayer = 1;
		try {
			server = HttpServer.create(new InetSocketAddress(8080), 50);
			server.createContext("/getName/", new NameGiver(this));
			server.createContext("/getData/", new DataSender(this));
			server.createContext("/sendData/", new MoveReceiver(this));
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			System.out.println("Error opening socket + setup");
			e.printStackTrace();
			return;
		}
	}


	public int nextPlayer() {
		if(nextPlayer==1 || nextPlayer==2) {
			System.out.println("Player " + nextPlayer + " Connecting");
			int temp = nextPlayer;
			nextPlayer += 1;
			return temp;
		}else{
			System.out.println("Failed Connection");
			return 0;
		}
	}

	public String getGrid(String playerName) {
		//return JSONWriter(board.getplayershow());
		//Show the current state of the board
		//return "{ board:[[\"0\",\"5\",\"2\",\"5\",\"4\",\"5\",\"2\",\"5\"],[\"5\",\"2\",\"5\",\"4\",\"5\",\"1\",\"5\",\"3\"],[\"3\",\"5\",\"1\",\"5\",\"2\",\"5\",\"2\",\"5\"],[\"5\",\"4\",\"5\",\"3\",\"5\",\"2\",\"5\",\"3\"],[\"0\",\"5\",\"1\",\"5\",\"3\",\"5\",\"1\",\"5\"],[\"5\",\"2\",\"5\",\"2\",\"5\",\"1\",\"5\",\"3\"],[\"0\",\"5\",\"0\",\"5\",\"4\",\"5\",\"4\",\"5\"],[\"5\",\"2\",\"5\",\"4\",\"5\",\"3\",\"5\",\"3\"]]}";
		return "{\n" + 
				"\"board\":[\n" + 
				"		[\"0\",\"5\",\"12\",\"5\",\"14\",\"5\",\"2\",\"5\"],\n" + 
				"		[\"5\",\"2\",\"5\",\"4\",\"5\",\"1\",\"5\",\"3\"],\n" + 
				"		[\"3\",\"5\",\"1\",\"5\",\"2\",\"5\",\"2\",\"5\"],\n" + 
				"		[\"5\",\"4\",\"5\",\"3\",\"5\",\"2\",\"5\",\"3\"],\n" + 
				"		[\"0\",\"5\",\"1\",\"5\",\"3\",\"5\",\"1\",\"5\"],\n" + 
				"		[\"5\",\"2\",\"5\",\"2\",\"5\",\"1\",\"5\",\"3\"],\n" + 
				"		[\"0\",\"5\",\"0\",\"5\",\"4\",\"5\",\"4\",\"5\"],\n" + 
				"		[\"5\",\"2\",\"5\",\"4\",\"5\",\"3\",\"5\",\"3\"]\n" + 
				"	]\n" + 
				"}";
	}
	
	public void clicked(String x,String y, String move) {
		//Board.input: put a board input, return when that input is done
	}
	
	/*
	public void updateData() {
		readerOne.send(board.getplayershow());
		//readerTwo.send(board.getplayershow());
		if(board.isOver()){
			this.kill();
		}

	}

	
	private void kill() {
		readerOne.interrupt();
		readerTwo.interrupt();
	}*/
}
