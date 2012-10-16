/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanisms;

import actuator.GRTSolenoid;
import core.HouseMech;

/**
 *
 * @author darshan
 */
public class Elmo extends HouseMech{
    
    public Elmo(GRTSolenoid sol){
        super("Elmo", sol);
    }
    
    public void popOut() {
        activate();
    }
    
    public void backIn() {
        deactivate();
    }
    
    protected void extend(){
        solenoids[0].engage(true);
    }
    
    protected void retract(){
        solenoids[0].engage(false);
    }
    
}
