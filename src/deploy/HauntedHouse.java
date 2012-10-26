package deploy;

import actuator.GRTSolenoid;
import controller.HauntedHouseController;
import controller.HouseAutoController;
import core.HouseMech;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import java.util.Random;
import mechanisms.*;
import sensor.ButtonPanel;

/**
 * HauntedHouse is the entry point.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * @author Calvin, agd, dan
 */
public class HauntedHouse extends SimpleRobot {

    public static final int MIN_EXTENDED_TIME = 3 * 1000;       //Minimum time in milliseconds a mech is extended.
    public static final int MAX_EXTENDED_TIME = 8 * 1000;       //Max time in milliseconds a mech can be extended.
    public static final int MIN_RETRACTED_TIME = 2 * 1000;      //Min time in milliseconds a mech will remain in the retracted state.
    public static final int MAX_RETRACTED_TIME = 5 * 1000;      //Max time in milliseconds a mech will remain in the retracted state.
    private HouseMech[] mechanisms;                 //Array of mechanisms on the house.
    private HouseAutoController[] autoControllers;  //Array of mechanism controllers.

    /**
     * Constructor for the haunted house. Initialize all components here.
     */
    public HauntedHouse() {

        GRTSolenoid elmoSol = new GRTSolenoid(1, "ElmoSolenoid");
        GRTSolenoid cyclopsSol = new GRTSolenoid(2, "CyclopsSolenoid");
        GRTSolenoid pumpkinSol = new GRTSolenoid(3, "PumpkinSolenoid");
        GRTSolenoid shadowsSol = new GRTSolenoid(4, "ShadowSolenoid");
        GRTSolenoid skeletonSol = new GRTSolenoid(5, "SkeletonSolenoid");
        GRTSolenoid headSol = new GRTSolenoid(6, "HeadSolenoid");
        GRTSolenoid slenderSol = new GRTSolenoid(7, "SlenderSolenoid");

        elmoSol.enable();
        cyclopsSol.enable();
        pumpkinSol.enable();
        shadowsSol.enable();
        skeletonSol.enable();
        headSol.enable();
        slenderSol.enable();

        HouseMech elmo = new Elmo(elmoSol);
        HouseMech cyclops = new Cyclops(cyclopsSol);
        HouseMech pumpkin = new Pumpkin(pumpkinSol);
        HouseMech shadows = new Shadows(shadowsSol);
        HouseMech skeleton = new SkeletonInChair(skeletonSol);
        HouseMech spinHead = new SpinningHead(headSol);
        HouseMech slender = new SpinningHead(slenderSol);   //For the record, this is cheating.
        //Probably should have been separate mech class, but because this has same functionality as SpinningHead, we reused it.

        mechanisms = new HouseMech[]{elmo, cyclops, pumpkin, shadows, skeleton,
            spinHead, slender};

        autoControllers = new HouseAutoController[mechanisms.length];

        for (int i = 0; i < mechanisms.length; i++) {
            autoControllers[i] = new HouseAutoController(mechanisms[i].getID(),
                    mechanisms[i],
                    MIN_EXTENDED_TIME,
                    MAX_EXTENDED_TIME,
                    MIN_RETRACTED_TIME,
                    MAX_RETRACTED_TIME);

            autoControllers[i].beginAutonomous();
            autoControllers[i].enable();
        }

        HauntedHouseController controller =
                new HauntedHouseController("HH Controller", autoControllers);
        controller.enable();
    }
    
    void LEDTest() {
        boolean[] states = new boolean[]{true, false, true, false,
            true, false, true, false,
            true, false, true, false,
            true, false, true, false};

        for (int i = 0;; i++) {
            for (int j = 0; j < 16; j++)
                states[j] = !states[j];
            ButtonPanel.getInstance().setLEDStates(states);
            Timer.delay(2.5);
        }
    }
}
