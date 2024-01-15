import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class unzip {
    public static void main(String[] args) {
        String zipFilePath = "path/to/your/zip/file.zip";
        String destDirectory = "path/to/your/destination/directory";

        try {
            unzip(zipFilePath, destDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unzip(String zipFilePath, String destDirectory) throws IOException {
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                // Create directories if needed
                if (zipEntry.isDirectory()) {
                    new java.io.File(destDirectory + java.io.File.separator + fileName).mkdirs();
                } else {
                    // Extract file
                    try (FileOutputStream fos = new FileOutputStream(destDirectory + java.io.File.separator + fileName)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
        }
    }
}
