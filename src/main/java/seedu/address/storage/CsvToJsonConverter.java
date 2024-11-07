package seedu.address.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import seedu.address.commons.util.StringUtil;
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
     * @param directory the directory which stores the .csv files ot be converted
     */
    public CsvToJsonConverter(File directory) {
        personFieldNames = Person.getFieldSimpleNames();
        for (int i = 0; i < personFieldNames.length; i++) {
            personFieldNames[i] = StringUtil.toCamelCase(personFieldNames[i]);
        }
        this.directory = directory;

        if (!directory.exists() || !this.directory.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a directory");
        }
    }

    /**
     * Converts all the .csv files in a given directory to .json files
     * @return A List containing all the successfully converted .json files
     * @throws ConverterException When the provided path contains no .csv files to convert
     */
    public List<File> convertAllCsvFiles() throws ConverterException {
        List<File> jsonFiles = new ArrayList<>();
        if (directory.getName().endsWith(".csv")) {
            jsonFiles.add(convertCsvFile(directory));
        } else if (directory.isDirectory()) {
            try {
                File[] files = findAllCsvFiles();
                for (File csvFile: files) {
                    jsonFiles.add(convertCsvFile(csvFile));
                }
            } catch (IOException ioe) {
                throw new ConverterException(ioe.getMessage());
            }
        }
        return jsonFiles;
    }

    private File[] findAllCsvFiles() throws IOException {
        File[] csvFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            throw new IOException("The provided path contains no .csv files to import");
        }
        return csvFiles;
    }

    private File convertCsvFile(File csvFile) throws ConverterException {
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
        return writeToJson(objectMapper, jsonArray, getJsonName(csvFile));
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

    private File writeToJson(ObjectMapper content, ArrayNode arrayNode,
                                String jsonFilePath) throws ConverterException {
        try {
            ObjectNode rootObject = content.createObjectNode();
            rootObject.set("persons", arrayNode);
            File toReturn = new File(jsonFilePath);
            content.writerWithDefaultPrettyPrinter().writeValue(toReturn, rootObject);
            return toReturn;
        } catch (IOException e) {
            throw new ConverterException("Could not write to Json File");
        }
    }

    private BufferedReader createNewBufferedReader(File csvFile) throws ConverterException {
        try {
            FileReader fr = new FileReader(csvFile);
            return new BufferedReader(fr);
        } catch (FileNotFoundException fnfe) {
            throw new ConverterException("File not found", fnfe);
        }
    }
    private String[] getHeaders(BufferedReader br) throws IOException {
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
            if (jsonObject.has(headers[i]) && !jsonObject.get(headers[i]).isNull()) {
                continue;
            } else if (!Arrays.asList(this.personFieldNames).contains(headers[i])) {
                continue;
            }

            if (values.length != headers.length) {
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
            return new File(csvFile.getParent(), csvName.replace(".csv", ".json")).getPath();
        } else {
            throw new IllegalArgumentException("Filename must end with .csv");
        }
    }
}
