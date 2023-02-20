import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigParserHostPort {

    private static int port;
    private static String host;


    public void readSettings(String PATH) throws IOException {
        FileReader input = new FileReader(
                PATH
        );

        Properties props = new Properties();
        props.load(input);

        port = Integer.parseInt(props.getProperty("port"));
        host = props.getProperty("host");

    }

    public int getPort(){
        return port;
    }

    public String getHost(){
        return host;
    }
}
