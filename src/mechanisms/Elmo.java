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
    
    public void popOut (){
        solenoids[0].engage(true);
        super.activate();
    }
    
    public void backIn(){
        solenoids[0].engage(false);
        super.deactivate();
    }
    
}
