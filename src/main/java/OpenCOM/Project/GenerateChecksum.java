package OpenCOM.Project;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.security.*;
import java.util.stream.Collectors;

public class GenerateChecksum {

    private static List<String> hashes = new ArrayList<String>();
    private static List<String> filePaths = new ArrayList<String>();

    public static List<String> checksumForAllFiles() throws NoSuchAlgorithmException, IOException {
        if (hashes.isEmpty()) {
            System.out.println("Generating checksums for current file tree...");
        }
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        filePaths = Files.walk(Paths.get(".\\src\\main\\java\\OpenCOM\\Project\\")).filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());


        for (int fileNo = 0; fileNo < filePaths.size(); fileNo++) {
            String hexValue = checksum(filePaths.get(fileNo), messageDigest);
            hashes.add(hexValue);
        }
        return hashes;
    }

    public static String checksum(String filePath, MessageDigest messageDigest) {
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(new File(filePath)), messageDigest)) {
            while (dis.read() != -1) ;
            messageDigest = dis.getMessageDigest();
        } catch (IOException exception) {
            System.out.println(exception);
        }

        StringBuilder output = new StringBuilder();
        for (byte b : messageDigest.digest()) {
            output.append(String.format("%02x", b));
        }
        return output.toString();
    }


    public static void printHashes() {
        System.out.println("Generated hashes: ");
        for (String hash : hashes) {
            System.out.println(hash);
        }
    }

    public static List<String> storeHashes() {
        return hashes;
    }
    public static List<String> getFilePaths() {
        return filePaths;
    }
}


/*        String[] capletNames, typeFolders, fileNames;
        capletNames = new String[]{"AlarmCaplet", "ControllerCaplet", "DisplayCaplet", "SensorCaplet"};
        for() {     //This needs to be recursive
            capletName = capletNames[i];
            typeFolder = typeFolders[j];
            fileName = fileNames[k];
            String hexValue = checksum("OpenCOM.Project." + capletName + "." + typeFolder + "." + fileName, messageDigest);
            String[] filePaths = new String[200];
            int i=0;
            while (true) {
                    List<String> path = Files.walk(Paths.get(".\\src\\main\\java\\OpenCOM\\Project\\")).filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
                    if (path != "") {
                        try {
                            filePaths[i] = path;
                            System.out.println(filePaths[i]);
                            i++;
                        } catch (ArrayIndexOutOfBoundsException a) {
                            break;
                        }
                    } else break;
            }

 */
