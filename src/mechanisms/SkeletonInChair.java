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
    
    public void run()
    {
        while(running)
        {
            
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                
            }
            
            toggle();
            
        }
    }
    
    public void startAutonomous()
    {
        startPolling();
    }
    
    public void stopAutonomous()
    {
        halt();
    }
        

    protected void extend() {
        solenoids[0].engage(true);
    }

    protected void retract() {
        solenoids[0].engage(false);
    }
}
