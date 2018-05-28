package clients;

import VSMS.Agent;
import VSMS.Main;
import edu.wpi.rail.jrosbridge.messages.geometry.Point;
import edu.wpi.rail.jrosbridge.messages.geometry.Pose;
import edu.wpi.rail.jrosbridge.messages.geometry.Twist;
import extras.Quat;
//import msgs.ModelState;
//import msgs.ModelStates;
//import msgs.SpawnModel;

/**
 * Created by the following students at the University of Antwerp
 * Faculty of Applied Engineering: Electronics and ICT
 * Pepermans Manu
 **/

/**
 * Updates simulated agents
 */
public class SimulatedClient extends Client {

    String agentName;

    public SimulatedClient(String ip, int port, String id,  String agentName){
        super(ip, port, id);
        this.agentName = agentName;
        init();
        simulatedAgents.add(Main.agentHandler.newAgent(agentName, new Pose(new Point(0, 0, 0), Quat.toQuaternion(0,0,0)), new Twist()));

    }

    /**
     * Get robots from client and update robots already tracked
     */
    public void updateOwnedAgents(){

    }
}
