
package mechanisms;

import actuator.GRTSolenoid;
import core.HouseMech;

/**
 *
 * @author sidd
 */
public class Shadows extends HouseMech {
    private GRTSolenoid shadows;
    
    public Shadows(String name, GRTSolenoid solenoid) {
        super(name, solenoid);
    }
    
    public void extend() {
        shadows.engage(true);
        super.extend();
    }
    
    public void retract() {
        shadows.engage(false);
        super.retract();
    }
}
    

