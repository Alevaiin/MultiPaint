import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class PaintServer
{
    private ServerSocket serverSocket;

    public void start(int port) throws IOException
    {
        System.out.println("Esperando conexiones...");
        List<ClientConnection> clients = new ArrayList<>();
        serverSocket = new ServerSocket(port);
        while(true){
            ClientConnection client = new ClientConnection(serverSocket.accept());
            System.out.println("Conexion recibida");
            clients.add(client);
            client.start();
        }

    }


    public static void main(String[] args) throws IOException
    {
        PaintServer server = new PaintServer();
        server.start(6666);
    }
}