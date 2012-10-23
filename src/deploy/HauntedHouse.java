/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy;

import actuator.GRTSolenoid;
import controller.HouseAutoController;
import core.HouseMech;
import edu.wpi.first.wpilibj.SimpleRobot;
import java.util.Random;
import logger.GRTLogger;
import mechanisms.*;

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

    public static final int SLOT_ELMO = 0;
    public static final int SLOT_CYCLOPS = 1;
    public static final int SLOT_PUMPKIN = 2;
    public static final int SLOT_SHADOWS = 3;
    public static final int SLOT_SKELETON = 4;
    public static final int MIN_EXTENDED_TIME = 100;
    public static final int MAX_EXTENDED_TIME = 500;
    public static final int MIN_RETRACTED_TIME = 100;
    public static final int MAX_RETRACTED_TIME = 500;
    private HouseMech[] mechanisms;
    private HouseAutoController[] autoControllers;

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
        HouseMech slender = new SpinningHead(slenderSol);

        mechanisms = new HouseMech[]{elmo, cyclops, pumpkin, shadows, skeleton, spinHead, slender};

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
    }
}
