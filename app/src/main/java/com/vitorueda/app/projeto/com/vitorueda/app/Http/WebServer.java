package com.vitorueda.app.projeto.com.vitorueda.app.Http;

import java.util.Map;

/**
 * Created by lsitec61.ueda on 17/09/2014.
 */
public class WebServer extends NanoHTTPD {
    private String msg;

    public WebServer() {
        super(8080);
        msg = "<html><body><h1>Hello server</h1>\n";
        msg += "</body></html>\n";
    }

    public Response teste(String msg) {
        return new NanoHTTPD.Response(msg);
    }

    @Override
    public Response serve(String uri, Method method,
                          Map<String, String> header,
                          Map<String, String> parameters,
                          Map<String, String> files) {
        return new NanoHTTPD.Response(msg);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
