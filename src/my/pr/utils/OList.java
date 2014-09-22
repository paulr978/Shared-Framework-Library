/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author prando
 */
public class OList {

    public static List getOrderedList(List list, Comparator c) {
        Collections.sort(list, c);
        return list;
    }

    public static LinkedList getOrderedList(Set set, Comparator c) {
        LinkedList list = new LinkedList();
        Iterator i =  set.iterator();
        while (i.hasNext()) {
            list.add(i.next());
        }
        Collections.sort(list, c);
        return list;
    }
}
