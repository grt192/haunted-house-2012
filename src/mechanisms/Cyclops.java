/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanisms;

import actuator.GRTSolenoid;
import core.HouseMech;

/**
 *
 * @author nadia
 */
public class Cyclops extends HouseMech {
    private GRTSolenoid cyclops;
    
    public Cyclops(String name, GRTSolenoid solenoid) {
        super(name, solenoid);
    }
    
    protected void extend() {
        cyclops.engage(true);
    }
    
    protected void retract() {
        cyclops.engage(false);
    }
}
