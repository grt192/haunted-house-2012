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
    public Pumpkin(String name, GRTSolenoid solenoid){
        super(name, solenoid);
    }

    public void retract (){
        super.activate();
        solenoids[0].engage(false);
    }

    public void extend(){
        super.deactivate();
        solenoids[0].engage(true);
    }
}