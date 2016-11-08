package com.kidbear.plane.core;

import com.kidbear.plane.net.socket.WebSocketServer;

public class GameServer {
	public static volatile boolean shutdown = false;
	private static GameServer server;

	private GameServer() {
	}

	public static GameServer getInstance() {
		if (null == server) {
			server = new GameServer();
		}
		return server;
	}

	/**
	 * @Title: startServer
	 * @Description: 开启服务器
	 * @throws
	 */
	public void startServer() {
		// SocketServer.getInstance().start();
		// HttpServer.getInstance().start();
		WebSocketServer.getInstance().start();
		shutdown = false;
	}

	/**
	 * @Title: shutServer
	 * @Description: 关闭服务器
	 * @throws
	 */
	public void shutServer() {
		// SocketServer.getInstance().shut();
		// HttpServer.getInstance().shut();
		WebSocketServer.getInstance().shut();
		shutdown = true;
	}
}
