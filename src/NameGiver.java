import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.*;

public class NameGiver implements HttpHandler {

	Launcher master;
	
	public NameGiver(Launcher master){
		this.master = master;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
        String response = String.valueOf(master.nextPlayer());
        Headers heads = arg0.getResponseHeaders();
        heads.add("Access-Control-Allow-Origin", "*");
        arg0.sendResponseHeaders(200, response.length());
        OutputStream os = arg0.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
	}

}
