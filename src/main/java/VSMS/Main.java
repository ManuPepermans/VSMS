package VSMS;

import clients.Client;
import clients.RealClient;
import edu.wpi.rail.jrosbridge.messages.geometry.Point;
import edu.wpi.rail.jrosbridge.messages.geometry.Pose;
import edu.wpi.rail.jrosbridge.messages.geometry.Twist;
import extras.Quat;

import static clients.Client.createID;
import static java.lang.Thread.sleep;

/**
 * Created by the following students at the University of Antwerp
 * Faculty of Applied Engineering: Electronics and ICT
 * Manu Pepermans.
 **/
public class Main {

    public static boolean debug = true;

    private ClientReceiver clientReceiver;
    public static AgentHandler agentHandler;

    /**
     * New RSMS starts client accepting service and robot updater
     * @param args
     */
    public Main(String[] args) throws  InterruptedException {


        //init vars and services
        init();

        //Init Threads
        Thread clientAccepter = new Thread(clientReceiver);
        Thread robotUpdater = new Thread(agentHandler);

        //Accept new clients and subscribe to topic for robot updates
        clientAccepter.start();

        //Push updates to robots
        robotUpdater.start();

        //Test with some standard agents
        if (args.length > 0)
        {
            testRaytracing();
        }

        while(true){

        }


    }


    /**
     * Used to test raytracing
     */
    public void testRaytracing(){
        String testId = createID();
        RealClient client = new RealClient("127.0.0.1", 9090, testId, "test");
        client.realAgents.add(agentHandler.newAgent("main", new Pose(new Point(-1, 0, 0), Quat.toQuaternion(0,0,0)), new Twist()));
        client.realAgents.add(agentHandler.newAgent("main", new Pose(new Point(1, 0, 0), Quat.toQuaternion(0,0,0)), new Twist()));
    }

    /**
     * Init handlers
     * clientReciever: connection with a client that can handle multiple agents
     * agentHandler: receives position of an agent and answers with other positions of the agents
     */
    private void init(){
        clientReceiver = new ClientReceiver(this);
        agentHandler = new AgentHandler();
    }

    public static void main(String[] args) throws InterruptedException{
        new Main(args);
    }



    }



