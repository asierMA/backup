import java.io.*;

class Backup {

    public static void main(String args[])
    {
        try {
            System.out.println("I am going to compress files readme.txt and hello.txt to a zip.");
            Runtime r = Runtime.getRuntime();
            Process p = r.exec("zip -@ archive.zip");
            System.out.println("Zip has been started...");

            OutputStream s = p.getOutputStream();
            DataOutputStream d = new DataOutputStream(s);
            d.writeBytes("readme.txt");
            d.writeChar('\n');
            d.writeBytes("hello.txt");
            d.writeChar('\n');
            d.close();

            System.out.println("List of files has been sent...");

            p.waitFor();
            System.out.println("Zip has finished.");
        } catch (Exception e)
        {
            System.out.println("exception! " + e.getMessage());
            e.printStackTrace();
        }
    }
}