package mysql_server_proxy;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetSocket;

public class MysqlProxyConnection {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlProxyConnection.class);

	private final NetSocket clientSocket;
    private final NetSocket serverSocket;
    
    
	public MysqlProxyConnection(NetSocket clientSocket, NetSocket serverSocket) {
		this.clientSocket = clientSocket;
		this.serverSocket = serverSocket;
	}
    
    public void proxy() {
    	//close connection between client and proxy when connection between proxy and server close
    	serverSocket.closeHandler(handler -> clientSocket.close());
    	// like above
    	clientSocket.closeHandler(handler -> serverSocket.close());
    	
    	serverSocket.exceptionHandler(e -> {
    		//log when exception get caught
    		LOGGER.error(e.getMessage(),e);
    	    //close connection at the same time
    		close();
    	});
    	
    	clientSocket.exceptionHandler(e -> {
    		LOGGER.error(e.getMessage(), e);
            close();
    	});
    	//Dispatch data to target server
    	clientSocket.handler(buffer -> serverSocket.write(buffer));
    	//Dispatc data to target client
    	serverSocket.handler(buffer -> clientSocket.write(buffer));
    }
    
    private void close(){
    	clientSocket.close();
    	serverSocket.close();
    }
}
