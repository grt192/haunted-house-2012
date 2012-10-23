/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event.events;

import core.HouseMech;

/**
 *
 * @author Calvin
 */
public class HouseMechEvent {
    
    public final HouseMech source;
    
    public HouseMechEvent(HouseMech h) {
        source = h;
    }
}
