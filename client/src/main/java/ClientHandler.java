import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientHandler implements Ihandler {
    private static final ChatLog chatLog = new ChatLog();
    private static final String path = System.getProperty("user.dir")+"/Client.log";
    private Iserver server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private Socket clientSocket = null;
    private static int clients_count = 0;

    public ClientHandler(Socket clientSocket, Iserver server) {
        try {
            clients_count++;
            this.clientSocket = clientSocket;
            this.server = server;
            this.inMessage = new Scanner(clientSocket.getInputStream());
            this.outMessage = new PrintWriter(clientSocket.getOutputStream());


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                server.sendMessageToAllClients("Новый участник в чате!");
                server.sendMessageToAllClients("Клиентов в чате " + clients_count);
                chatLog.writeMessage("Новый участник в чате!",path);
                break;
            }
            while (true) {
                if (inMessage.hasNext()) {
                    String cliMessage = inMessage.nextLine();
                    System.out.println("сообщение клиента " + cliMessage);
                    chatLog.writeMessage(cliMessage,path);
                    if (cliMessage.equalsIgnoreCase("/exit")) {
                        break;
                    }
                    server.sendMessageToAllClients(cliMessage);


                }
                Thread.sleep(100);

            }


        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void sendMsg(String msg) throws IOException {
        chatLog.writeMessage(msg,path);
        outMessage.println(msg);
        outMessage.flush();

    }

    public synchronized void close() throws IOException {
        server.removeClient(this);
        clients_count--;
        chatLog.writeMessage("Клиентов в чате = " + clients_count,path);
        server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
    }

}
