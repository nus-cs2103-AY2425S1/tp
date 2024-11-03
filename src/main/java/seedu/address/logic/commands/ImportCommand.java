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
import seedu.address.model.Model;
import seedu.address.model.person.Person;

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
        "Your header should be Name, Phone, Email, Telegram, Tags, Github, Assignments, WeeksPresent"
            + " (Case insensitive, Order sensitive)";

    private final String csvFilePath;

    /**
     * Instantiates an ImportCommand to import data from the specified file path.
     *
     * @param csvFilePath the path to the CSV file
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
            String[] expectedHeaders = {"Name", "Phone", "Email", "Telegram", "Tags", "Github",
                "Assignments", "WeeksPresent"};

            // Read header and ensure it is as expected.
            String[] headers = csvReader.readNext();
            checkHeaders(headers, expectedHeaders);

            // Read each line, parse the person data, then add to newPersons list.
            while ((fields = csvReader.readNext()) != null) {
                if (fields.length != expectedHeaders.length) {
                    throw new CommandException("Invalid CSV format.");
                }
                Person person = CsvPersonParser.parsePerson(fields, model);
                newPersons.add(person);
            }
        } catch (IOException | CsvValidationException e) {
            throw new CommandException("Error reading from the CSV file: " + e.getMessage());
        }
        // Replace all existing person with those present in the CSV file.
        model.replaceAllPersons(newPersons);

        return new CommandResult(String.format("Successfully imported %d persons.", newPersons.size()));
    }

    /**
     * Checks if the CSV headers match the expected headers.
     *
     * @param headers the headers from the CSV file
     * @param expectedHeaders the expected headers in the correct order
     * @throws CommandException if the headers are incorrect
     */
    private void checkHeaders(String[] headers, String[] expectedHeaders) throws CommandException {
        System.out.println(headers.length);
        System.out.println(headers.toString());
        if (headers.length == 0) {
            throw new CommandException("CSV header is empty/contains empty values, please ensure"
                + " all headers are valid.\n"
                + CORRECT_HEADER_USAGE);
        }

        for (int i = 0; i < headers.length; i++) {
            headers[i] = headers[i].trim(); // Trim whitespace from headers
        }

        for (String header : headers) {
            if (header.isEmpty() && headers.length <= 8) {
                throw new CommandException("CSV header is empty/contains empty values, please ensure"
                    + " all headers are valid.\n"
                    + CORRECT_HEADER_USAGE);
            }
        }

        if (headers.length > expectedHeaders.length) {
            throw new CommandException("There is an extra column!\n"
                + "Please ensure there is only be 8 corresponding header/data columns\n" + CORRECT_HEADER_USAGE);
        }

        if (headers.length < expectedHeaders.length) {
            throw new CommandException("There are lesser columns in header than expected!\n" + CORRECT_HEADER_USAGE);
        }

        for (int i = 0; i < headers.length; i++) {
            if (!headers[i].trim().equalsIgnoreCase(expectedHeaders[i])) {
                throw new CommandException("Header is defined incorrectly!\n" + CORRECT_HEADER_USAGE);
            }
        }
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



