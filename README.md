httpkids
--
web framework based on netty for kids, java10 required!

HelloWorld
--
```java
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

http://localhost:8080/kids
```


FullStack
--
```java
import java.util.HashMap;

import httpkids.server.KidsRequestDispatcher;
import httpkids.server.Router;
import httpkids.server.internal.HttpServer;

public class HelloWorld {

    public static void main(String[] args) {
        var router = new Router((ctx, req) -> {
            ctx.html("Hello, World");  // 纯文本html
        }).handler("/hello.json", (ctx, req) -> {
            ctx.json(new String[] { "Hello", "World" });  // JSON API
        }).handler("/hello", (ctx, req) -> {
            var res = new HashMap<String, Object>();
            res.put("req", req);
            ctx.render("playground.ftl", res);  // 模版渲染
        }).handler("/world", (ctx, re) -> {
            ctx.redirect("/hello");  // 302重定向
        }).child("/user", () -> {   // 路由嵌套
            return new Router((ctx, req) -> {
                ctx.html("Hello, World");
            }).handler("/hello.json", (ctx, req) -> {
                ctx.json(new String[] { "Hello", "World" });
            }).handler("/hello", (ctx, req) -> {
                var res = new HashMap<String, Object>();
                res.put("req", req);
                ctx.render("playground.ftl", res);
            }).handler("/world", (ctx, re) -> {
                ctx.redirect("/hello");
            }).filter((ctx, req, before) -> {  // 请求过滤器、拦截器
                if (before) {
                    System.out.printf("before %s\n", req.path());
                } else {
                    System.out.printf("after %s\n", req.path());
                }
                return true;
            });
        }).resource("/pub", "/static");  // 静态资源

        var rd = new KidsRequestDispatcher("/kids", router);
        rd.templateRoot("/tpl");  // 模版classpath目录

        var server = new HttpServer("localhost", 8080, 2, 16, rd);
        server.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                server.stop();  // 优雅停机
            }

        });
    }

}

http://localhost:8080/kids
http://localhost:8080/kids/hello
http://localhost:8080/kids/hello.json
http://localhost:8080/kids/world
http://localhost:8080/kids/user
http://localhost:8080/kids/user/hello
http://localhost:8080/kids/user/hello.json
http://localhost:8080/kids/user/world
http://localhost:8080/kids/pub/bootstrap.min.css
```

Discussion
--
关注公众号「码洞」，我们一起来聊聊这个框架

