package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentClass;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

/**
 * Command that exports current list of persons to a csv file
 */
public class ImportCommand extends Command {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not import data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not import data to file %s due to insufficient permissions to read to the file or the folder.";
    public static final String MESSAGE_CSV_ERROR = "Error reading CSV file";
    public static final String MESSAGE_FILE_CORRUPTED = "Data in file is corrupted";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found";
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_SUCCESS = "Data imported.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Imports the data from a csv file.\n"
        + "Parameters: import file path\n"
        + "Example: " + COMMAND_WORD
        + " /Users/username/Desktop/addressbook.csv";


    private static final Path projectRootPath = Paths.get(System.getProperty("user.dir"));

    private Path importCsvFilePath;
    private Storage storage;
    private List<String> duplicatePersonsNames = new ArrayList<>();
    private boolean haveDuplicatePersons = false;

    /**
     * Public constructor for ExportCommand
     */
    public ImportCommand(Path importCsvFilePath) {
        this.importCsvFilePath = importCsvFilePath;
        JsonAddressBookStorage jsonStorage =
            new JsonAddressBookStorage(projectRootPath.resolve("addressbook.json"));
        JsonUserPrefsStorage userPrefStorage =
            new JsonUserPrefsStorage(projectRootPath.resolve("config.json"));
        this.storage = new StorageManager(jsonStorage, userPrefStorage);
        haveDuplicatePersons = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(importCsvFilePath);
        requireNonNull(storage);
        readCsvFile(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /*
     * Reads the CSV file and imports the data into the address book
     */
    private void readCsvFile(Model model) throws CommandException {
        try {
            CSVReader reader = new CSVReader(new FileReader(importCsvFilePath.toFile()));
            String[] nextLine;
            reader.readNext(); // skip header
            while ((nextLine = reader.readNext()) != null) {

                Name name = ParserUtil.parseName(cleanDataString(nextLine[0]));
                StudentClass studentClass = ParserUtil.parseClass(cleanDataString(nextLine[1]));
                Phone phone = ParserUtil.parsePhone((nextLine[2].trim() == "") ? "00000000" : nextLine[2]);
                List<String> tagList = Arrays.asList(nextLine[3].split(" "));

                if (!"".equals(tagList.get(0))) {
                    Set<Tag> tags = ParserUtil.parseTags(tagList);
                    isDuplicatePerson(model, new Person(name, studentClass, phone, tags));
                } else {
                    isDuplicatePerson(model, new Person(name, studentClass, phone, null));
                }


            }
            if (haveDuplicatePersons) {
                throw new CommandException(
                    String.format("Data imported.\nDuplicate persons found in file: %s",
                            duplicatePersonsNames.toString()));
            }
        } catch (CsvValidationException e) {
            throw new CommandException(MESSAGE_CSV_ERROR);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_FILE_CORRUPTED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isDuplicatePerson(Model model, Person person) {
        if (model.hasPerson(person)) {
            haveDuplicatePersons = true;
            duplicatePersonsNames.add(person.getName().toString());
        } else {
            model.addPerson(person);
        }
    }

    /**
     * Method that fills in missing data with default values
     * @param data
     * @return
     */
    private String cleanDataString(String data) {
        return data.trim().isEmpty() ? "unknown" : data;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return importCsvFilePath.toString().equals(otherImportCommand.importCsvFilePath.toString());

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filepath: ", importCsvFilePath.toString())
                .toString();
    }
}
