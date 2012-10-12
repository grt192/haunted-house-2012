package controller;

import core.EventController;
import core.HouseMech;
import event.events.ButtonEvent;
import event.listeners.ButtonListener;
import java.util.Vector;
import sensor.GRTXBoxJoystick;

public class HauntedHouseTestController extends EventController implements ButtonListener {

    private GRTXBoxJoystick stick;
    private Vector mechanisms;
    private int currentControlledMech = 0;	//The index of the current mechanism we are controlling.

    public HauntedHouseTestController(Vector mechanisms, GRTXBoxJoystick xbox, String name) {
        super(name);
        stick = xbox;
        this.mechanisms = mechanisms;
    }
    
    public HauntedHouseTestController(GRTXBoxJoystick xbox, String name) {
        this(new Vector(), xbox, name);
    }
    
    public void addMech(HouseMech mech){
        mechanisms.add(mech);
    }

    protected void startListening() {
        this.stick.addButtonListener(this);
    }

    protected void stopListening() {
        this.stick.removeButtonListener(this);
    }

    public void buttonPressed(ButtonEvent e) {
        
        switch (e.getButtonID()) {
            case GRTXBoxJoystick.KEY_BUTTON_0:
                ((HouseMech) mechanisms.get(currentControlledMech)).toggle();
                break;
            case GRTXBoxJoystick.KEY_BUTTON_4:
                currentControlledMech++;
                break;
            case GRTXBoxJoystick.KEY_BUTTON_5:
                currentControlledMech--;
        }
        
        currentControlledMech %= mechanisms.size();
    }

    public void buttonReleased(ButtonEvent e) {
        // TODO Auto-generated method stub
    }
}
