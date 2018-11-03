import java.io.IOException;


import com.sun.net.httpserver.*;

public class NameGiver implements HttpHandler {

	Launcher master;
	
	public NameGiver(Launcher master){
		this.master = master;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		arg0.sendResponseHeaders(master.nextPlayer(), -1);
	}

}
