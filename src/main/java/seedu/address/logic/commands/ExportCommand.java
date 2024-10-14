package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Exported file to /output/volunteers.csv successfully.";
    public static final String MESSAGE_DIRECTORY = "Failed to create output directory.";
    public static final String MESSAGE_CSV_WRITING = "An error occurred while writing the CSV file.";
    public static final String OUTPUT_DIRECTORY = "output";
    public static final String CSV_FILE_PATH = "output/volunteers.csv";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> volunteers = model.getFilteredPersonList();

        StringBuilder csvOutput = new StringBuilder();

        // CSV headers
        csvOutput.append("Name,Phone,Email,Address,Tags\n");

        // Build the CSV content
        for (Person volunteer : volunteers) {
            // Convert tags to a comma-separated string, wrapped in quotes
            Set<Tag> tags = volunteer.getTags();
            String tagsString = tags.stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(", ")); // Join tags with comma separator

            // Enclose fields to prevent commas from messing with CSV
            csvOutput.append("\"").append(volunteer.getName()).append("\",")
                    .append(volunteer.getPhone()).append(",")
                    .append(volunteer.getEmail()).append(",")
                    .append("\"").append(volunteer.getAddress()).append("\",")
                    .append("\"").append(tagsString).append("\"\n"); // Append tags, wrapped in quotes
        }

        // Check if output directory exists, if not create it
        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists()) {
            boolean created = outputDir.mkdirs();
            if (!created) {
                throw new CommandException(MESSAGE_DIRECTORY);
            }
        }

        // Write to CSV file
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH)) {
            writer.write(csvOutput.toString());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_CSV_WRITING);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
