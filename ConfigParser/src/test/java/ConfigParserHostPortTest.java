import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ConfigParserHostPortTest {
    protected int port;
    protected String host;

    @Before
    public void setUp() throws IOException {
        ConfigParserHostPort parse = new ConfigParserHostPort();
        parse.readSettings("/Users/dmitriidobr/IdeaProjects/webchat/settings.properties");
        host=parse.getHost();
        port=parse.getPort();
    }

    @Test
    public void testReadSettingsPort(){
        assert host.equals("192.168.0.180");
    }
    @Test
    public void testReadSettingsHost(){
        assert port==8089;
    }
}
