import configuration.Configuration;
import configuration.ConfigurationManager;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Server Starting...");


        ConfigurationManager.getInstance().loadConfigurationFile("src/configuration/conf.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using WebRoot: " + conf.getWebroot());
    }
}
