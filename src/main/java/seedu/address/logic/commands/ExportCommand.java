package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

/**
 * Command that exports current list of persons to a csv file
 */
public class ExportCommand extends Command {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Data saved. \nData exported to /data/exported_data.csv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the data to a spreadsheet.\n"
            + "Example: " + COMMAND_WORD;

    private static final Path projectRootPath = Paths.get(System.getProperty("user.dir"));

    private Storage storage;

    /**
     * Public constructor for ExportCommand
     */
    public ExportCommand() {
        JsonAddressBookStorage jsonStorage =
            new JsonAddressBookStorage(projectRootPath.resolve("addressbook.json"));
        JsonUserPrefsStorage userPrefStorage =
            new JsonUserPrefsStorage(projectRootPath.resolve("config.json"));
        storage = new StorageManager(jsonStorage, userPrefStorage);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        Path importPath = projectRootPath.resolve("data").resolve("addressbook.json");
        Path exportPath = projectRootPath.resolve("exported_data.csv");
        requireNonNull(model);
        saveJsonFile(model, importPath, exportPath);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Command that exports current list of persons to a csv file
     */
    public CommandResult execute(Model model, Path importPath, Path exportPath) throws CommandException {
        requireNonNull(model);
        saveJsonFile(model, importPath, exportPath);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Saves the current data to the json file without exiting
     * @param model
     * @throws CommandException
     */
    private void saveJsonFile(Model model, Path importPath, Path exportPath) throws CommandException {
        try {
            storage.saveAddressBook(model.getAddressBook());
            translateJsonToCsv(importPath, exportPath);
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }
    }

    /**
     * Translate the Jsonfile into a csv file
     * @param jsonFilePath
     */
    private void translateJsonToCsv(Path jsonFilePath, Path exportPath) throws AccessDeniedException {
        try {
            // Read the JSON file
            String jsonContent = Files.readString(jsonFilePath);
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray jsonArray = jsonObject.getJSONArray("persons");

            FileWriter csvWriter = new FileWriter(exportPath.toFile());

            // Write the CSV header
            csvWriter.append("Name,Class,Phone number,Tags\n");

            // Iterate through the JSON array and write each object as a CSV row
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject person = jsonArray.getJSONObject(i);
                String name = person.getString("name");
                String studentClass = person.getString("studentClass");
                String phone = person.getString("phone");
                String tags = getPersonTags(person.getJSONArray("tags"));
                csvWriter.append(name).append(",").append(studentClass)
                            .append(",").append(phone).append(",").append(tags).append("\n");
            }
            // Close the CSV writer
            csvWriter.flush();
            csvWriter.close();

        } catch (AccessDeniedException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the json array of tags into a parsable string
     * @param tags
     * @return String
     */
    private String getPersonTags(JSONArray tags) {
        StringBuilder tagsString = new StringBuilder();
        for (int i = 0; i < tags.length(); i++) {
            tagsString.append(tags.getString(i));
            if (i != tags.length() - 1) {
                tagsString.append(" ");
            }
        }
        return tagsString.toString();
    }
}
