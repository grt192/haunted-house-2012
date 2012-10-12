package controller;

import core.EventController;
import core.HouseMech;
import event.events.ButtonEvent;
import event.listeners.ButtonListener;
import java.util.ArrayList;
import java.util.List;
import sensor.GRTXBoxJoystick;

public class HauntedHouseTestController extends EventController implements ButtonListener {

    private GRTXBoxJoystick stick;
    private List<HouseMech> mechanisms;
    private int currentControlledMech = 0;	//The index of the current mechanism we are controlling.

    public HauntedHouseTestController(List<HouseMech> mechanisms, GRTXBoxJoystick xbox, String name) {
        super(name);
        stick = xbox;
        this.mechanisms = mechanisms;
    }
    
    public HauntedHouseTestController(GRTXBoxJoystick xbox, String name) {
        this(new ArrayList<HouseMech>(), xbox, name);
    }
    
    public void addMech(HouseMech mech){
        mechanisms.add(mech);
    }

    @Override
    protected void startListening() {
        this.stick.addButtonListener(this);
    }

    @Override
    protected void stopListening() {
        this.stick.removeButtonListener(this);
    }

    @Override
    public void buttonPressed(ButtonEvent e) {
        
        switch (e.getButtonID()) {
            case GRTXBoxJoystick.KEY_BUTTON_0:
                mechanisms.get(currentControlledMech).toggle();
                break;
            case GRTXBoxJoystick.KEY_BUTTON_4:
                currentControlledMech++;
                break;
            case GRTXBoxJoystick.KEY_BUTTON_5:
                currentControlledMech--;
        }
        
        currentControlledMech %= mechanisms.size();
    }

    @Override
    public void buttonReleased(ButtonEvent e) {
        // TODO Auto-generated method stub
    }
}
