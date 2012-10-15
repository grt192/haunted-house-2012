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
    
    public void activate() {
        cyclops.engage(true);
        super.activate();
    }
    
    public void deactivate() {
        cyclops.engage(false);
        super.deactivate();
    }
}
