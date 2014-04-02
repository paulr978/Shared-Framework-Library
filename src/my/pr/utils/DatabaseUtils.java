package my.pr.utils;

import java.io.*;

public class DatabaseUtils {
	
	public static byte[] serialize(Object obj) {
		
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(obj);
			return out.toByteArray();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static Object deserialize(byte[] data) {
		
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}
        
        public static Object deserialize(InputStream input) {
		
		try {   BufferedInputStream in = new BufferedInputStream(input);
			//ByteArrayInputStream in = new ByteArrayInputStream(input);
			ObjectInputStream is = new ObjectInputStream(in);
                    return is.readObject();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}

}
