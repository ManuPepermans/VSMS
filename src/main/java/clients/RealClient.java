package clients;

import VSMS.Main;
import edu.wpi.rail.jrosbridge.messages.geometry.Point;
import edu.wpi.rail.jrosbridge.messages.geometry.Pose;
import edu.wpi.rail.jrosbridge.messages.geometry.Twist;
import extras.Quat;

/**
 * Created by the following students at the University of Antwerp
 * Faculty of Applied Engineering: Electronics and ICT
 * Manu Pepermans
 **/
public class RealClient extends Client {

    String agentName;

    public RealClient(String ip, int port, String id, String agentName){
        super(ip, port, id);
        this.agentName = agentName;
        init();
        //Add empty robot
        realAgents.add(Main.agentHandler.newAgent(agentName, new Pose(new Point(0, 0, 0), Quat.toQuaternion(0,0,0)), new Twist()));
    }

    /**
     * Get robots from client and update robots already tracked
     */
    public void updateOwnedAgents() {
    }

    public void drawExternalAgents() {
        return;
    }
}
