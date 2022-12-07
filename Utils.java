import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.TreeMap;

public class Utils {
    
    public static int byteArrayToInt(byte[] byteArray) {
        return ByteBuffer.wrap(byteArray).getInt();
    }

    public static byte[] intToByteArray(int intToConvert) {
        
        return ByteBuffer.allocate(4).putInt(intToConvert).array();
    }

    public static void addIntToByteArray(byte[] byteArray, int intToConvert, int startIndex) {
        // convert message length to byte array of length 4
        byte[] lengthArray = Utils.intToByteArray(intToConvert);
        // add message length into first 4 bytes of message array
        for(int i = 0; i < 4; i++) {
            byteArray[startIndex + i] = lengthArray[i];
        }
    }

    public static String getStringFromBytes(byte[] byteArray, int first, int last) {
        int size = last - first + 1;
        //tbd - exception
        if (size <= 0 || last >= byteArray.length)
        {return "";}

        //Part of byte array need to be returned as a string
        byte[] outputString = new byte[size];
        System.arraycopy(byteArray, first, outputString, 0, size);
        return new String(outputString, StandardCharsets.UTF_8);

    }

    
    public static ArrayList<String> getFileLines(String fileName) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader bReader = new BufferedReader(new FileReader(fileName));
        String line = bReader.readLine();
        while (line != null) {
            lines.add(line);
            line = bReader.readLine();
        }
        bReader.close();
        return lines;
    }

}
