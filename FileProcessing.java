import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class FileProcessing{
    public static boolean check(String peerId) throws IOException {
        File file = new File("./" + peerId + "/thefile");
        return file.exists();
    }

    public static byte[] returnChunks(byte[] original, int low, int high) {
        byte[] result = new byte[high - low];
        System.arraycopy(original, low, result, 0, Math.min(original.length - low, high - low));
        return result;
    }

    public static HashMap<Integer, byte[]> getDataInChunks(int fileSize, int chunkSize, String fileName) throws Exception {
        //byte[][] fileChunks = new byte[(int) Math.ceil(fileSize / chunkSize)][chunkSize];
        //fileChunks = new byte[(int) Math.ceil(fileSize / chunkSize) + 1][];
        //System.out.println(fileChunks == null);
        HashMap<Integer, byte[]> fileData= new HashMap<Integer, byte[]>();
        BufferedInputStream file = new BufferedInputStream(new FileInputStream("./" + ParentThread.peerID + "/" + fileName));
        byte[] byteArray = new byte[fileSize];

        file.read(byteArray);
        file.close();
        int chunkIndex = 0, cnt = 0;

        while (chunkIndex < fileSize) {

            if (chunkIndex + chunkSize <= fileSize) {
                fileData.put(cnt, returnChunks(byteArray, chunkIndex, chunkIndex + chunkSize));
                cnt++;
            } else {
                fileData.put(cnt ,returnChunks(byteArray, chunkIndex, fileSize));
                cnt++;
            }
            chunkIndex += chunkSize;

        }

        return fileData;

    }


    public static void createDirectories(int peerID, String file) throws IOException {

        Path p = Paths.get("./" + String.valueOf(peerID));
        System.out.println(p.toString());
        if (Files.exists(p)) {
            clean(p, file);
        } else {
            Files.createDirectory(p);

        }
        System.out.println("Here");
        new File("./" + String.valueOf(peerID) + "/logs_" + String.valueOf(peerID) + ".log");
    }

    public static void clean(Path path, String file) throws IOException {

        Stream<Path> filesList = Files.list(path);

        for (Object o : filesList.toArray()) {

            Path current_file = (Path) o;
            if (!current_file.getFileName().toString().equals(file)) {
                Files.delete(current_file);
            }

        }
        filesList.close();


    }

    public static HashMap<Integer, byte[]> sortFileData(HashMap<Integer, byte[]> map) throws Exception
    {
        List<Map.Entry<Integer, byte[]> > list =
                new LinkedList<Map.Entry<Integer, byte[]> >(map.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, byte[]> >() {
            public int compare(Map.Entry<Integer, byte[]> o1,
                               Map.Entry<Integer, byte[]> o2)
            {
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, byte[]> temp = new LinkedHashMap<Integer, byte[]>();
        for (Map.Entry<Integer, byte[]> sorted : list) {
            temp.put(sorted.getKey(), sorted.getValue());
        }
        return temp;

    }



}

