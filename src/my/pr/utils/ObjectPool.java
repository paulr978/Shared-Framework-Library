/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

import java.util.*;


public abstract class ObjectPool {
	
	public abstract boolean isAdhoc();
	public abstract int getMaxSize();
	public abstract int getMinSize();
	public abstract int getInitialSize();
	public abstract long getExpirationTimeout();
	public abstract boolean validate(Object object);
	public abstract boolean expire(Object object);
	public abstract Object create();

	
	private Hashtable locked = null;
	private Hashtable unlocked = null;
	
	
	public ObjectPool() {
		locked = new Hashtable();
		unlocked = new Hashtable();
	}
	
	public synchronized Object checkOut() {
		long now = System.currentTimeMillis();
		
		if(isAdhoc()) {
			System.out.println("ADHOC - CheckOut");
			Object newObject = create();
			addLocked(newObject, now);
			return newObject;
		}
		
		else {
			System.out.println("NORMAL - CheckOut");
			if(getUnlockedSize() > 0) {
				
				Enumeration keys = unlocked.keys();
				
				while(keys.hasMoreElements()) {
					Object currentObject = keys.nextElement();
					
					if(validate(currentObject)) {
						removeUnlocked(currentObject);
						addLocked(currentObject, now);
						return currentObject;
					}
					
					else {
						//Need to review
						System.out.println("Validation Failed");
						terminate(currentObject);
						
					}
				}
			}
			
			if(getUnlockedSize() + getLockedSize() < getMaxSize()) {
				Object newObject = create();
				addLocked(newObject, now);
				return newObject;
			}
			
			else {
				//Should be an exception instead of S.O.P()
				System.out.println("Max Objects Reached...");
				return null;
			}

		}
		
		
	}
	
	public synchronized void checkIn(Object object) {
		
		if(validate(object)) {
			
			removeLocked(object);
			addUnlocked(object);
			
			if(isAdhoc()) {
				System.out.println("ADHOC - CheckIn");
				terminate(object);
			}

		}
		else {
			System.out.println("Object Validation Failed Upon Check-In.");
			//removeLocked(object);
			terminate(object);
			//Object newObject = create();
			//addUnlocked(newObject);
			
			//Check if error was fatal - if fatal(effects all connections), refresh pool...
			
			
		}
		
	}
	
	public synchronized void terminate(Object object) {
		removeLocked(object);
		removeUnlocked(object);
		expire(object);
		object = null;
	}
	
	private void addUnlocked(Object object) {
		long now = System.currentTimeMillis();
		unlocked.put(object, now);
		System.out.println("Add Unlocked Pooled Object: " + object + " - (Unlocked / Locked) " + getUnlockedSize() + " / " + getLockedSize());
	}
	
	private void removeUnlocked(Object object) {
		unlocked.remove(object);
		System.out.println("Remove Unlocked Pooled Object: " + object + " - (Unlocked / Locked) " + getUnlockedSize() + " / " + getLockedSize());

	}
	
	private void addLocked(Object object, long time) {
		locked.put(object, time);
		System.out.println("Add Locked Pooled Object: " + object + " - (Unlocked / Locked) " + getUnlockedSize() + " / " + getLockedSize());
	}
	
	private void removeLocked(Object object) {
		locked.remove(object);
		System.out.println("Remove Locked Pooled Object: " + object + " - (Unlocked / Locked) " + getUnlockedSize() + " / " + getLockedSize());
	}
	
	private int getUnlockedSize() {
		return unlocked.size();
	}
	
	private int getLockedSize() {
		return locked.size();
	}
	
	public int getTotalPoolSize() {
		return locked.size() + unlocked.size();
	}
	

}
