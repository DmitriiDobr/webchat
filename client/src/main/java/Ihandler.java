import java.io.IOException;

public interface Ihandler extends Runnable {

    void sendMsg(String msg) throws IOException;
    void close() throws IOException;
    void run();
}
