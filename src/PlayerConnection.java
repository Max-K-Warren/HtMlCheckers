
public class PlayerConnection extends Thread {

	Launcher master;
	Tile team;
	
	public PlayerConnection(Launcher master, Tile player) {
		this.master = master;
	}
}
