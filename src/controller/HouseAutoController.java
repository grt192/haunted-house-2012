/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.GRTLoggedProcess;
import core.HouseMech;
import java.util.Random;
import logger.GRTLogger;

/**
 * A controller that controls autonomous switching
 * of a haunted house mechanism.
 * Runs on a timer on random intervals with a set range.
 *
 * @author student
 */
public class HouseAutoController extends GRTLoggedProcess {

    private final HouseMech mech;
    private static Random random = new Random();
    private final int minExtendedTime;
    private final int maxExtendedTime;
    private final int minRetractedTime;
    private final int maxRetractedTime;

    /**
     * Constructor
     *
     * @param name name of controller
     * @param mech mechanism to control
     * @param minExtendedTime minimum time to stay extended (milliseconds)
     * @param maxExtendedTime maximum time to stay extended
     * @param minRetractedTime minimum time to stay retracted
     * @param maxRetractedTime maximum time to stay retracted
     *
     */
    public HouseAutoController(String name, HouseMech mech,
            int minExtendedTime, int maxExtendedTime,
            int minRetractedTime, int maxRetractedTime) {
        
        super(name, 0);
        
        this.mech = mech;
        this.minExtendedTime = minExtendedTime;
        this.maxExtendedTime = maxExtendedTime;
        this.minRetractedTime = minRetractedTime;
        this.maxRetractedTime = maxRetractedTime;
    }
    
    protected void poll() {
        mech.toggle();        
        boolean extended = mech.getCurrentState();
		
		GRTLogger.getLogger().logInfo(extended ? "Retracting" : "Extending");
        
		int sleepTime = random.nextInt(extended
                ? maxExtendedTime - minExtendedTime
                : maxRetractedTime - minRetractedTime)
                + (extended ? minExtendedTime : minRetractedTime);
        GRTLogger.getLogger().logInfo("Going to sleep for " + sleepTime + " millis");
        setSleepTime(sleepTime);
        //sleeps according to GRTLoggedProcess
    }

    public void beginAutonomous() {
        startPolling();
    }

    public void endAutonomous() {
        halt();
    }
}
