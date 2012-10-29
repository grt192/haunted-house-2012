package controller;

import core.EventController;
import event.events.ButtonEvent;
import event.events.HouseMechEvent;
import event.listeners.ButtonListener;
import event.listeners.HouseMechListener;
import sensor.ButtonPanel;

/**
 * Controller for the haunted house using button panel.
 * The board operates as follows:
 * 
 * Pressing a button for a mechanism toggles it, regardless of auto/manual control.
 * Pressing the rocker switch right side extends all the mechanisms,
 * left side retracts all the mechanisms.
 * 
 * Orange + button sets the mechanism to manual control.
 * Orange + rocker right sets all mechanisms to manual control.
 * 
 * Green + button sets mechanism to auto control.
 * Green + rocker right sets all mechanisms to auto control.
 * 
 * Green/orange + rocker left toggles all mechanisms.
 * 
 * If green and orange buttons are simultaneously pressed, green takes priority.
 * 
 * Green/orange LEDs indicate auto v. manual control.
 * Green is auto control, orange is manual control.
 * 
 * Orange/red LEDs indicate mech state.
 * Orange is retracted, red is extended.
 *
 * @author Calvin
 */
public class HauntedHouseController extends EventController
        implements ButtonListener, HouseMechListener {

    ButtonPanel panel = ButtonPanel.getInstance();
    HouseAutoController[] mechs;

    /**
     * Constructs a new HauntedHouseController.
     * 
     * @param name name of controller
     * @param mechs array of mechanism (autocontrollers) corresponding to the
     * panel buttons. Since there are only 8 buttons, this only takes 8 mechs.
     */
    public HauntedHouseController(String name, HouseAutoController[] mechs) {
        super(name);
        if (mechs.length > 8)
            throw new IllegalArgumentException(
                    "HauntedHouseController can only handle 8 mechs, given "
                    + mechs.length);

        log("HauntedHouseController initialized");
        this.mechs = mechs;
    }

    protected void startListening() {
        panel.enable();
        panel.addButtonListener(this);
        for (int i = 0; i < mechs.length; i++)
            mechs[i].getMech().addStateChangeListener(this);
    }

    protected void stopListening() {
        panel.disable();
        panel.removeButtonListener(this);
        for (int i = 0; i < mechs.length; i++)
            mechs[i].getMech().removeStateChangeListener(this);
    }

    public void buttonPressed(ButtonEvent e) {
        
        log("Button pressed " + e.getButtonID());

        int mechNum = -1;
        switch (e.getButtonID()) {
            case ButtonPanel.TOGGLE_RIGHT:
                if (panel.isPressed(ButtonPanel.ARCADE_GREEN)) {
                    log("All mechanisms autonomously controlled");
                    for (int i = 0; i < mechs.length; i++) {
                        mechs[i].beginAutonomous();
                        panel.setLEDState(i, ButtonPanel.GREEN_LEFT);
                    }
                } else if (panel.isPressed(ButtonPanel.ARCADE_ORANGE)) {
                    log("All mechanisms manually controlled");
                    for (int i = 0; i < mechs.length; i++) {
                        mechs[i].endAutonomous();
                        panel.setLEDState(i, ButtonPanel.RED_LEFT);
                    }
                } else {
                    log("All mechanisms extended");
                    for (int i = 0; i < mechs.length; i++)
                        mechs[i].getMech().activate();
                }               
                break;
            case ButtonPanel.TOGGLE_LEFT:
                 if (panel.isPressed(ButtonPanel.ARCADE_GREEN) ||
                         panel.isPressed(ButtonPanel.ARCADE_ORANGE)) {
                    log("All mechanisms toggled");
                    for (int i = 0; i < mechs.length; i++)
                        mechs[i].getMech().toggle();
                } else {
                    log("All mechanisms retracted");
                    for (int i = 0; i < mechs.length; i++)
                        mechs[i].getMech().deactivate();
                }
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

        if (mechNum >= 0 && mechNum < mechs.length) //button pressed was mech button
            if (panel.isPressed(ButtonPanel.ARCADE_GREEN)) {
                mechs[mechNum].beginAutonomous();
                panel.setLEDState(mechNum, ButtonPanel.GREEN_LEFT);
                log("Begin autonomous: " + mechs[mechNum].getID());
            } else if (panel.isPressed(ButtonPanel.ARCADE_ORANGE)) {
                mechs[mechNum].endAutonomous();
                panel.setLEDState(mechNum, ButtonPanel.RED_LEFT);
                log("Begin manual: " + mechs[mechNum].getID());
            } else {
                log("Toggle mech: " + mechs[mechNum].getID());
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
    }

    public void mechRetract(HouseMechEvent e) {
        for (int i = 0; i < mechs.length; i++)
            if (mechs[i].getMech() == e.source) {
                panel.setLEDState(i, ButtonPanel.RED_RIGHT);
                break;
            }
    }
}
