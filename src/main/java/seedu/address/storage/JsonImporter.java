package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Model;
import seedu.address.storage.exceptions.ImporterException;

/**
 * A class that does all the importing of data from a group of .json files to AddressBook.json
 */
public class JsonImporter {
    private final List<File> jsonFiles = new ArrayList<>();

    /**
     * Creates a new JsonImporter
     * @param jsonFiles A list containing the .json files to be imported.
     */
    public JsonImporter(List<File> jsonFiles) {
        this.jsonFiles.addAll(jsonFiles);
    }

    private Model importJsonFile(File jsonFile, Model model) throws ImporterException {
        requireNonNull(jsonFile);

        Path filePath = jsonFile.toPath().toAbsolutePath();

        Optional<JsonSerializableAddressBook> jsonAddressBook;
        try {
            jsonAddressBook = JsonUtil.readJsonFile(
                    filePath, JsonSerializableAddressBook.class);
        } catch (DataLoadingException e) {
            throw new ImporterException(e);
        }
        if (!jsonAddressBook.isPresent()) {
            throw new ImporterException("jsonAddressBook is Null");
        }

        try {
            model.addAllPersons(jsonAddressBook.get().toModelType(true));
            return model;
        } catch (IllegalValueException ive) {
            throw new ImporterException(ive);
        }
    }

    /**
     * Imports all .json files converted by the CsvToJsonConverter
     * @param model model which the .json files should be imported to
     * @return The model after the .json files have been imported
     * @throws ImporterException if there was any error during the execution of the importer
     */
    public final Model importAllJsonFiles(Model model) throws ImporterException {
        for (File file: jsonFiles) {
            importJsonFile(file, model);
        }
        return model;
    }

    /**
     * Inits the necessary folders for the import command to work
     * @throws IllegalStateException if the "Import" file exists but is not a directory
     */
    public static final void initImporterSystem() throws IllegalStateException {

        File importer = new File("Import");

        if (!importer.exists()) {
            importer.mkdir();
        } else if (!importer.isDirectory()) {
            throw new IllegalStateException("'Import' exists but is not a directory.");
        }
    }

}
