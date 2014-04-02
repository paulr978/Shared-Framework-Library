/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 *
 * @author prando
 */
public class OCompiler {

    public static String getClassNameFromCode(String code) {
        try {
            String compactCode = code.replace(" ", "");
            if (compactCode.contains("extends")) {
                compactCode = compactCode.substring(compactCode.indexOf("publicclass") + 11, compactCode.indexOf("extends"));
            } else {
                compactCode = compactCode.substring(compactCode.indexOf("publicclass") + 11, compactCode.indexOf("{"));
            }

            return compactCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSuperClassFromCode(String code) {
        try {
            String compactCode = code.replace(" ", "");
            compactCode = compactCode.substring(compactCode.indexOf("extends") + 7, compactCode.length());
            compactCode = compactCode.substring(0, compactCode.indexOf("{"));
            return compactCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getInterfacesFromCode(String code) {
        try {
            String[] interfaces = null;
            String compactCode = code.replace(" ", "");
            compactCode = compactCode.substring(compactCode.indexOf("implements") + 10, compactCode.length());
            compactCode = compactCode.substring(0, compactCode.indexOf("{"));
            if (compactCode.contains(",")) {
            } else {
                interfaces = new String[1];
                interfaces[0] = compactCode;
            }

            return interfaces;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized void compileFile(String fileToCompile) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compilationResult = compiler.run(null, null, null, fileToCompile);
        if (compilationResult == 0) {
            System.out.println("Compilation is successful");
        } else {
            System.out.println("Compilation Failed");
        }

    }

    public static byte[] compileClass(String className, String code) throws Exception {
        // We get an instance of JavaCompiler. Then
        // we create a file manager
        // (our custom implementation of it)
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        ClassFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(diagnostics, null, null));

        // Dynamic compiling requires specifying
        // a list of "files" to compile. In our case
        // this is a list containing one "file" which is in our case
        // our own implementation (see details below)
        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new CharSequenceJavaFileObject(className, code));


        List<String> options = new ArrayList<String>();
        options.add("-classpath");
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("java.class.path")).append(File.pathSeparator);
        sb.append(System.getProperty("java.ext.dirs")).append(File.pathSeparator);
        sb.append(System.getProperty("java.library.path")).append(File.pathSeparator);
        URLClassLoader urlClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
        for (URL url : urlClassLoader.getURLs()) {
            sb.append(url.getFile()).append(File.pathSeparator);
        }



        options.add(sb.toString());



        System.out.println("Classpath: " + options.toString());

        // We specify a task to the compiler. Compiler should use our file
        // manager and our list of "files".
        // Then we run the compilation with call()
        CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, jfiles);

        String errorMsg = null;
        boolean success = task.call();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            System.out.println(diagnostic.getCode());
            System.out.println(diagnostic.getKind());
            System.out.println(diagnostic.getPosition());
            System.out.println(diagnostic.getStartPosition());
            System.out.println(diagnostic.getEndPosition());
            System.out.println(diagnostic.getSource());
            System.out.println(diagnostic.getMessage(null));
            if (!success) {
                errorMsg = diagnostic.getMessage(null);
                System.out.println("Error Occured: " + errorMsg);
            }
        }
        System.out.println("Success: " + success);


        if (success) {
            //Object instance = Class.forName(className).newInstance();
            //Object instance = fileManager.getClassLoader(null).loadClass(className).newInstance();



            Class instance = fileManager.getClassLoader(null).loadClass(className);


            //byte[] instanceBytes = ObjectUtils.toByteArray(instance);
            //Object newInstance 


            System.out.println(instance);
            return fileManager.getBytes();
            //return instance;
        } else {
            throw new Exception(errorMsg);
        }



        /*
         if (success) {
         try {
         URL[] urls = new URL[1];
         urls[0] = new URL(("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension));
         URLClassLoader loader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());

         return loader.loadClass(className);
         //return loader.loadClass(className).newInstance();
                
         } catch (ClassNotFoundException e) {
         e.printStackTrace();
         throw new Exception(errorMsg);
         //} catch (InstantiationException e) {
         //    e.printStackTrace();
         //} catch (IllegalAccessException e) {
         //    e.printStackTrace();
         }
         } else {
         throw new Exception(errorMsg);
         }
         */


    }

}
