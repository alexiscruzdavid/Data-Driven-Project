package ooga.data;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A tool to read SIM files and convert their data into a metadata Map.
 */
public class SIMFileReader {

    private static final String INVALID_FILE_MESSAGE = "Invalid properties file passed.";

    /**
     * Reads in a Sim file and converts the values into a Map of keys and values.
     *
     * @param file the name/path of the SIM file.
     * @return a Map containing the metadata held in the SIM file.
     */
    public static Map<String, String> getMetadataFromFile(File file) throws FileNotFoundException {
        HashMap<String, String> metadata = new HashMap<>();
        Properties initialProperties;
        try {
            initialProperties = readPropertiesFile(file);
        } catch (Exception e) {
            throw new FileNotFoundException(INVALID_FILE_MESSAGE);
        }

        for (final String name : initialProperties.stringPropertyNames()) {
            metadata.put(name, initialProperties.getProperty(name));
        }
        return metadata;
    }

    /**
     * Reads a SIM file and returns a Properties file with its values.
     *
     * @param file the path of the SIM file being used.
     * @return a Properties class containing the data held in the SIM file.
     */
    private static Properties readPropertiesFile(File file) throws IOException {
        FileInputStream fis;
        fis = new FileInputStream(file);
        Properties myProperties = new Properties();
        myProperties.load(fis);
        fis.close();
        return myProperties;
    }

}
