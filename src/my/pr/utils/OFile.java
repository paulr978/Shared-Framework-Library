/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 *
 * @author Paul Rando
 */
public class OFile {

    private byte[] data = null;

    public OFile(byte[] data) {
        this.data = data;
    }

    public static boolean isZipFile(File file) throws IOException {
        if (file.isDirectory()) {
            return false;
        }
        if (!file.canRead()) {
            throw new IOException("Cannot read file " + file.getAbsolutePath());
        }
        if (file.length() < 4) {
            return false;
        }
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        int test = in.readInt();
        in.close();
        System.out.println("isZipFile: " + test + ", Expecting: " + 0x504b0304);
        return test == 0x504b0304;
    }

    public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();
    }

    public static final void writeLineToFile(File file, String text) throws Exception {
        if (!file.exists()) {
            file.createNewFile();
        }

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        out.write(text + "\n");
        out.close();
    }
    
    public String convertToString() throws UnsupportedEncodingException {
        return convertToString("UTF-8");
    }
    
    public String convertToString(String format) throws UnsupportedEncodingException {
        return new String(data, format);
    }
    
    public static ArrayList<File> getFilesRecursively(File directory, String extension) {
        ArrayList<File> filesArray = new ArrayList<File>();
        getFilesRecursively(filesArray, directory, extension);
        return filesArray;
    }
    
    public static void getFilesRecursively(ArrayList<File> filesArray, File directory, String extension) {
        if(directory.isDirectory()) {
            File[] files = directory.listFiles();
            for(File file : files) {
                if(file.isDirectory()) {
                    getFilesRecursively(filesArray, file, extension);
                }
                else {
                    if(file.getName().toLowerCase().endsWith(".jar")) {
                        filesArray.add(file);
                    }
                }
            }
        }

    }
    

    public File getFile(String name) throws FileNotFoundException, IOException {
        File file = new File(name);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.flush();
        fos.close();

        return file;
    }
}
