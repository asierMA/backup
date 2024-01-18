import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class unzip {
    public static void main(String[] args) {
        String zipFilePath = "path/to/your/zip/file.zip";

        try {
            uz(zipFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void uz(String zipFilePath) throws IOException {
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();

                // Extract file content
                StringBuilder fileContent = new StringBuilder();
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fileContent.append(new String(buffer, 0, len));
                }

                // Send file content to upload class
                upload u = new upload();
                u.main(fileContent.toString());

                zipEntry = zis.getNextEntry();
            }



        }
    }
}
