import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Config c = Config.loadConfig();
        if (c != null)
            c.dumpConfig();
            for (Site s : c.getSites()){
                System.out.println(s.getName());
            }

        List<Site> sites = c.getSites();
        String url = sites.get(0).getName();
        String destination = c.getStorageServer();
        String email = c.getEmailAdmin();

        System.out.println(url + destination);
        System.exit(0);

        try {
            // Connect to the website and fetch the HTML content
            Document doc = Jsoup.connect(url).get();
            String htmlContent = doc.html();

            // Create a backup folder if it doesn't exist
            File backupFolder = new File(destination);
            if (!backupFolder.exists()) {
                backupFolder.mkdir();
            }

            // Create a timestamp for the backup file
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy HH.mm");
            String timestamp = dateFormat.format(new Date());

            String filename = "backup_"+timestamp;
            // Save the fetched HTML content to a file in the destination folder
            File backupFile = new File(backupFolder, filename+ ".html");
            //FileUtils.writeStringToFile(backupFile, htmlContent, "UTF-8");
            zip z = new zip();
            z.main(backupFile,htmlContent);
            backupFile.delete();
            // Write backup details to the log file
            File logFile = new File(backupFolder, "backup_log.txt");
            String logEntry = timestamp + "\t" + filename + "\n";
            FileUtils.writeStringToFile(logFile, logEntry, "UTF-8", true);


            System.out.println("Website backed up successfully to: " + backupFile.getAbsolutePath());
            System.out.println("Backup log updated at: " + logFile.getAbsolutePath());

            // send an email to the user with backup details
            email e = new email();
            e.sendEmail("email",backupFile.getAbsolutePath(),logFile.getAbsolutePath());

            // Display the list of previous backups
            viewBackupLog(destination);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewBackupLog(String destination) {
        File backupFolder = new File(destination);
        File logFile = new File(backupFolder, "backup_log.txt");

        System.out.println("Previous Backups:");
        try {
            if (logFile.exists()) {
                // Read and display the contents of the backup log file
                String logContent = FileUtils.readFileToString(logFile, "UTF-8");
                System.out.println(logContent);
            } else {
                System.out.println("No previous backups found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading backup log: " + e.getMessage());
        }
    }

}





/*

 TO EXECUTE IN CLI
javac WebsiteBackupCLI.java
java WebsiteBackupCLI <website_url> <destination_folder>
*/