import java.io.IOException;

public interface Iserver {

    void sendMessageToAllClients(String message) throws IOException;
    void removeClient(ClientHandler client);
}
