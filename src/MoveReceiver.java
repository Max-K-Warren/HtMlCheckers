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
        Headers heads = arg0.getResponseHeaders();
        heads.add("Access-Control-Allow-Origin", "*");
        heads.add("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT");
        arg0.sendResponseHeaders(200, 0);
        String data = br.readLine();
        System.out.println(data);
        /*
        if(data==null) {
            Headers heads = arg0.getResponseHeaders();
            heads.add("Access-Control-Allow-Origin", "*");
            heads.add("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT");
            arg0.sendResponseHeaders(200, 0);
        }
        else {
        	System.out.println("moves");
            String playerName = data.substring(0, 2);
            String move = data.substring(2, 4);
            master.clicked(data.substring(2, 3),data.substring(4,5), data.substring(0,1));
            Headers heads = arg0.getResponseHeaders();
            //heads.add("Access-Control-Allow-Origin", "*");
            //heads.add("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT");
            arg0.sendResponseHeaders(200, 0);
        }
        */
	}

}