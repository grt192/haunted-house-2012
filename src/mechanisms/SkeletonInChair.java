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

    private GRTSolenoid chairPiston;

    /**
     *
     * @param name
     * @param chairPiston
     */
    public SkeletonInChair(String name, GRTSolenoid chairPiston) {
        super(name, chairPiston);

        this.chairPiston = chairPiston;

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
        chairPiston.engage(true);
    }

    protected void retract() {
        chairPiston.engage(false);
    }
}
