/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.Sensor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import event.events.ButtonEvent;
import event.listeners.ButtonListener;
import java.util.Enumeration;
import java.util.Vector;

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
	
	public static final int TOGGLE_RED = 12;
	public static final int TOGGLE_GREEN = 10;	
	public static final int ARCADE_ORANGE = 14;
	public static final int ARCADE_GREEN = 16;
	
	private static final int REGISTER_CLK = 6;
	private static final int REGISTER_D = 2;
	private static final int REGISTER_LOAD = 4;
	
	public static final int NUM_BUTTONS = 12;
	
	private final Vector buttonListeners = new Vector();
	
	DriverStationEnhancedIO io = DriverStation.getInstance().getEnhancedIO();
	
	/**
     * State definitions
     */
    public static final double PRESSED = TRUE;
    public static final double RELEASED = FALSE;
	
	private boolean[] LEDState = new boolean[16];
	
	public ButtonPanel(String name) throws DriverStationEnhancedIO.EnhancedIOException {
		super(name, 50, NUM_BUTTONS);
		
		io.setDigitalConfig(BUTTON1, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(BUTTON2, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(BUTTON3, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(BUTTON4, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(BUTTON5, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(BUTTON6, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(BUTTON7, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(BUTTON8, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(TOGGLE_RED, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(TOGGLE_GREEN, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(ARCADE_ORANGE, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		io.setDigitalConfig(ARCADE_GREEN, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
		
		io.setDigitalConfig(REGISTER_CLK, DriverStationEnhancedIO.tDigitalConfig.kOutput);
		io.setDigitalConfig(REGISTER_D, DriverStationEnhancedIO.tDigitalConfig.kOutput);
		io.setDigitalConfig(REGISTER_LOAD, DriverStationEnhancedIO.tDigitalConfig.kOutput);
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
	
	public void addButtonListener(ButtonListener b) {
        buttonListeners.addElement(b);
    }

    public void removeButtonListener(ButtonListener b) {
        buttonListeners.removeElement(b);
    }
	
	private void updateLEDs() throws DriverStationEnhancedIO.EnhancedIOException {
		io.setDigitalOutput(REGISTER_LOAD, false);
		
		for (int i = 0; i < LEDState.length; i++) {
			io.setDigitalOutput(REGISTER_CLK, false);
			io.setDigitalOutput(REGISTER_D, LEDState[i]);
			io.setDigitalOutput(REGISTER_CLK, true);
		}
		
		io.setDigitalOutput(REGISTER_LOAD, true);
	}
}
