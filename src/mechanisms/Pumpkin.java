/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanisms;

import actuator.GRTSolenoid;
import core.HouseMech;

/**
 *
 * @author Angie
 */
public class Pumpkin extends HouseMech{
    
	public Pumpkin(GRTSolenoid solenoid){
        super("Pumpkin", solenoid);
    }

    protected void retract (){
        solenoids[0].engage(false);
    }

    protected void extend() {
        solenoids[0].engage(true);
    }
}
