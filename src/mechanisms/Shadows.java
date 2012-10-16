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
        shadows = solenoids[0];
    }

    protected void extend() {
        shadows.engage(true);
    }

    protected void retract() {
        shadows.engage(false);
    }
}
