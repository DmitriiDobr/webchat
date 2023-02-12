import java.io.IOException;

public interface IChatLogger {
    void writeMessage(String msg,String path) throws IOException;
}
