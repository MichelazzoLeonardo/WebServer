import configuration.Configuration;
import configuration.ConfigurationManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private static ServerSocket ss;
    private static Socket s;
    public static void main(String[] args) throws IOException {
        System.out.println("Server Starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/configuration/conf.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using WebRoot: " + conf.getWebroot());

        ss = new ServerSocket(8080);
        Thread thread = new Thread(() -> {
            try {
                sendOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        while (true) {
            System.out.println("now accepting...");
            s = ss.accept();
            System.out.println("starting thread...");
            thread.start();
            System.out.println("thread finished...");
        }
    }

    public static synchronized void sendOutput() throws IOException {
        System.out.println("new client");

        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();

        String html =
                //BODY
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-16\">\n" +
                        "    <title>Web Server</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<h1>Home Page</h1>" +
                        "</body>\n" +
                        "</html>";

        String response =
                //STATUS
                "HTTP/1.1 200 OK\r\n" +
                        //HEADER
                        "Content-length: " + html.getBytes().length + "\r\n\r\n" +
                        html;

        os.write(response.getBytes());

        os.close();
        is.close();
        s.close();
    }
}
