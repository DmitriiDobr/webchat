import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.net.InetAddress;



public class Server implements Iserver {

    private ArrayList<Ihandler> clients = new ArrayList<Ihandler>();
    private static final String path = System.getProperty("user.dir")+"/Server.log";
    private static final ChatLog chatlog = new ChatLog();


    public Server() throws IOException {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            getHostDetails();
            int PORT = readerSettings();
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер вэбчата запущен!");
            while (true){
                clientSocket = serverSocket.accept();
                Ihandler client = new ClientHandler(clientSocket,this);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            clientSocket.close();
            System.out.println("Сервер остановлен");
            serverSocket.close();

        }


    }

    public void sendMessageToAllClients(String message) throws IOException {
        System.out.println(message);
        for (Ihandler o: clients){
            o.sendMsg(message);
        }
        chatlog.writeMessage("Сообщение "+message+" отправлено участникам",path);
    }

    public void removeClient(ClientHandler client){
        clients.remove(client);

    }

    private static int readerSettings() throws IOException {
        FileReader input = new FileReader(
                path
        );
        BufferedReader bufRead = new BufferedReader(input);
        String[] portLine =bufRead.readLine().split(":");
        return Integer.parseInt(portLine[1]);

    }
    private static  void getHostDetails() throws IOException {
        InetAddress ip;
        String hostname;
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        chatlog.writeMessage("IP адрес сервера: " + ip,path);
        chatlog.writeMessage("Имя хоста: " + hostname,path);
        System.out.println("IP адрес сервера: " + ip);
        System.out.println("Имя хоста: " + hostname);
    }

}
