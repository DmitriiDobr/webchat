import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ChatLog implements IChatLogger{

    private static Logger writeLog = Logger.getLogger("Chat logger");

    @Override
    public synchronized void writeMessage(String msg,String path) throws IOException {
        FileHandler fh = new FileHandler(path);
        writeLog.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        writeLog.info(msg);
    }
}
