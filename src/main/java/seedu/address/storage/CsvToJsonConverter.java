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

    private final File directory;
    private final String[] personFieldNames;

    /**
     * creates a new CsvToJsonConverter object with the provided {@Code String directorypath}. Checks if the given
     * path is a valid directory. Will convert all .csv files inside the directory to .json files.
     * @param directoryPath the string representation of the path to the directory which stores the .csv files
     */
    public CsvToJsonConverter(String directoryPath) {
        personFieldNames = Person.getFieldSimpleNames();
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

    private boolean convertCsvFile(File csvFile) throws ConverterException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader br = createNewBufferedReader(csvFile);
        ArrayNode jsonArray = objectMapper.createArrayNode();
        try {
            String[] headers = getHeaders(br);
            addAllJSonObjects(br, objectMapper, headers, jsonArray);
            br.close();
        } catch (IOException ioe) {
            throw new ConverterException("There has been a corrupted input", ioe);
        }
        writeToJson(objectMapper, jsonArray, getJsonName(csvFile));
        return true;
    }

    private void addAllJSonObjects(BufferedReader br, ObjectMapper objectMapper,
                                   String[] headers, ArrayNode jsonArray) throws IOException {
        String line = br.readLine();
        while (line != null) {
            ObjectNode jsonObject = addNextJsonObject(line, objectMapper, headers);
            line = br.readLine();
            jsonArray.add(jsonObject);
        }
    }

    private boolean writeToJson(ObjectMapper content, ArrayNode arrayNode,
                                String jsonFilePath) throws ConverterException {
        try {
            content.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFilePath), arrayNode);
            return true;
        } catch (IOException e) {
            throw new ConverterException("Could not write to Json File");
        }
    }

    private BufferedReader createNewBufferedReader(File csvFile) throws ConverterException{
        try {
            FileReader fr = new FileReader(csvFile);
            return new BufferedReader(fr);
        } catch (FileNotFoundException fnfe) {
            throw new ConverterException("File not found", fnfe);
        }
    }
    
    private String[] getHeaders(BufferedReader br) throws IOException{
        String line = br.readLine();

        if (line == null) {
            throw new IOException(".csv file is empty");
        }

        return line.split(",");
    }

    private ObjectNode addNextJsonObject(String line, ObjectMapper objectMapper, String[] headers) {
        String[] values = line.split(",");

        ObjectNode jsonObject = objectMapper.createObjectNode();

        for (int i = 0; i < headers.length; i++) {
            if (!jsonObject.get(headers[i]).isNull()) {
                continue;
            } else if (!Arrays.asList(this.personFieldNames).contains(headers[i])) {
                continue;
            }

            if (values[i].isBlank()) {
                values[i] = null;
            }

            jsonObject.put(headers[i], values[i]);
        }
        return jsonObject;
    }

    private String getJsonName(File csvFile) {
        assert csvFile != null;
        String csvName = csvFile.getName();

        assert csvName.endsWith(".csv");

        if (!csvName.isBlank() && csvName.endsWith(".csv")) {
            return csvName.replace(".csv", ".json");
        } else {
            throw new IllegalArgumentException("Filename must end with .csv");
        }
    }
}
