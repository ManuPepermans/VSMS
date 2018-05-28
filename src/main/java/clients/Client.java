package clients;

import VSMS.Agent;
import VSMS.Main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by the following students at the University of Antwerp
 * Faculty of Applied Engineering: Electronics and ICT
 * Pepermans Manu
 **/


abstract public class Client {

    protected InetAddress ip; // IP address of the client
    protected int port; // Port on which the client has connected
    protected String id;
    private static AtomicLong idCounter = new AtomicLong();

    public static String createID()
    {
        return String.valueOf(idCounter.getAndIncrement());
    }

    public final List<Agent> realAgents = Collections.synchronizedList(new ArrayList<Agent>());
    public final List<Agent> simulatedAgents = Collections.synchronizedList(new ArrayList<Agent>());


    /**
     * Create client with ip address and port number
     * @param ip ip address from client
     * @param port port to communicate with client
     */
    public Client(String ip, int port, String id){
        try {
            this.ip = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
        this.id = id;
    }

    /**
     * Add client to agentHandler to allow for agent updates
     */
    public void init(){
        Main.agentHandler.addClient(this);

        //Get Robots
        updateOwnedAgents();
    }


    //get robots from client
    abstract public void updateOwnedAgents();

}
