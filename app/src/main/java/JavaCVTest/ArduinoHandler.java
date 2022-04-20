package JavaCVTest;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ArduinoHandler {
    private int port = 1337;
    private String ipArd = "192.168.0.12";
    private DataOutputStream dos;
    private DataInputStream dis;
    private byte messageFromArduino;
    private String ledOn = "a";

    public ArduinoHandler() throws IOException {
        connectToArduino();
    }

    public void connectToArduino() throws IOException {

            Socket socket = new Socket(ipArd, port);
            System.out.println("new game started");
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            new Handler();

    }

    public void run(){ // java serverns tråd
    }

    private class Handler extends Thread{

        public Handler(){
            start();
        }

        @Override
        public void run() {
            try {
                while(!interrupted()) {
                    messageFromArduino = dis.readByte();
                    System.out.println("arduino-server called victory");

                    if(messageFromArduino == 71){
                        System.out.println("YOU WON!");
                        dos.write(ledOn.getBytes());
                        dos.flush();
                        currentThread().interrupt();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ArduinoHandler();

    }

}
