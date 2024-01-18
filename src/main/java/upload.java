
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class upload {
    public void main(String fileContent) {
        Config c = new Config();
        List<Site> s = c.getSites();

        String uploadUrl = s.get(0).getName();

        try {
            uploadFile(uploadUrl, fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void uploadFile(String uploadUrl, String fileContent) throws IOException {
        URL url = new URL(uploadUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");

        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            byte[] fileBytes = java.nio.file.Files.readAllBytes(new File(fileContent).toPath());
            outputStream.write(fileBytes);
        }

        int responseCode = httpURLConnection.getResponseCode();
        // Handle the response code accordingly
        System.out.println("Response Code: " + responseCode);
    }
}
