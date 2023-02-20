import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ChatLog{

    private static final Logger writeLog = Logger.getLogger("Chat logger");
    private static final String PATH = System.getProperty("user.dir")+"/log.txt";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public synchronized boolean writeMessage(String msg) throws IOException {

            try {
                PrintWriter writer = new PrintWriter(new FileWriter(PATH, true));
                writer.write(LocalDateTime.now().format(formatter) + " " + msg + "\n");
                writer.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

    }
}
