package VSMS;

import clients.Client;
import com.google.gson.*;
import com.sun.xml.internal.bind.v2.TODO;
import edu.wpi.rail.jrosbridge.messages.geometry.Pose;
import edu.wpi.rail.jrosbridge.messages.geometry.Twist;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by the following students at the University of Antwerp
 * Faculty of Applied Engineering: Electronics and ICT
 * Manu Pepermans
 **/

/**
 * Handles messages from agents. Answers with other locations
 * */
public class AgentHandler implements Runnable{
    public boolean shutdown = false;
    public boolean communication = true;
    int serverPort = 6666;
    PrintWriter out = null;
    //List<DrawCar.Car> cars;
    ServerSocket serverSocket;
    private long agentCounter = 0;
    private List<Client> clients;
    /**
     * New Agenthandler
     * Initialize server for clients
     */
    public AgentHandler(){
            try {
                serverSocket = new ServerSocket(serverPort);
            } catch (IOException e) {
                System.err.println("Could not open serverSocket");
                if(Main.debug)
                    e.printStackTrace();
            }

        clients = new ArrayList<Client>();
    }

    /**
     * Get all the agents positions
     */
    public void agentPositions()

    {
        for(int i = 0; i < clients.size();i++)
        {
            for(int ii = 0; ii < clients.get(i).realAgents.size(); ii++)
            {

            }
            for(int ii = 0; ii < clients.get(i).simulatedAgents.size(); ii++)
            {

            }
        }
    }

    /**
     * Add a client
     * @param client
     */
    public void addClient(Client client){
        clients.add(client);
    }

    /**
     * Create a new robot with a new ID
     * @param model_name
     * @param pose
     * @param twist
     * @return robot
     */
    public Agent newAgent(String model_name, Pose pose, Twist twist){
        agentCounter++;
        return new Agent(agentCounter, model_name, pose, twist, this);
    }


    /**
     * Continuously update every non local robot's location for every client
     */
    public void run() {
        while (!shutdown) {
            while(!shutdown){
                if(communication){
                    try {

                        Socket server = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                        PrintWriter out = null;

                        String clientInfo = in.readLine();
                        System.out.println(clientInfo);
                        InetAddress IP = server.getInetAddress();

                        //parsing JSON (pose)
                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(clientInfo, JsonObject.class);
                        JsonObject poseDetails = jsonObject.getAsJsonObject("pose");

                        String test = poseDetails.toString();
                        Pose pose = Pose.fromJsonString(test);

                        // Get ID from client and agent
                        int clientId = jsonObject.get("clientID").getAsInt();
                        int agentId = jsonObject.get("agentID").getAsInt();

                        // Update agent
                        Agent agent = clients.get(clientId).realAgents.get(agentId);
                        agent.updateRobot(pose);
                        try {
                            out = new PrintWriter(new PrintStream(server.getOutputStream()));
                            int agentCounterLoop =0;
                            int clientCounter = 0;
                            for(Client clientsAgent : clients){


                                for(Agent ownedRobots : clientsAgent.realAgents)
                            {

                                if(clientId!=clientCounter) {

                                    out.println("{\"pose\":" + ownedRobots.pose+"}");
                                }
                                //System.out.println("Client: "+clientCounter + " ,Agent: " +robotCounter + ", pose:"+ownedAgents.pose);
                                agentCounterLoop++;

                            }
                                clientCounter ++;

                            }
                            out.println("END");


                        }
                        finally {
                            {
                                if(out != null){
                                    out.close();
                                }
                                server.close();
                            }
                        }

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
}
