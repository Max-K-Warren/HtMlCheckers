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
			/*
			jSConnection = new ServerSocket(8080);
			Socket pOneSocket = jSConnection.accept();
			Socket pTwoSocket = jSConnection.accept();
			readerOne = new PlayerConnection(pOneSocket, this, Tile.BLACK);
			readerTwo = new PlayerConnection(pTwoSocket, this, Tile.WHITE);
			this.updateData();
			readerOne.start();
			readerTwo.start();
			*/
		} catch (IOException e) {
			System.out.println("Error opening socket + setup");
			e.printStackTrace();
			return;
		}
	}


	public int nextPlayer() {
		if(nextPlayer==1 || nextPlayer==2) {
			return nextPlayer;
		}else{
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
