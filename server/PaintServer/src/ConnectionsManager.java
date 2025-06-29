import java.util.ArrayList;
import java.util.List;

public class ConnectionsManager
{
    List<ClientConnection> clients;

    public ConnectionsManager(){
        this.clients = new ArrayList<>();
    }
}
