package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * A class that does all the importing of data from a group of .json files to AddressBook.json
 */
public class JsonImporter {
    private final Path AddressBookFilePath;
    private final File directoryToImport;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Creates a new instance of JsonImporter which will handle all the importing from the provided directory path
     * @param directoryPath path to the directory containing all the .json files to be imported
     */
    public JsonImporter(String directoryPath, JsonAddressBookStorage storage) {
        this.directoryToImport = new File(directoryPath);
        this.AddressBookFilePath = storage.getAddressBookFilePath();
        if (!directoryToImport.exists() || !this.directoryToImport.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a directory");
        }
    }

    public static void appendJsonFiles(File targetFile, File sourceDirectory) throws IOException {
        // Step 1: Directly load the existing AddressBook.json data as an ArrayNode
        ArrayNode targetArray = loadJsonArrayFromFile(targetFile);

        // Step 2: Get all JSON files in the source directory
        File[] jsonFiles = sourceDirectory.listFiles((dir, name) -> name.endsWith(".json"));
        if (jsonFiles == null) {
            System.out.println("No JSON files found in the specified directory.");
            return;
        }

        // Step 3: Append each JSON file's content to the target array
        for (File jsonFile : jsonFiles) {
            if (!jsonFile.equals(targetFile)) {  // Avoid appending the target file itself
                ArrayNode sourceArray = loadJsonFileAsArray(jsonFile);
                targetArray.addAll(sourceArray);  // Append data from source array
            }
        }

        // Step 4: Write the combined array back to the target file
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(targetFile, targetArray);
        System.out.println("Appended JSON files to " + targetFile.getName());
    }

    // Helper method to load an existing JSON array from a file
    private static ArrayNode loadJsonArrayFromFile(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("The file " + file.getName() + " does not exist.");
        }

        JsonNode jsonNode = objectMapper.readTree(file);
        if (jsonNode.isArray()) {
            return (ArrayNode) jsonNode;
        } else {
            throw new IOException("Expected JSON array in " + file.getName());
        }
    }

    // Helper method to load a JSON file as an ArrayNode; wraps objects in an array if necessary
    private static ArrayNode loadJsonFileAsArray(File file) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(file);
        if (jsonNode.isArray()) {
            return (ArrayNode) jsonNode;
        } else if (jsonNode.isObject()) {
            ArrayNode arrayNode = objectMapper.createArrayNode();
            arrayNode.add(jsonNode);
            return arrayNode;
        } else {
            throw new IOException("Invalid JSON data in " + file.getName());
        }
    }
}
