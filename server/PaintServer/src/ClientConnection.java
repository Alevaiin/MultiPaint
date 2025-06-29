import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread
{
    private final Socket socket;
    private String clientId;
    private PrintWriter out;
    private BufferedReader in;

    private final ConnectionsManager connectionsManager = ConnectionsManager.getConnectionManager();
    ;

    public ClientConnection(Socket socket)
    {
        this.socket = socket;
        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e)
        {
            System.out.println("Error al recibir conexion");
        }
    }

    public void run()
    {
        clientId = readNextMessage();
        System.out.println(clientId + " se ha conectado");
        while (socket.isConnected())
        {
            String message = readNextMessage();
            processMessage(message);
        }
        closeConnection();
        System.out.println(clientId + " se ha desconectado");
    }

    public void processMessage(String message)
    {
        System.out.println(clientId + ": " + message);
        connectionsManager.broadcast(message, clientId);

    }

    private String readNextMessage()
    {
        String inputLine;
        try
        {
            inputLine = in.readLine();
        } catch (IOException e)
        {
            System.out.println("Error al leer del socket");
            throw new RuntimeException(e);
        }
        return inputLine;
    }

    public String getClientId()
    {
        return this.clientId;
    }

    public void send(String message)
    {
        this.out.println(message);
    }

    public void closeConnection()
    {
        try
        {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
