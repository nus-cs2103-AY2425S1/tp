package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents a command to export person data to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_WORD_SHORT_FORM = "ex";

    public static final String MESSAGE_USAGE = "Exports person data to a CSV file.\n"
        + "Command: " + COMMAND_WORD + " or " + COMMAND_WORD_SHORT_FORM + "\n"
        + "Parameters: " + PREFIX_PATH + "FILE_PATH\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_PATH + "data/persons.csv\n"
        + "Example: " + COMMAND_WORD_SHORT_FORM + " " + PREFIX_PATH.getShortPrefix() + "data/persons.csv";

    private final String filePath;

    /**
     * Constructs an ExportCommand with the specified file path for export.
     *
     * @param filePath The path where the CSV file will be saved.
     */
    public ExportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> persons = model.getAddressBook().getPersonList();

        // Create directories if they don't exist
        File file = createDirectories(filePath);

        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            return writeData(writer, persons);
        } catch (IOException e) {
            throw new CommandException("Error writing to the CSV file: " + e.getMessage());
        }
    }

    /**
     * Writes the person data to the provided CSVWriter.
     *
     * @param writer The CSVWriter to write the data to.
     * @param persons The list of persons to export.
     * @return CommandResult indicating the number of persons exported.
     */
    private CommandResult writeData(CSVWriter writer, List<Person> persons) {
        // Define CSV header.
        String[] header = {"Name", "Email", "Telegram", "Tags", "Github", "Assignments", "WeeksPresent"};
        writer.writeNext(header);

        // Write each person data to CSV file.
        for (Person person : persons) {
            // Join weeks present as a comma-separated string.
            String weeksPresentStr = person.getWeeksPresent().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

            // Prepare the person's data as a string array for CSV.
            String[] record = {
                person.getName().toString(),
                person.getEmail().toString(),
                person.getTelegram().toString(),
                String.join(",", person.getTags().stream().map(Tag::toString).toArray(String[]::new)),
                person.getGithub().toString(),
                String.join(",",
                    person.getAssignment()
                        .values()
                        .stream().map(Assignment::toCsvAdapted).toArray(String[]::new)), weeksPresentStr
            };
            writer.writeNext(record);
        }
        return new CommandResult("Exported " + persons.size() + " persons to CSV.");
    }

    /**
     * Ensures the directory structure for the specified file path exists,
     * creating it if necessary.
     *
     * @param filePath The file path for the CSV file.
     * @return The File object for the specified path.
     * @throws CommandException If the directory structure cannot be created.
     */
    private File createDirectories(String filePath) throws CommandException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        // Check and create parent directories if they don't exist.
        if (parentDir != null && !parentDir.exists()) {
            boolean isDirCreated = parentDir.mkdirs();
            if (!isDirCreated) {
                throw new CommandException("Failed to create directory structure for: " + filePath);
            }
        }
        return file;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ExportCommand e)) {
            return false;
        }
        return filePath.equals(e.filePath);
    }
}

