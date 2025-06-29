import java.util.ArrayList;
import java.util.List;

public class ConnectionsManager
{
    private final List<ClientConnection> clients;

    private static ConnectionsManager connectionManager;

    private ConnectionsManager(){
        this.clients = new ArrayList<>();
    }

    public static ConnectionsManager getConnectionManager(){
        if(connectionManager == null)
            connectionManager = new ConnectionsManager();
        return connectionManager;
    }

    public void addClient(ClientConnection clientConnection){
        if(!clients.contains(clientConnection)){
            this.clients.add(clientConnection);
        }
        clientConnection.closeConnection();
        System.out.println(clientConnection.getClientId() + " ya esta conectado");
    }

    public void broadcast(String message, String from){
        this.clients.stream().filter( clientConnection -> !clientConnection.getClientId().equals(from) ).forEach(clientConnection -> clientConnection.send(message));
    }
}
