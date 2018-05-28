package VSMS;

import clients.RealClient;
import clients.SimulatedClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static clients.Client.createID;


/**
 * Created by the following students at the University of Antwerp
 * Faculty of Applied Engineering: Electronics and ICT
 * Manu Pepermans
 **/

/**
 * Accepts clients and adds them to the agentHandler in order that
 */
public class ClientReceiver implements Runnable{

    private int serverPort = 6660;
    private int port = 6660;
    private boolean accepting = true;
    private boolean shutdown = false;
    PrintWriter out = null;

    Main simServer;
    ServerSocket serverSocket;

    /**
     * New connection
     * @param server
     */
    ClientReceiver(Main server){
        this.simServer=server;
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.err.println("Could not open serverSocket");
            if(Main.debug)
                e.printStackTrace();
        }
    }

    public synchronized void pause(){accepting = false;}

    public synchronized void resume(){accepting = true;}

    public synchronized void shutdown(){shutdown = true;}

    /**
     * This thread accepts clients on the VSMS.
     * When a new client is added the agent will receive a client and robot id
     * With both ID's the agent can send his updated locations
     */
    public void run() {
        while(!shutdown){
            if(accepting){
                try {
                    Socket server = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                    String clientInfo = in.readLine();
                    System.out.println(clientInfo);
                    InetAddress IP = server.getInetAddress();

                    //parsing JSON string from the agent to see whether it is a virtual or real agent?
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(clientInfo, JsonObject.class);
                    Boolean real = jsonObject.get("robot").getAsBoolean();
                    String model = jsonObject.get("model").getAsString();

                    //If the agent is real a new real client is made
                   if(real)
                    {
                       String id = createID();
                       new RealClient(IP.getHostAddress(),port, id, "Real");

                       System.out.println("New client with ID:" + id);


                        out = new PrintWriter(server.getOutputStream(), true);
                        out.println(id);
                        out.println('0');
                    }

                    else
                    {
                        String id = createID();
                        new SimulatedClient(IP.getHostAddress(),serverPort,id, "Simulated"  );
                    }

                    server.close();
                } catch (IOException e) {
                    System.err.println("Could not accept/close serverSocket connection");
                    if (Main.debug)
                        e.printStackTrace();
                }
            }else{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
