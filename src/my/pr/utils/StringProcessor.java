/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */

package my.pr.utils;

import java.util.Random;

public class StringProcessor {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getRandom(int length) {
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();

    }

    public static String stripGetFromMethodName(String methodName) {
        if (methodName.startsWith("get")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("get") + 3))) {
                return methodName.replace("get", "");
            }
        }
        return methodName;
    }

    public static String stripIsFromMethodName(String methodName) {
        if (methodName.startsWith("is")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("is") + 2))) {
                return methodName.replace("is", "");
            }
        }
        return methodName;
    }

    public static String stripHasFromMethodName(String methodName) {
        if (methodName.startsWith("has")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("has") + 3))) {
                return methodName.replace("has", "");
            }

        }
        return methodName;
    }

    public static String attachSetToAttributeName(String attributeName) {
        return "set" + attributeName;
    }

    public static String getGetterType(String methodName) {
        if (methodName.startsWith("get")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("get") + 3))) {
                return "get";
            }
        } else if (methodName.startsWith("is")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("is") + 2))) {
                return "is";
            }
        } else if (methodName.startsWith("has")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("has") + 3))) {
                return "has";
            }
        }

        return null;
    }

    public static boolean isGetterMethod(String methodName) {
        if (methodName.startsWith("get")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("get") + 3))) {
                return true;
            }
        } else if (methodName.startsWith("is")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("is") + 2))) {
                return true;
            }
        } else if (methodName.startsWith("has")) {
            if (Character.isUpperCase(methodName.charAt(methodName.indexOf("has") + 3))) {
                return true;
            }
        }

        return false;

    }

    public static String replaceGetterWithSetter(String methodName) {
        String setterName = null;

        String getterType = getGetterType(methodName);
        if (getterType.equalsIgnoreCase("get")) {
            setterName = stripGetFromMethodName(methodName);
        } else if (getterType.equalsIgnoreCase("is")) {
            setterName = stripIsFromMethodName(methodName);
        } else if (getterType.equalsIgnoreCase("has")) {
            setterName = stripHasFromMethodName(methodName);
        } else {
            return null;
        }
        return "set" + setterName;
    }

    public static String removeGetter(String methodName) {
        String setterName = null;

        String getterType = getGetterType(methodName);
        if (getterType.equalsIgnoreCase("get")) {
            setterName = stripGetFromMethodName(methodName);
        } else if (getterType.equalsIgnoreCase("is")) {
            setterName = stripIsFromMethodName(methodName);
        } else if (getterType.equalsIgnoreCase("has")) {
            setterName = stripHasFromMethodName(methodName);
        } else {
            return null;
        }
        return setterName;
    }
}
