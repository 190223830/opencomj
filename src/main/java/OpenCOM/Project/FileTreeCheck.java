package OpenCOM.Project;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class FileTreeCheck {
    private static boolean check = false;
    private static List<String> starterHashes, starterFiles;


    public static void start(List<String> hashes, Boolean switchOn) throws NoSuchAlgorithmException, IOException {
        check = switchOn;
        System.out.println("Starting repeated file tree integrity checks...");
        starterHashes = hashes;

        starterFiles = GenerateChecksum.getFilePaths();

        while(check) {
            List<String> newHashes = GenerateChecksum.checksumForAllFiles();
            if (newHashes != starterHashes) {
                System.out.println("WARNING: UNKNOWN COMPONENTS FOUND IN FILE TREE");
                List<String> newFiles = GenerateChecksum.getFilePaths();
                for(int i=0; i<starterFiles.size(); i++) {
                    if (starterFiles.get(i).equalsIgnoreCase(newFiles.get(i)) == false) {
                        System.out.println("CHANGE IN COMPONENT: " + starterFiles.get(i));
                    }
                }

            }
            Wait(10);
        }
    }

    public static void Wait(long seconds) {
        long time0 = System.currentTimeMillis();
        long time1 = -1;
        while(time1 < (seconds*1000)){
            time1 = System.currentTimeMillis()-time0;
        }
    }

    public void stop() {
        check = false;
    }
}
