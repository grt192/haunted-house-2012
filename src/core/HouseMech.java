package core;

import actuator.GRTSolenoid;

/**
 * Generic haunted house mechanism.
 * 
 * @author agd
 */
public abstract class HouseMech extends GRTLoggedProcess {

    /**
     * Array of solenoids for this mechanism. If this HouseMech was 
     * constructed with a single solenoid and not an array, that solenoid
     * will be the first element in this solenoid array.
     */
    protected GRTSolenoid[] solenoids;
    private boolean extended = false;	//We start in the inactive (not extended) state.

    /**
     * Instantiates a house mechanism.
     * 
     * @param name name of mechanism
     * @param sols solenoids to control
     */
    public HouseMech(String name, GRTSolenoid[] sols) {
        super(name);
        this.solenoids = sols;
    }
    
    /**
     * Instantiates a house mechanism.
     * 
     * @param name name of mechanism
     * @param sol single solenoid to control
     */
    public HouseMech(String name, GRTSolenoid sol) {
        this(name, new GRTSolenoid[0]);
        solenoids[0] = sol;
    }

    /**
     * Extend the mechanism into its active state. Witch pops out of window, or
     * door will swing open, etc.
     *
     * Override this to define custom actuation, and call super.extend() afterwards.
     */
    public void activate() {
        this.extended = true;
    }

    /**
     * Retract the mechanism into its inactive state. Witch will go back into
     * window, door will swing closed, etc.
     *
     * Override and call super.retract();
     */
    public void deactivate() {
        this.extended = false;
    }

    /**
     * Toggles the mechanism. If it is extended, retract; and vice versa.
     */
    public void toggle() {
        if (extended)
            deactivate();
        else
            activate();
    }
}
