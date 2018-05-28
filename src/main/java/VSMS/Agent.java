package VSMS;

import edu.wpi.rail.jrosbridge.messages.geometry.*;

/**
 * Created by the following students at the University of Antwerp
 * Faculty of Applied Engineering: Electronics and ICT
 * Manu Pepermans based on previous work of Arthur Janssens
 *
 **/

/**
 * Agent
 */
public class Agent {

    public long id;
    public String model_name;
    public Pose pose;
    public Twist twist;
    private AgentHandler agentHandler;

    public boolean created = false;

    /**
     * new Agent
     * @param id
     * @param model_name
     * @param pose
     * @param twist
     * @param agentHandler
     */
    public Agent(long id, String model_name, Pose pose, Twist twist, AgentHandler agentHandler){
        this.id=id;
        this.model_name=model_name;
        this.pose=pose;
        this.twist=twist;
        this.agentHandler = agentHandler;
    }

    public Agent clone(){
        return new Agent(id, model_name, pose, twist, null);
    }

    public Agent(String str){
        this.model_name = str;
    }

    public void updateRobot(Pose pose){
        this.pose=pose;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() == this.getClass()){
            return ((Agent) o).model_name.equals(this.model_name);
        }else if(o.getClass() == String.class){
            return o == this.model_name;
        }
        return false;
    }

}
