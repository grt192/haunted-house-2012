package sensor;

import core.Sensor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.Timer;
import event.events.ButtonEvent;
import event.listeners.ButtonListener;
import java.util.Enumeration;
import java.util.Vector;
import logger.GRTLogger;

/**
 * Button panel to be used to control the haunted house.
 *
 * @author Calvin Huang
 */
public class ButtonPanel extends Sensor {

    public static final int BUTTON1 = 1;
    public static final int BUTTON2 = 3;
    public static final int BUTTON3 = 5;
    public static final int BUTTON4 = 7;
    public static final int BUTTON5 = 9;
    public static final int BUTTON6 = 11;
    public static final int BUTTON7 = 13;
    public static final int BUTTON8 = 15;
    
    public static final int TOGGLE_LEFT = 10;
    public static final int TOGGLE_RIGHT = 12;
    public static final int ARCADE_ORANGE = 14;
    public static final int ARCADE_GREEN = 16;
    
    private int[] buttonPins = {1, 3, 5, 7, 9, 10, 11, 12, 13, 14, 15, 16};
    
    private static final int REGISTER_CLK = 2;
    private static final int REGISTER_D = 6;
    private static final int REGISTER_LOAD = 4;
    
    public static final int NUM_BUTTONS = 17;
    
    private final Vector buttonListeners = new Vector();
    private final LEDUpdater updater = new LEDUpdater();
    
    DriverStationEnhancedIO io = DriverStation.getInstance().getEnhancedIO();
    
    /**
     * State definitions
     */
    public static final double PRESSED = TRUE;
    public static final double RELEASED = FALSE;
    private boolean[] LEDStates = new boolean[16];
    
    private static ButtonPanel instance;
    
    public static ButtonPanel getInstance() {
        if (instance == null) {
            instance = new ButtonPanel();
            instance.startPolling();
        }
        return instance;
    }

    private ButtonPanel() {
        super("HauntedHouseButtonPanel", 50, NUM_BUTTONS);

        try {
            io.setDigitalConfig(BUTTON1, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(BUTTON2, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(BUTTON3, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(BUTTON4, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(BUTTON5, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(BUTTON6, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(BUTTON7, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(BUTTON8, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(TOGGLE_LEFT, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(TOGGLE_RIGHT, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(ARCADE_ORANGE, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            io.setDigitalConfig(ARCADE_GREEN, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);

            io.setDigitalConfig(REGISTER_CLK, DriverStationEnhancedIO.tDigitalConfig.kOutput);
            io.setDigitalConfig(REGISTER_D, DriverStationEnhancedIO.tDigitalConfig.kOutput);
            io.setDigitalConfig(REGISTER_LOAD, DriverStationEnhancedIO.tDigitalConfig.kOutput);
        } catch (EnhancedIOException e) {
            e.printStackTrace();
        }
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        ButtonEvent e = new ButtonEvent(this, id, newDatum == PRESSED);
        if (newDatum == PRESSED) {
            for (Enumeration en = buttonListeners.elements(); en.hasMoreElements();) {
                ((ButtonListener) en.nextElement()).buttonPressed(e);
            }
        } else {
            for (Enumeration en = buttonListeners.elements(); en.hasMoreElements();) {
                ((ButtonListener) en.nextElement()).buttonReleased(e);
            }
        }
    }
    
    protected void poll() {
        for(int i = 0; i < buttonPins.length; i++) {
            try {
                int currPin = buttonPins[i];
                //buttons are configured to pull low when pressed
                setState(currPin, io.getDigital(currPin) ? RELEASED : PRESSED);
            } catch (EnhancedIOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addButtonListener(ButtonListener b) {
        buttonListeners.addElement(b);
    }

    public void removeButtonListener(ButtonListener b) {
        buttonListeners.removeElement(b);
    }
        
    public boolean isPressed(int buttonID) {
        return getState(buttonID) == PRESSED;
    }

    public void updateLEDs() {
        StringBuffer s = new StringBuffer("UpdateLEDS");
        for (int i = 0; i < 16; i++)
            s.append(LEDStates[i] ? '1' : '0');
        log(s.toString());
        updater.update();
    }
    
    public void setLEDState(int buttonNum, LEDState state) {
        log("Set " + buttonNum + "led " + state.value);
        switch (state.value) {
            case LEDState.GL_VAL:
                LEDStates[2 * buttonNum] = false;
                break;
            case LEDState.RL_VAL:
                LEDStates[2 * buttonNum] = true;
                break;
            case LEDState.OR_VAL:
                LEDStates[2 * buttonNum + 1] = false;
                break;
            case LEDState.RR_VAL:
                LEDStates[2 * buttonNum + 1] = true;
                break;
        }
        updateLEDs();
    }
    
    public void setLEDStates(boolean[] states) {
        for (int i = 0; i < states.length || i < LEDStates.length; i++)
            LEDStates[i] = states[i];
        updateLEDs();
    }
    
    public static final LEDState GREEN_LEFT = new LEDState(LEDState.GL_VAL);
    public static final LEDState RED_LEFT = new LEDState(LEDState.RL_VAL);
    public static final LEDState ORANGE_RIGHT = new LEDState(LEDState.OR_VAL);
    public static final LEDState RED_RIGHT = new LEDState(LEDState.RR_VAL);
    
    public static class LEDState {
        final int value;
        
        static final int GL_VAL = 0;
        static final int RL_VAL = 1;
        static final int OR_VAL = 2;
        static final int RR_VAL = 3; 
        
        private LEDState(int value) {
            this.value = value;
        }
    }
    
    private class LEDUpdater implements Runnable {      
        
        int regNum;
        boolean updating = false;
        boolean restart = false;

        public void run() {
            updating = true;
            try {
                io.setDigitalOutput(REGISTER_LOAD, false);

                for (regNum = LEDStates.length - 1; regNum >= 0; regNum--) {
                    if (restart)
                        regNum = LEDStates.length - 1;
                    restart = false;
                    log("updating LED " + regNum);
                    io.setDigitalOutput(REGISTER_CLK, false);
                    Timer.delay(.030);
                    io.setDigitalOutput(REGISTER_D, LEDStates[regNum]);
                    Timer.delay(.012);
                    io.setDigitalOutput(REGISTER_CLK, true);
                    Timer.delay(.040);
                }

                Timer.delay(.03);
                io.setDigitalOutput(REGISTER_LOAD, true);
            } catch (EnhancedIOException ex) {
                ex.printStackTrace();
            }
            updating = false;
            log("done updating");
        }
        
        public synchronized void update() {
            if (!updating) {  //start updating
                new Thread(this).start();
                log("Starting new thread to update");
                return;
            }
            //just start from beginning in feeding to SR
            restart = true;
            log("Restarting update procedure");
        }
    }
}
