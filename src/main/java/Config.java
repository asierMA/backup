import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@XmlRootElement(name = "backups")
@XmlType()
public class Config {

    private static final String pathToConfig = "/etc/backups/";
    private static final String nameOfConfig = "backups_config.xml";

    @XmlElement(name = "emailAdmin")
    String emailAdmin;

    @XmlElement(name = "storageServer")
    String storageServer;
    @XmlElement(name = "site")
    List<Site> sites;

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public String getStorageServer() {
        return storageServer;
    }

    public List<Site> getSites() {
        return sites;
    }

    public static Config loadConfig() {
        try {
            File f = new File(pathToConfig + nameOfConfig);
            if (!f.exists())
                f = new File(nameOfConfig);

            JAXBContext context = JAXBContext.newInstance(Config.class);
            return (Config) context.createUnmarshaller()
                    .unmarshal(new FileReader(f));
        } catch (JAXBException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void dumpConfig() {
        System.out.println("emailAdmin=" + emailAdmin);
        System.out.println("storageServer=" + storageServer);
        for (Site s: sites)
        {
            System.out.println("Site " + s.name);
            System.out.println("  autoFullFrequency=" +s.autoFullFrequency);
            System.out.println("  autoIncrementalFrequency=" + s.autoIncrementalFrequency);
            System.out.println("  autoFirstEverBackup=" + s.autoFirstEverBackup);
            System.out.println("  autoHourOfDay=" + s.autoHourOfDay);
            System.out.println("  autoMinuteOfHour=" + s.autoMinuteOfHour);
            System.out.println("  autoKeepNoFullBackups=" + s.autoKeepNoFullBackups);
            System.out.println("  include:");
            for (String loc: s.includeLocations)
                System.out.println("    " + loc);
            System.out.println("  exclude:");
            for (String loc: s.excludeLocations)
                System.out.println("    " + loc);
        }
    }
}

class Site
{
    @XmlElement(name = "name")
    String name;

    @XmlElement(name = "includeLocation")
    List<String> includeLocations;

    @XmlElement(name = "excludeLocation")
    List<String> excludeLocations;

    @XmlElement(name = "autoFullFrequency")
    int autoFullFrequency;
    @XmlElement(name = "autoIncrementalFrequency")
    int autoIncrementalFrequency;
    @XmlElement(name = "autoFirstEverBackup")
    javax.xml.datatype.XMLGregorianCalendar autoFirstEverBackup;
    @XmlElement(name = "autoHourOfDay")
    int autoHourOfDay;
    @XmlElement(name = "autoMinuteOfHour")
    int autoMinuteOfHour;
    @XmlElement(name = "autoKeepNoFullBackups")
    int autoKeepNoFullBackups;

    public String getName() {
        return name;
    }

    public List<String> getIncludeLocations() {
        return includeLocations;
    }

    public List<String> getExcludeLocations() {
        return excludeLocations;
    }

    public int getAutoFullFrequency() {
        return autoFullFrequency;
    }

    public int getAutoIncrementalFrequency() {
        return autoIncrementalFrequency;
    }

    public XMLGregorianCalendar getAutoFirstEverBackup() {
        return autoFirstEverBackup;
    }

    public int getAutoHourOfDay() {
        return autoHourOfDay;
    }

    public int getAutoMinuteOfHour() {
        return autoMinuteOfHour;
    }

    public int getAutoKeepNoFullBackups() {
        return autoKeepNoFullBackups;
    }
}
