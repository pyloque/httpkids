package httpkids.demo;

import httpkids.server.KidsRequestDispatcher;
import httpkids.server.Router;
import httpkids.server.internal.HttpServer;

public class HelloWorld {

	public static void main(String[] args) {
		var rd = new KidsRequestDispatcher("/kids", new Router((ctx, req) -> {
			ctx.html("Hello, World");
		}));
		new HttpServer("localhost", 8080, 2, 16, rd).start();
	}

}
