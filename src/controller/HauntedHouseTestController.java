package controller;

import sensor.GRTXBoxJoystick;
import sensor.base.GRTXboxDriverStation;
import core.EventController;
import core.HouseMech;
import event.events.ButtonEvent;
import event.events.XboxJoystickEvent;
import event.listeners.ButtonListener;
import event.listeners.XboxJoystickListener;

public class HauntedHouseTestController extends EventController implements ButtonListener {

	private GRTXBoxJoystick stick;
	private HouseMech[] mechanisms;
	private int currentControlledMech = 0;	//The index of the current mechanism we are controlling.
	
	public HauntedHouseTestController(HouseMech[] mechs, GRTXBoxJoystick xbox, String name) {
		super(name);
		this.stick = xbox;
		this.mechanisms = mechs;
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
		if(e.getButtonID() == GRTXBoxJoystick.KEY_BUTTON_4){
			this.currentControlledMech = (this.currentControlledMech - 1) % this.mechanisms.length;
		} else if (e.getButtonID() == GRTXBoxJoystick.KEY_BUTTON_5){
			this.currentControlledMech = (this.currentControlledMech + 1) % this.mechanisms.length;
		} else if (e.getButtonID() == GRTXBoxJoystick.KEY_BUTTON_0){
			this.mechanisms[this.currentControlledMech].toggle();
		}
	}

	@Override
	public void buttonReleased(ButtonEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

