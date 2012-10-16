/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanisms;

import actuator.GRTSolenoid;
import core.HouseMech;

/**
 *
 * @author drewbent
 */
public class SpinningHead extends HouseMech {
    
    public SpinningHead(GRTSolenoid sol) {
        super("SpinningHead", sol);
    }

    protected void extend() {
        solenoids[0].engage(true);
    }

    protected void retract() {
        solenoids[0].engage(false);
    }
    
}
