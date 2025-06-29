import exceptions.ClientDisconnectedException;
import exceptions.ClientRejectedException;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;

public class ClientConnection extends Thread
{
    private final Socket socket;
    private String clientId;
    private PrintWriter out;
    private BufferedReader in;

    private int inactiveCount = 0;
    private final ConnectionsManager connectionsManager = ConnectionsManager.getConnectionManager();

    public ClientConnection(Socket socket)
    {
        this.socket = socket;
        try
        {
            this.socket.setSoTimeout(Constants.INACTIVE_TIMEOUT_MILIS);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientId = readNextMessage();
            if(clientId == null){
                throw new ClientRejectedException("No se recibio el ID de parte del cliente");
            }
        } catch (IOException e)
        {
            System.out.println("Error al recibir conexion");
        }
    }

    public void run()
    {
        System.out.println(clientId + " se ha conectado");
        try
        {
            while (socket.isConnected() && !this.isInactive())
            {
                String message = readNextMessage();
                processMessage(message);
            }
        }catch (ClientDisconnectedException e){
            System.out.println("Se ha perdido la conexion con el cliente "+clientId);
        }
        System.out.println(clientId + " se ha desconectado");
        closeConnection();
        this.connectionsManager.removeClient(this);
    }

    public void processMessage(String message)
    {
        if(message == null)
            return;
        System.out.println(clientId + ": " + message);
        connectionsManager.broadcast(message, clientId);
    }

    private String readNextMessage() throws ClientDisconnectedException
    {
        String inputLine;
        try
        {
            inputLine = in.readLine();
        }catch (SocketTimeoutException e){
            System.out.println(clientId + " inactivo");
            this.inactiveCount++;
            return null;
        }
        catch (IOException e)
        {
            System.out.println("Error al leer del socket");
            throw new ClientDisconnectedException(e.getMessage());
        }
        this.inactiveCount = 0;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientConnection that = (ClientConnection) o;
        return Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(clientId);
    }

    public boolean isInactive(){
        return this.inactiveCount > Constants.MAX_INACTIVE_COUNT;
    }
}
