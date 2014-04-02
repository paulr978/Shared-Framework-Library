/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.utils;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author prando
 */
public class ByteClassLoader extends URLClassLoader {

    private final Map<String, byte[]> extraClassDefs;

    public ByteClassLoader(URL[] urls, ClassLoader parent, Map<String, byte[]> extraClassDefs) {
        super(urls, parent);
        this.extraClassDefs = new HashMap<String, byte[]>(extraClassDefs);
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {

        try {
            Class sysClass = Class.forName(name);
            return sysClass;
        } catch (ClassNotFoundException e) {
        }

        byte[] classBytes = this.extraClassDefs.remove(name);
        if (classBytes != null) {
            return defineClass(name, classBytes, 0, classBytes.length);
        }
        return super.findClass(name);
    }
}
