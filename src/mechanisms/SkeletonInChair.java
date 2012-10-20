package mechanisms;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import actuator.GRTSolenoid;
import core.HouseMech;

/**
 *
 * @author trevornielsen
 */
public class SkeletonInChair extends HouseMech {

    /**
     *
     * @param chairPiston
     */
    public SkeletonInChair(GRTSolenoid chairPiston) {
        super("skeleton", chairPiston);    
    }   

    protected void extend() {
        solenoids[0].engage(true);
    }

    protected void retract() {
        solenoids[0].engage(false);
    }
}
