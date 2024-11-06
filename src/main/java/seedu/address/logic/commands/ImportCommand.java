package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CsvPersonParser;
import seedu.address.logic.parser.CsvRowParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Command to import person data from a CSV file into the address book.
 * Expects a CSV file with a specific header format that is case-insensitive but order-sensitive.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports person data from a CSV file.\n"
        + "Parameters: FILE_PATH"
        + "[" + PREFIX_PATH + "FILE_PATH]\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_PATH + "data/persons.csv";
    public static final String CORRECT_HEADER_USAGE =
        "Header of CSV file should be Name, Email, Telegram, Tags, Github, Assignments, WeeksPresent"
            + " (Case insensitive, Order sensitive)";
    private static final String MESSAGE_INVALID_CSV = "Invalid CSV format, ensure that all necessary data are present.";
    private static final String MESSAGE_MISSING_PERSON_DATA = "There is no person data present.";
    private static final String MESSAGE_NULL_FIELDS = "Please ensure that there is no null fields";
    public static final String MESSAGE_READING_ERROR = "Error reading from the CSV file path: ";

    private final String csvFilePath;

    /**
     * Instantiates an ImportCommand to import data from the specified file path.
     *
     * @param csvFilePath the path to the CSV file.
     */
    public ImportCommand(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> newPersons = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] fields;

            // Define the expected headers in this specific order.
            String[] expectedHeaders = {"Name", "Email", "Telegram", "Tags", "Github",
                "Assignments", "WeeksPresent"};

            // Read header and ensure it is as expected.
            String[] headers = csvReader.readNext();
            headers = CsvRowParser.cleanRow(headers);
            CsvRowParser.checkHeaders(headers, expectedHeaders);

            // Read each line, parse the person data, then add to newPersons list.
            while ((fields = csvReader.readNext()) != null) {
                if (CsvRowParser.isRowEmpty(fields)) {
                    continue; // Skip empty rows
                }
                String[] cleanedFields = CsvRowParser.cleanRow(fields);
                if (cleanedFields.length != expectedHeaders.length) {
                    throw new CommandException(MESSAGE_INVALID_CSV);
                }
                Person person = CsvPersonParser.parsePerson(cleanedFields, model);
                newPersons.add(person);
            }

            if (newPersons.isEmpty()) {
                throw new CommandException(MESSAGE_MISSING_PERSON_DATA);
            }
            model.replaceAllPersons(newPersons);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_READING_ERROR + e.getMessage()
                + "\nPlease check file path provided again");
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_READING_ERROR + e.getMessage()
                + "\nPlease ensure that there are no duplicate person in the CSV file");
        } catch (CsvValidationException | CommandException e) {
            throw new CommandException(MESSAGE_READING_ERROR + e.getMessage());
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_NULL_FIELDS);
        }

        // Replace all existing person with those present in the CSV file.

        return new CommandResult(String.format("Successfully imported %d persons.", newPersons.size()));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImportCommand other)) {
            return false;
        }
        return this.csvFilePath.equals(other.csvFilePath);
    }
}



