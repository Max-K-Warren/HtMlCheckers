import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sun.net.httpserver.*;

public class MoveReceiver implements HttpHandler{

	Launcher master;
	
	public MoveReceiver(Launcher master){
		this.master = master;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
        InputStreamReader isr = new InputStreamReader(arg0.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String data = br.readLine();
        String playerName = data.substring(0, 2);
        String move = data.substring(2, 4);
        master.clicked(playerName, move);
        arg0.sendResponseHeaders(1, -1);
	}

}