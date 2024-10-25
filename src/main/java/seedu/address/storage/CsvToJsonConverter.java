package seedu.address.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import seedu.address.model.person.Person;
import seedu.address.storage.exceptions.ConverterException;

/**
 * A class created to handle all conversion from .csv file to .json file for mass importing of contacts
 */
public class CsvToJsonConverter {

    private final String directoryPath;
    private final File directory;
    private final String[] personFieldNames;

    /**
     * creates a new CsvToJsonConverter object with the provided {@Code String directorypath}. Checks if the given
     * path is a valid directory. Will convert all .csv files inside the directory to .json files.
     * @param directoryPath the string representation of the path to the directory which stores the .csv files
     */
    public CsvToJsonConverter(String directoryPath) {
        personFieldNames = Person.getFieldSimpleNames();
        this.directoryPath = directoryPath;
        this.directory = new File(directoryPath);

        if (!directory.exists() || !this.directory.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a directory");
        }
    }

    private File[] findAllCsvFiles() throws IOException {
        File[] csvFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            throw new IOException("The provided path contains no .csv files to import");
        }
        return csvFiles;
    }

    private ArrayNode parseCsvFile() throws ConverterException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader br;
        try {
            FileReader fr = new FileReader(this.directoryPath);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException fnfe) {
            throw new ConverterException("File not found", fnfe);
        }
         try {
             String line = br.readLine();

             if (line == null) {
                 throw new IOException(".csv file is empty");
             }

             String[] headers = line.split(",");

             ArrayNode jsonArray = objectMapper.createArrayNode();

             line = br.readLine();
             while (line != null) {
                 String[] values = line.split(",");

                 ObjectNode jsonObject = objectMapper.createObjectNode();

                 for (int i = 0; i < headers.length; i++) {
                     if (!jsonObject.get(headers[i]).isNull()) {
                         continue;
                     } else if (!Arrays.asList(personFieldNames).contains(headers[i])) {
                         continue;
                     }

                     if (values[i].isBlank()) {
                         values[i] = null;
                     }

                     jsonObject.put(headers[i], values[i]);
                 }

                 jsonArray.add(jsonObject);
             }
             return jsonArray;
         } catch (IOException ioe) {
            throw new ConverterException("There has been a corrupted input", ioe);
         }
    }

    private static void containsValidHeaders(File csvFile) {

    }


}
