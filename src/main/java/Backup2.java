import java.io.*;

class Backup2 {

    public static void main(String args[])
    {
        Process p = null;
        try {
            System.out.println("I am going to run ls.");
            Runtime r = Runtime.getRuntime();
            p = r.exec("cmd /c dir C:\\Users\\josep\\desktop");
            System.out.println("ls has been started...");

            InputStream s = p.getInputStream();
            BufferedReader d
                    = new BufferedReader(new InputStreamReader(s));
            int i = 0;
            while (true)
            {
                String ln = d.readLine();
                if (ln == null) break;
                System.out.println((i++) + ": " + ln);
            }

            System.out.println("End of list of files");

            p.waitFor();
            System.out.println("ls has finished.");
        } catch (Exception e)
        {
            System.out.println("exception! " + e.getMessage());
            e.printStackTrace();
        }
    }
}
