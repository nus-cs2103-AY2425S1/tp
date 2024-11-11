package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * The ExportCommand class is responsible for exporting contacts from the address book to a CSV file.
 * When executed, it writes the list of persons, along with their details, to a file in CSV format.
 *
 * Usage example: {@code export}
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Contacts have been successfully exported";
    public static final String COLUMN_HEADERS = "Name,Phone Number,Email Address,Address,Tags";
    public static final String DEFAULT_DIRECTORY = "./data";
    public static final String FILE_NAME = "ExportedContacts.csv";
    public static final String MESSAGE_ERROR_EXPORTING_CONTACTS = "An error occurred while exporting contacts";

    // Use Paths to handle file paths across different environments
    public static final Path FILE_PATH = Paths.get(DEFAULT_DIRECTORY, FILE_NAME);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> personList = model.getPersonList();
        try {
            createDirectoryIfNotExists();
            exportContacts(FILE_PATH, personList);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_EXPORTING_CONTACTS);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Ensures the directory for the export file exists. If it doesn't, it is created.
     */
    private void createDirectoryIfNotExists() throws IOException {
        Path directoryPath = FILE_PATH.getParent();
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath); // Create directory if it doesn't exist
        }
    }

    /**
     * Exports the list of persons to the specified CSV file.
     *
     * @param filePath  The path to the CSV file.
     * @param personList The list of persons to export.
     * @throws IOException If an I/O error occurs.
     */
    private void exportContacts(Path filePath, List<Person> personList) throws IOException {
        // Use try-with-resources to automatically close the writer
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath.toFile(), false))) {
            bw.write(COLUMN_HEADERS);
            bw.newLine();
            for (Person person : personList) {
                bw.write(person.toCsvFormat());
                bw.newLine();
            }
        }
    }
}
