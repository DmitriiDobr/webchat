import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public boolean status;
    private Scanner scanner;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private static final ChatLog logWriter = new ChatLog();

    public Client() throws IOException {
        try{
            ConfigParserHostPort parse = new ConfigParserHostPort();
            parse.readSettings(System.getProperty("user.dir")+"/settings.properties");
            int port = parse.getPort();
            String host = parse.getHost();
            socket = new Socket(host, port);
            scanner = new Scanner(System.in);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Введите свой никнейм!");
            String name = scanner.nextLine();
            out.println(name);
            logWriter.writeMessage("Никнейм  нового пользователя "+ name);
            MessageListeners listen = new MessageListeners(in);
            listen.start();
            String message ="";
            while(!"/exit".equals(message)){
                message = scanner.nextLine();
                out.println(message);
                logWriter.writeMessage("Сообщение клиента - " + name +" "+ message);

            }
            listen.interrupt();


        }catch (IOException e){
            e.printStackTrace();
        }finally {
            closeAll();
        }

    }

    public void closeAll(){
        try{
            out.close();
            in.close();
            scanner.close();
            socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }



}
