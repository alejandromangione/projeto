package com.vitorueda.app.projeto.Http;

import java.util.Map;

/**
 * Created by lsitec61.ueda on 17/09/2014.
 */
public class WebServer extends com.vitorueda.app.projeto.Http.NanoHTTPD {


    private String msg;

    public WebServer() {
        super(8080);
        }

    @Override
    public Response serve(String uri, Method method,
                          Map<String, String> header,
                          Map<String, String> parameters,
                          Map<String, String> files) {
        if(uri.equalsIgnoreCase("/login")) {
            login();
        } else {
            pergunta();
        }
        return new Response(msg);
    }

    private void login() {
        cabecalho();
        this.msg += "<body>"
         + "<div id=\"mainform\">"
         + "<div id=\"form\">"
         + "<label>Name :</label>"
         + "<input id=\"name\" type=\"text\">"
         + "<label>Password :</label>"
         + "<input id=\"password\" type=\"password\">"
         + "<input id=\"submit\" type=\"button\" value=\"Submit\">"
         + "</div>"
         + "</div>"
         + "</body>"
         + "</html>";
    }

    private void pergunta() {
        cabecalho();
        this.msg += "<body>"
                + "<div id=\"mainform\">"
                + "<div id=\"form\">"
                + "<input type=\"radio\" name=\"group1\" value=\"Opcao1\" />Opcao1<br>"
                + "<input type=\"radio\" name=\"group1\" value=\"Opcao2\" />Opcao2<br>"
                + "<input id=\"submit\" type=\"button\" value=\"Submit\">"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

    private void cabecalho() {
        this.msg = "<html>"
                + "<head>"
                + "<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js\"></script>"
                + "<style>"
                + "#form {"
                + "background-color:#fff;"
                + "color:#123456;"
                + "box-shadow:0 1px 1px 1px #123456;"
                + "font-weight:400;"
                + "width:350px;"
                + "margin:50px 250px 0 35px;"
                + "float:left;"
                + "height:500px"
                + "}"
                + "#form div {"
                + "padding:10px 0 0 30px"
                + "}"
                + "h3 {"
                + "margin-top:0;"
                + "color:#fff;"
                + "background-color:#3C599B;"
                + "text-align:center;"
                + "width:100%;"
                + "height:50px;"
                + "padding-top:30px"
                + "}"
                + "#mainform {"
                + "width:960px;"
                + "margin:50px auto;"
                + "padding-top:20px;"
                + "font-family:'Fauna One',serif"
                + "}"
                + "input {"
                + "width:90%;"
                + "height:30px;"
                + "margin-top:10px;"
                + "border-radius:3px;"
                + "padding:2px;"
                + "box-shadow:0 1px 1px 0 #123456"
                + "}"
                + "input[type=button] {"
                + "background-color:#3C599B;"
                + "border:1px solid #fff;"
                + "font-family:'Fauna One',serif;"
                + "font-weight:700;"
                + "font-size:18px;"
                + "color:#fff"
                + "}"
                + "</style>"
                + "</head>";
    }
}
