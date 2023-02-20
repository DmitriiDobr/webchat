import java.io.BufferedReader;
import java.io.IOException;

public class MessageListeners extends Thread {

    private BufferedReader in;


    public MessageListeners(BufferedReader in){
        this.in = in;
    }

    @Override
    public void run() {
        try{
            while(!Thread.currentThread().interrupted()){
                String message = in.readLine();
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
