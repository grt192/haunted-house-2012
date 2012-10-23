/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import event.events.ButtonEvent;
import event.events.HouseMechEvent;
import event.listeners.ButtonListener;
import event.listeners.HouseMechListener;
import sensor.ButtonPanel;

/**
 * Controller for the haunted house using button panel.
 *
 * @author Calvin
 */
public class HauntedHouseController extends EventController
        implements ButtonListener, HouseMechListener {

    ButtonPanel panel = ButtonPanel.getInstance();
    HouseAutoController[] mechs;

    public HauntedHouseController(String name, HouseAutoController[] mechs) {
        super(name);
        if (mechs.length > 8)
            throw new IllegalArgumentException(
                    "HauntedHouseController can only handle 8 mechs, given "
                    + mechs.length);

        this.mechs = mechs;
    }

    protected void startListening() {
        panel.addButtonListener(this);
        for (int i = 0; i < mechs.length; i++)
            mechs[i].getMech().addStateChangeListener(this);
    }

    protected void stopListening() {
        panel.removeButtonListener(this);
        for (int i = 0; i < mechs.length; i++)
            mechs[i].getMech().removeStateChangeListener(this);
    }

    public void buttonPressed(ButtonEvent e) {
        if (e.getSource() != panel)
            return;

        int mechNum = -1;
        switch (e.getButtonID()) {
            case ButtonPanel.TOGGLE_RIGHT:
                for (int i = 0; i < mechs.length; i++)
                    mechs[i].beginAutonomous();
                break;
            case ButtonPanel.TOGGLE_LEFT:
                for (int i = 0; i < mechs.length; i++)
                    mechs[i].endAutonomous();
                break;
            case ButtonPanel.BUTTON1:
                mechNum = 0;
                break;
            case ButtonPanel.BUTTON2:
                mechNum = 1;
                break;
            case ButtonPanel.BUTTON3:
                mechNum = 2;
                break;
            case ButtonPanel.BUTTON4:
                mechNum = 3;
                break;
            case ButtonPanel.BUTTON5:
                mechNum = 4;
                break;
            case ButtonPanel.BUTTON6:
                mechNum = 5;
                break;
            case ButtonPanel.BUTTON7:
                mechNum = 6;
                break;
            case ButtonPanel.BUTTON8:
                mechNum = 7;
                break;
        }

        if (mechNum >= 0)
            if (panel.getState(ButtonPanel.ARCADE_GREEN)
                    == ButtonPanel.PRESSED)
                mechs[mechNum].beginAutonomous();
            else {
                mechs[mechNum].endAutonomous();
                mechs[mechNum].getMech().toggle();
            }
    }

    public void buttonReleased(ButtonEvent e) {
    }

    public void mechExtend(HouseMechEvent e) {
        for (int i = 0; i < mechs.length; i++)
            if (mechs[i].getMech() == e.source) {
                panel.setLEDState(i, ButtonPanel.ORANGE_RIGHT);
                break;
            }
        panel.updateLEDs();
    }

    public void mechRetract(HouseMechEvent e) {
        for (int i = 0; i < mechs.length; i++)
            if (mechs[i].getMech() == e.source) {
                panel.setLEDState(i, ButtonPanel.RED_RIGHT);
                break;
            }
        panel.updateLEDs();
    }
}
