/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.utils;

/**
 *
 * @author prando
 */
public class OBoolean {
    
    
    public static boolean parseInt(int bool) {
    
        if(bool == 1) {
            return true;
        }
        
        return false;
    }
    
    public static boolean parseString(String bool) {

        if(bool == null) {
            return false;
        }
        else if(bool.equalsIgnoreCase("on")) {
            return true;   
        }
        else if(bool.equalsIgnoreCase("true")) {
            return true;
        }
        else if(bool.equalsIgnoreCase("1")) {
            return true;
        }
        else {
            return false;
        }
    }
    
}
