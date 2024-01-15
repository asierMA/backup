
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class upload {
    public static void main(String[] args) {
        String uploadUrl = "https://example.com/upload";
        String filePath = "path/to/your/extracted/file.html";

        try {
            uploadFile(uploadUrl, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void uploadFile(String uploadUrl, String filePath) throws IOException {
        URL url = new URL(uploadUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");

        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            byte[] fileBytes = java.nio.file.Files.readAllBytes(new File(filePath).toPath());
            outputStream.write(fileBytes);
        }

        int responseCode = httpURLConnection.getResponseCode();
        // Handle the response code accordingly
        System.out.println("Response Code: " + responseCode);
    }
}
