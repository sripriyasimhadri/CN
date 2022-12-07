import java.io.IOException;
import java.util.ArrayList;

public class ConfigurationSetting {

    static int preferredNeighbours;
    static int intervalToUnCh;
    static int optiUnChoke;
    static String nameOfFile;
    static int sizeOfFile;
    static int sizeOfPiece;
    static int piecesTot;

    public static void read(String filePath) throws IOException {
        ArrayList<String> lines = Utils.getFileLines(filePath);
        preferredNeighbours = Integer.parseInt(lines.get(0).split(" ")[1]);
        intervalToUnCh = Integer.parseInt(lines.get(1).split(" ")[1]);
        optiUnChoke = Integer.parseInt(lines.get(2).split(" ")[1]);
        nameOfFile = lines.get(3).split(" ")[1];
        sizeOfFile = Integer.parseInt(lines.get(4).split(" ")[1]);
        sizeOfPiece = Integer.parseInt(lines.get(5).split(" ")[1]);
        piecesTot = (int) Math.ceil((double) sizeOfFile / sizeOfPiece);
    }


}


