/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

import java.util.*;
import java.lang.reflect.*;

import org.w3c.dom.Element;

public class OClass {

    private String className = null;
    private Class classObj = null;

    public OClass(String className) throws ClassNotFoundException {
        this.className = className;
        this.classObj = Class.forName(className);
    }

    public OClass(Class cls) {
        this.classObj = cls;
        this.className = classObj.getCanonicalName();
    }

    public OClass(Object ofClass) throws ClassNotFoundException {
        this.className = ofClass.getClass().getCanonicalName();
        classObj = Class.forName(className);
    }

    public Object getExistingInstance() {
        try {
            //classObj = Class.forName(className);

            Method m = classObj.getDeclaredMethod("getInstance");
            return m.invoke(classObj, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object getNewInstance() {

        try {
            //classObj = Class.forName(className);

            Class[] constParams = new Class[0];
            Constructor constructor = classObj.getConstructor(null);
            Object constructedObj = constructor.newInstance(null);

            return constructedObj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Object getNewInstance(Map args) {

        try {
            //classObj = Class.forName(className);

            Class[] constParams = new Class[1];
            constParams[0] = Map.class;

            Constructor constructor = classObj.getConstructor(constParams);
            Object constructedObj = constructor.newInstance(args);

            return constructedObj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Object getNewInstance(Object... args) {

        try {
            //classObj = Class.forName(className);

            Class[] constParams = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                constParams[i] = args[i].getClass();
            }

            Constructor constructor = classObj.getConstructor(constParams);
            Object constructedObj = constructor.newInstance(args);

            return constructedObj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Object getNewInstance(int arg) {

        try {
            //classObj = Class.forName(className);

            Class[] constParams = new Class[1];
            constParams[0] = int.class;

            Constructor constructor = classObj.getConstructor(constParams);
            Object constructedObj = constructor.newInstance(arg);

            return constructedObj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
