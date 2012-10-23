/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event.listeners;

import event.events.HouseMechEvent;

/**
 *
 * @author Calvin
 */
public interface HouseMechListener {

    public void mechExtend(HouseMechEvent e);

    public void mechRetract(HouseMechEvent e);
}
