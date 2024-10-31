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
    public static final String COMMAND_WORD = "import";
    public static final String FILE_OPS_ERROR_FORMAT = "Could not import data due to the following error: %s";
    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not import data to file %s due to insufficient permissions to read to the file or the folder.";
    public static final String MESSAGE_CSV_ERROR = "Error reading CSV file";
    public static final String MESSAGE_FILE_CORRUPTED = "Data in file is corrupted or missing data";
    public static final String MESSAGE_FILE_NOT_FOUND =
        "File not found\nAre you using the correct absolute file path?"
        + "\nExample: Users/username/Desktop/addressbook.csv";
    public static final String MESSAGE_SUCCESS = "Data imported. \n%d students imported.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Imports the data from a csv file.\n"
        + "Parameters: import <absolute file path>\n"
        + "Example: " + COMMAND_WORD
        + " /Users/username/Desktop/addressbook.csv";

    private static final Path projectRootPath = Paths.get(System.getProperty("user.dir"));
    private Path importCsvFilePath;
    private Storage storage;

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
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(importCsvFilePath);
        requireNonNull(storage);
        int studentsImported = readCsvFile(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentsImported));
    }

    /*
     * Reads the CSV file and imports the data into the address book
     */
    private int readCsvFile(Model model) throws CommandException {
        List<String> duplicatePersonsNames = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(importCsvFilePath.toFile()));
            String[] nextLine;
            reader.readNext(); // skip header
            int[] importStudentNumbers = {0, 0}; // [0] = no. imported, [1] = no. duplicates
            while ((nextLine = reader.readNext()) != null) {
                Name name = ParserUtil.parseName(nextLine[0]);
                StudentClass studentClass = ParserUtil.parseClass(cleanDataString(nextLine[1]));
                Phone phone = ParserUtil.parsePhone((nextLine[2].trim() == "") ? "00000000" : nextLine[2]);
                List<String> tagList = Arrays.asList(nextLine[3].split(" "));

                if (!"".equals(tagList.get(0))) {
                    Set<Tag> tags = ParserUtil.parseTags(tagList);
                    importStudentNumbers = handleAddStudent(model, new Person(name, studentClass, phone, tags),
                        importStudentNumbers, duplicatePersonsNames);
                } else {
                    importStudentNumbers = handleAddStudent(model, new Person(name, studentClass, phone, null),
                        importStudentNumbers, duplicatePersonsNames);
                }
            }

            if (importStudentNumbers[0] == 0 || importStudentNumbers[1] > 0) {
                String message = produceImportMessageToUser(importStudentNumbers, duplicatePersonsNames);
                throw new CommandException(message);
            }
            reader.close();
            return importStudentNumbers[0];
        } catch (CsvValidationException e) {
            throw new CommandException(MESSAGE_CSV_ERROR);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_FILE_CORRUPTED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Method that produces a message to the user based on the number of students imported and duplicates found
     * @param importStudentNumbers
     * @return
     */
    private String produceImportMessageToUser(int[] importStudentNumbers, List<String> duplicatePersonsNames) {
        String message = "";
        if (importStudentNumbers[0] == 0) {
            message += "No students imported.";
        } else {
            message += String.format("Data imported with %d students added.", importStudentNumbers[0]);
        }

        if (importStudentNumbers[1] > 0) {
            message += String.format("\n%d Duplicate person(s) found in file: %s",
                importStudentNumbers[1],
                duplicatePersonsNames.toString());
        }
        return message;
    }

    /**
     * Method that checks if the person is a duplicate and adds non dups to the model
     * @param model
     * @param person
     * @param importStudentNumbers
     * @param duplicatePersonsNames
     * @return
     */
    private int[] handleAddStudent(Model model, Person person,
            int[] importStudentNumbers, List<String> duplicatePersonsNames) {
        System.out.println("eee");
        if (model.hasPerson(person)) {
            duplicatePersonsNames.add(person.getName().toString());
            importStudentNumbers[1]++;
        } else {
            System.out.println("etst");
            model.addPerson(person);
            importStudentNumbers[0]++;
        }
        return importStudentNumbers;
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
