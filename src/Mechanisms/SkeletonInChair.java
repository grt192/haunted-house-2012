/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Mechanisms;

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
    
    @Override
    public void extend()
    {
        chairPiston.engage(true);
        super.extend();
    }
    
    @Override
    public void retract()
    {
        chairPiston.engage(false);
        super.retract();
    }
    
}
