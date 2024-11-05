package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.exceptions.ImporterException;

/**
 * A class that does all the importing of data from a group of .json files to AddressBook.json
 */
public class JsonImporter {
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<File> jsonFiles = new ArrayList<>();

    /**
     * Creates a new JsonImporter
     * @param jsonFiles A list containing the .json files to be imported.
     */
    public JsonImporter(List<File> jsonFiles) {
        this.jsonFiles.addAll(jsonFiles);
    }

    private final Model importJsonFile(File jsonFile, Model model)  throws ImporterException {
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
            model.addAllPersons(jsonAddressBook.get().toModelType());
            return model;
        } catch (IllegalValueException ive) {
            throw new ImporterException(ive);
        }
    }

    public final Model importAllJsonFiles(Model model) throws ImporterException {
        for (File file: jsonFiles) {
            importJsonFile(file, model);
        }
        return model;
    }

    public static final boolean initImporterSystem() throws IllegalStateException{

        File importer = new File("Import");

        if (!importer.exists()) {
            importer.mkdir();
        } else if (!importer.isDirectory()) {
            throw new IllegalStateException("'Import' exists but is not a directory.");
        }
        return true;
    }

}
