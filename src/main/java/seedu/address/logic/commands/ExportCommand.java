package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVWriter;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The type Export command.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports person data to a CSV file.\n"
            + "Parameters: FILE_PATH"
            + "[" + PREFIX_PATH + "FILE_PATH]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PATH + "data/persons.csv";

    private final String filePath;

    public ExportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> persons = model.getAddressBook().getPersonList();

        // Create directories if they don't exist
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean dirCreated = parentDir.mkdirs();
            if (!dirCreated) {
                throw new CommandException("Failed to create directory structure for: " + filePath);
            }
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            // Write CSV Header
            String[] header = {"Name", "Phone", "Email", "Address", "Telegram", "Tags", "Github", "Assignment"};
            writer.writeNext(header);

            // Write each person data
            for (Person person : persons) {
                String[] record = {
                        person.getName().toString(),
                        person.getPhone().toString(),
                        person.getEmail().toString(),
                        person.getAddress().toString(),
                        person.getTelegram().toString(),
                        String.join(",", person.getTags().stream().map(Tag::toString).toArray(String[]::new)),
                        person.getGithub().toString(),
                        String.join(",",
                                person.getAssignment()
                                        .values()
                                        .stream().map(Assignment::toCsvAdapted).toArray(String[]::new))
                };
                writer.writeNext(record);
            }
            return new CommandResult("Exported " + persons.size() + " persons to CSV.");
        } catch (IOException e) {
            throw new CommandException("Error writing to the CSV file: " + e.getMessage());
        }
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

