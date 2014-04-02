/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.utils;

/**
 *
 * @author prando
 */
public class OStringUtils {
    
    
    public static String emptyIfNull(String input) {
        //System.out.println(input);
        if(input == null) {
            //System.out.println("Returning Empty String");
            return "";
        }
        //System.out.println("Returning String Value");
        return input;
    }
    
}
