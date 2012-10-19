/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy;

import actuator.GRTSolenoid;
import core.HouseMech;
import mechanisms.*;

/**
 * HauntedHouse is the entry point.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * @author Calvin, agd, dan
 */
public class HauntedHouse {
    
	public static final int SLOT_ELMO = 0;
	public static final int SLOT_CYCLOPS = 1;
	public static final int SLOT_PUMPKIN = 2;
	public static final int SLOT_SHADOWS = 3;
	public static final int SLOT_SKELETON = 4;
	
	
	private HouseMech[] mechanisms;
    /**
     * Constructor for the haunted house. 
     * Initialize all components here.
     */
    public HauntedHouse() {
		
		HouseMech elmo = new Elmo(new GRTSolenoid(1, "ElmoSolenoid"));
		HouseMech cyclops = new Cyclops(new GRTSolenoid(2, "ElmoSolenoid"));
		HouseMech pumpkin = new Pumpkin(new GRTSolenoid(3, "ElmoSolenoid"));
		HouseMech shadows = new Shadows(new GRTSolenoid(4, "ElmoSolenoid"));
		HouseMech skeleton = new SkeletonInChair(new GRTSolenoid(5, "ElmoSolenoid"));
		
		mechanisms = new HouseMech[]{ elmo, cyclops, pumpkin, shadows, skeleton };
    }
    
}
