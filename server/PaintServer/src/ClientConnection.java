import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread
{
    private Socket socket;
    private String clientId;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnection(Socket socket)
    {
        this.socket = socket;
        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.out.println("Error al recibir conexion");
        }
    }

    public void run()
    {
        try
        {
            clientId = readNextMessage();
            System.out.println(clientId + " se ha conectado");
            while(socket.isConnected()){
                String message = readNextMessage();
                processMessage(message);
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void processMessage(String message){
        System.out.println(message);
    }

    private String readNextMessage() throws IOException{
        String inputLine;
        inputLine = in.readLine();
        return inputLine;
    }
}
