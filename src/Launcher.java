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
		return "hi";
	}
	
	public void clicked(String player, String move) {
		//Board.input
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
