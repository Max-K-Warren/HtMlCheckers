import java.io.IOException;
import java.io.*;
import java.net.Socket;

public class PlayerConnection extends Thread {

	Launcher master;
	Tile team;
	Socket client;
	PrintStream out;
	BufferedReader in;
	
	public PlayerConnection(Socket player, Launcher master, Tile team) {
		this.master = master;
		this.team = team;
		this.client = player;
	}
	
	@Override
	public void run() {
		 try {
			out = new PrintStream(client.getOutputStream(), true);
			in = new BufferedReader( new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			System.out.println("IO init fail");
			e.printStackTrace();
			return;
		}
		while (true) {
			try {
				String maybe = in.readLine();
				master.clicked(maybe, this.team);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void send(int[][] gridState) {
		String jSONFile = JSONWriter.makeJSONString(gridState);
		out.println(jSONFile);
	}
}
