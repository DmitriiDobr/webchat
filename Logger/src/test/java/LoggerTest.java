import org.junit.Test;

import java.io.IOException;

public class LoggerTest {

    final String message = "log test message";

    @Test
    public void testLog() throws IOException {
        ChatLog log = new ChatLog();
        assert log.writeMessage(message);
    }
}
