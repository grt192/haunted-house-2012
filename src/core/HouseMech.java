package core;

import actuator.GRTSolenoid;

public abstract class HouseMech extends GRTLoggedProcess {

    protected GRTSolenoid[] solenoids;
    private boolean extended = false;	//We start in the inactive (not extended) state.

    public HouseMech(String name, GRTSolenoid[] sols) {
        super(name);
        this.solenoids = sols;
    }
    
    public HouseMech(String name, GRTSolenoid sol) {
        this(name, new GRTSolenoid[0]);
        solenoids[0] = sol;
    }

    /**
     * Extend the mechanism into its active state. Witch pops out of window, or
     * door will swing open, etc.
     *
     * Override this to define custom actuation, and call this afterwards.
     */
    public void extend() {
        this.extended = true;
    }

    /**
     * Retract the mechanism into its inactive state. Witch will go back into
     * window, door will swing closed, etc.
     *
     * Override and call super.retract();
     */
    public void retract() {
        this.extended = false;
    }

    public void toggle() {
        if (extended)
            retract();
        else
            extend();
    }
}
