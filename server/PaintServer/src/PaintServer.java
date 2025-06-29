import java.io.*;
import java.net.*;

public class PaintServer
{
    private ServerSocket serverSocket;

    public void start(int port) throws IOException
    {
        System.out.println("Esperando conexiones...");
        ConnectionsManager connectionsManager = ConnectionsManager.getConnectionManager();
        serverSocket = new ServerSocket(port);
        while(true){
            ClientConnection client = new ClientConnection(serverSocket.accept());
            System.out.println("Conexion recibida");
            connectionsManager.addClient(client);
            client.start();
        }
    }

    public static void main(String[] args) throws IOException
    {
        PaintServer server = new PaintServer();
        server.start(6666);
    }
}
