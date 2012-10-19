/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.HouseMech;
import java.util.Random;

/**
 *
 * @author student
 */
public class HouseAutoController extends Thread {
	private final HouseMech mech;
	private long startTime;
	private boolean autonomous = false;
	private final long[] waitTimes;
	private static Random random = new Random();
	public static final int INDEX_MIN_TIME_EXTENDED = 0;
	public static final int INDEX_MAX_TIME_EXTENDED = 1;
	public static final int INDEX_MIN_TIME_RETRACTED = 2;
	public static final int INDEX_MAX_TIME_RETRACTED = 3;
	/**
	 * Constructor
	 * @param mech
	 * @param waitTimes Array of format { min_extended_time, max_extended_time, min_retracted_time, max_retracted_time } all in milliseconds.
	 */
	public HouseAutoController(HouseMech mech, long[] waitTimes){
		this.mech = mech;
		this.waitTimes = waitTimes;
	}
	
	public void run(){
		while (true){
			if (autonomous){
				boolean extended = mech.getCurrentState();
				
				long sleepTime = random.nextLong();
				sleepTime = sleepTime % (waitTimes[extended ? INDEX_MAX_TIME_RETRACTED : INDEX_MAX_TIME_EXTENDED] 
							- waitTimes[extended ? INDEX_MIN_TIME_RETRACTED : INDEX_MIN_TIME_RETRACTED]) 
							+ waitTimes[extended ? INDEX_MIN_TIME_RETRACTED : INDEX_MIN_TIME_RETRACTED];
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}				
			}
		}
	}
	public void beginAutonomous(){
		startTime = System.currentTimeMillis();
		autonomous = true;
	}
	public void endAutonomous(){
		autonomous = false;
	}
}
