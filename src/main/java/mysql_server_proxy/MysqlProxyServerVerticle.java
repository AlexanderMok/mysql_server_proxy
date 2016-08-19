package mysql_server_proxy;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetServer;

public class MysqlProxyServerVerticle extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlProxyServerVerticle.class);

	private final int port = 3306;
	private final String mysqlHost = "192.489.15";
	
	@Override
	public void start() throws Exception {
		
		//Create a TCP/SSL server using default options. A proxy server
		NetServer netServer = vertx.createNetServer();
		
		//Like above. Client to connnect to mysql server
		NetClient netClient = vertx.createNetClient();
		
		netServer.connectHandler(socket -> netClient.connect(port, mysqlHost, result -> {
			// response to client
			if (result.succeeded()) {
				new MysqlProxyConnection(socket, result.result()).proxy();
			} else {
				LOGGER.error(result.cause().getMessage(),result.cause());
			}
		}))
		.listen(port, listenHandler -> {
			if (listenHandler.succeeded()) {
				LOGGER.info("MySQl proxy server start up");
			} else {
				LOGGER.error("Mysql proxy exit. because: " + listenHandler.cause().getMessage(), listenHandler.cause());
			    
				System.exit(1);
			}
		});
	}
}
