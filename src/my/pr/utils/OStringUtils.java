/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
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
