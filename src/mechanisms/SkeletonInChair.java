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
    public SkeletonInChair(String name, GRTSolenoid chairPiston)
    {
        super(name, chairPiston);
        
        this.chairPiston = chairPiston;
        
    }
    
    public void activate()
    {
        chairPiston.engage(true);
        super.activate();
    }
    
    public void deactivate()
    {
        chairPiston.engage(false);
        super.deactivate();
    }
    
}
