import java.io.*;
import java.net.*;

public class Client
{
    private Socket socket;
    private String clientId;
    private PrintWriter out;
    private BufferedReader in;

    public Client(Socket socket)
    {
        this.socket = socket;
        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientId = in.readLine();
        }catch (IOException e){
            System.out.println("Error al recibir conexion");
        }
        System.out.println("Nuevo cliente: "+clientId);
    }




}
