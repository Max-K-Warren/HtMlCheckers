import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.*;

public class DataSender implements HttpHandler{

	Launcher master;
	
	public DataSender(Launcher master){
		this.master = master;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// parse request
        InputStreamReader isr = new InputStreamReader(arg0.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String playerName = br.readLine();

        // send response
        String response = master.getGrid(playerName);
        arg0.sendResponseHeaders(200, response.length());
        OutputStream os = arg0.getResponseBody();
        os.write(response.toString().getBytes());
        //os.close();
	}

}
