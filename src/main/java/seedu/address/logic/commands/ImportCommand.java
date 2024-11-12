package seedu.address.logic.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * The {@code ImportCommand} class is responsible for importing contacts from a CSV file into the address book.
 * It reads the CSV file, validates its format, and adds the contacts to the model.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "The contacts from %s have been successfully imported";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the contacts from the specified file located in the data folder \n"
            + "Parameters: FILENAME (Must be a valid file name)\n"
            + "Example: " + COMMAND_WORD + " contacts.csv";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "The specified file does not exist";
    public static final String MESSAGE_INCORRECT_FILE_FORMAT = "The format of the specified file is incorrect";
    public static final String MESSAGE_ERROR_READING_FILE = "There was an error when reading the file";
    public static final String COLUMN_HEADERS = "Name,Phone Number,Email Address,Address,Tags";
    public static final String DEFAULT_DIRECTORY = "./data";

    private final String fileName;
    private final Path filePath;

    /**
     * Constructs an {@code ImportCommand} with the specified file name.
     *
     * @param fileName The name of the CSV file to import.
     */
    public ImportCommand(String fileName) {
        this.fileName = fileName;
        this.filePath = Paths.get(DEFAULT_DIRECTORY, fileName);
    }

    /**
     * Executes the import command, reading contacts from a CSV file and adding them to the model.
     *
     * @param model The {@code Model} which the command should operate on.
     * @return A {@code CommandResult} containing the result of the command.
     * @throws CommandException If there is an error during the import process.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Ensure the file exists
        if (!Files.exists(filePath)) {
            throw new CommandException(MESSAGE_FILE_DOES_NOT_EXIST);
        }

        // Validate the format of the CSV file
        if (!checkCsvFileFormat(filePath)) {
            throw new CommandException(MESSAGE_INCORRECT_FILE_FORMAT);
        }

        // Parse and import the list of persons from the file
        List<Person> personList = getPersonList(filePath);
        for (Person person : personList) {
            if (!model.hasPerson(person)) {
                int updatedPersonId = model.generateNewPersonId();
                Person updatedIdPerson = person.changeId(updatedPersonId);
                model.addPerson(updatedIdPerson);
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

    /**
     * Checks if the CSV file format is correct by validating the column headers.
     *
     * @param filePath The path to the CSV file.
     * @return True if the format is correct, false otherwise.
     * @throws CommandException If there is an issue reading the file.
     */
    private boolean checkCsvFileFormat(Path filePath) throws CommandException {
        // Check if the file has a .csv extension
        String fileName = filePath.getFileName().toString();
        if (!fileName.toLowerCase().endsWith(".csv")) {
            return false;
        }
        // Proceed to check the header format
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String headerLine = br.readLine();
            return headerLine != null && headerLine.equals(COLUMN_HEADERS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_READING_FILE);
        }
    }


    /**
     * Reads the list of persons from the CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return The list of persons.
     * @throws CommandException If there is an issue reading or parsing the file.
     */
    private List<Person> getPersonList(Path filePath) throws CommandException {
        List<Person> personList = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            // Skip the header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                Person person = parseLine(trimLine(line));
                personList.add(person);
            }
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_READING_FILE);
        }

        return personList;
    }

    /**
     * Trims a line from the CSV file to remove extra characters like surrounding quotes.
     *
     * @param line The line to trim.
     * @return The trimmed line.
     * @throws CommandException If the line format is incorrect.
     */
    private String trimLine(String line) throws CommandException {
        try {
            return line.substring(1, line.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INCORRECT_FILE_FORMAT);
        }
    }

    /**
     * Parses a line from the CSV file into a {@code Person} object.
     *
     * @param line The line to parse.
     * @return The {@code Person} object.
     * @throws CommandException If the line format is incorrect.
     */
    private Person parseLine(String line) throws CommandException {
        String[] arr = line.split("\",\"");
        if (arr.length != 4 && arr.length != 5) {
            throw new CommandException(MESSAGE_INCORRECT_FILE_FORMAT);
        }
        try {
            Name name = ParserUtil.parseName(arr[0]);
            Phone phone = ParserUtil.parsePhone(arr[1]);
            Email email = ParserUtil.parseEmail(arr[2]);
            Address address = ParserUtil.parseAddress(arr[3]);
            List<String> tagList = arr.length == 5 ? Arrays.asList(arr[4].split(",")) : new ArrayList<>();
            Set<Tag> tags = ParserUtil.parseTags(tagList);

            return new Person(name, phone, email, address, tags);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_INCORRECT_FILE_FORMAT);
        }
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
        return filePath.equals(otherImportCommand.filePath);
    }
}

