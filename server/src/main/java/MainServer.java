import java.io.IOException;
import java.nio.file.Path;

public class MainServer {
    public static void main(String[] args) throws IOException {
        ConfigParserHostPort parse = new ConfigParserHostPort();
        parse.readSettings(System.getProperty("user.dir")+"/settings.properties");
        int port = parse.getPort();
        ServerChat server = new ServerChat();
        server.startServer(port);
    }
}
