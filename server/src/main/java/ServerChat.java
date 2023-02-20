import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServerChat {
    private static final ChatLog logWriter = new ChatLog();
    private final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private ServerSocket server;
    private Socket socket;
    public boolean status;


    public void startServer(int port) throws IOException {
        try {
            server = new ServerSocket(port);
            System.out.println("Сервер Запущен!");
            status = true;
            getHostDetails();
            while (true) {

                socket = server.accept();

                logWriter.writeMessage("Сервер запущен!");
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                logWriter.writeMessage("Новый Клиент в вэбчате!");
                client.start();

            }

        } catch (IOException e) {
            logWriter.writeMessage("Ошибка в методе startServer() у класса " + ServerChat.class.getName());
            e.printStackTrace();
        } finally {
            logWriter.writeMessage("Закрытие потоков у класса " + ServerChat.class.getName());
            closeAll();
        }
    }

    public void closeAll() throws IOException {
        try {
            server.close();
            socket.close();

        } catch (IOException e) {
            logWriter.writeMessage("Ошибка при закрытии потоков у класса " + ServerChat.class.getName());
            e.printStackTrace();
        }
    }


    private class ClientHandler extends Thread {
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String name = in.readLine();
                sendMessageToAllClients("В чат присоединился " + name);
                logWriter.writeMessage("В чат присоединился " + name);
                String message ="";
                while (true) {
                    message = in.readLine();
                    if ("/exit".equals(message)) {
                        break;
                    }
                    logWriter.writeMessage(name + " отправил сообщение: " + message);
                    sendMessageToAllClients(name + " отправил сообщение: " + message);
                }
                logWriter.writeMessage(name + " вышел из чата!");
                sendMessageToAllClients(name + " вышел из чата!");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
               closeAllStreams();
            }

        }

        private void sendMessage(String message) {
            out.println(message);
        }

        private void sendMessageToAllClients(String message) {
            synchronized (clients) {
                clients.forEach(clientHandler -> clientHandler.sendMessage(message));

            }

        }
        private void closeAllStreams(){
            try {
                in.close();
                out.close();
            }catch (IOException e){

               e.printStackTrace();
            }
        }

    }

    private static void getHostDetails() throws IOException {
        InetAddress ip;
        String hostname;
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logWriter.writeMessage("IP адрес сервера: " + ip);
        logWriter.writeMessage("Имя хоста: " + hostname);
        System.out.println("IP адрес сервера: " + ip);
        System.out.println("Имя хоста: " + hostname);
    }


}
