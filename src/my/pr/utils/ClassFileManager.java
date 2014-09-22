/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

import java.io.IOException;
import java.security.SecureClassLoader;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

/**
 *
 * @author prando
 */
public class ClassFileManager extends ForwardingJavaFileManager {

    /**
     * Instance of JavaClassObject that will store the compiled bytecode of our
     * class
     */
    private JavaClassObject jclassObject;

    /**
     * Will initialize the manager with the specified standard java file manager
     *
     * @param standardManger
     */
    public ClassFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
    }

    /**
     * Will be used by us to get the class loader for our compiled class. It
     * creates an anonymous class extending the SecureClassLoader which uses the
     * byte code created by the compiler and stored in the JavaClassObject, and
     * returns the Class for it
     */
    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                //System.out.println("Finding Class: " + name);

                try {
                    Class sysClass = Class.forName(name);
                    return sysClass;
                }
                catch(ClassNotFoundException e) {
                
                }


                byte[] b = jclassObject.getBytes();
                //System.out.println(super.defineClass(null, b, 0, b.length));
                Class cls = super.defineClass(name, b, 0, b.length);
                
                byte[] cB = ObjectUtils.toByteArray(cls);
                ObjectUtils.toObject(cB);
                //super.resolveClass(cls);
                //Class.forName(name, true, this);
                return cls;
            }
        };
    }
    
    public byte[] getBytes() {
        return jclassObject.getBytes();
    }

    /**
     * Gives the compiler an instance of the JavaClassObject so that the
     * compiler can write the byte code into it.
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
        jclassObject = new JavaClassObject(className, kind);
        return jclassObject;
    }
}
