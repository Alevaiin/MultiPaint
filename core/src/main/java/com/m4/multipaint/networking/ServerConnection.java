package com.m4.multipaint.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.m4.multipaint.drawing.DrawAction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServerConnection
{
    String clientId;
    Socket socket;

    public ServerConnection(String clientId, String ip, int port){
        socket = Gdx.net.newClientSocket(Net.Protocol.TCP,ip, port, null);
        this.clientId = clientId;
    }

    public void connect()
    {
        while (!socket.isConnected()) //Esperamos a que se conecte
        {
            Gdx.app.log("NETWORK", "Intentando conectar");

        }
        Gdx.app.log("NETWORK", "Conectado");
        sendToServer(clientId);

    }


    public void sendActionToServer(DrawAction action)
    {
        sendToServer(action.toString());
    }

    public void sendToServer(String message){
        try{
            Gdx.app.log("NETWORK", "Sending: "+message);
            socket.getOutputStream().write((message+"\n").getBytes(StandardCharsets.UTF_8));
            socket.getOutputStream().flush();
        }catch (IOException e){
            Gdx.app.log("ERROR", "Error al comunicarse con el server");
        }
    }
}
