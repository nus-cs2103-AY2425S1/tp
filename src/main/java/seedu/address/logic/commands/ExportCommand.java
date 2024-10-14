package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

/**
 * Lists all persons in the address book to the user.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Exported file to /output/volunteers.csv and /output/events.csv successfully.";
    public static final String MESSAGE_DIRECTORY = "Failed to create output directory.";
    public static final String MESSAGE_CSV_WRITING = "An error occurred while writing the CSV file.";
    public static final String OUTPUT_DIRECTORY = "output";
    public static final String VOLUNTEERS_CSV_FILE_PATH = "output/volunteers.csv";
    public static final String EVENTS_CSV_FILE_PATH = "output/events.csv";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Volunteer> volunteers = model.getFilteredVolunteerList();

        StringBuilder volunteerCsvOutput = new StringBuilder();

        // CSV headers
        volunteerCsvOutput.append("Name,Phone,Email,Available Date,Start Time,End Time\n");

        // Build the CSV content
        for (Volunteer volunteer : volunteers) {
            // Enclose fields to prevent commas from messing with CSV
            volunteerCsvOutput.append("\"").append(volunteer.getName()).append("\",")
                    .append(volunteer.getPhone()).append(",")
                    .append(volunteer.getEmail()).append(",")
                    .append(volunteer.getAvailableDate()).append(",")
                    .append(volunteer.getStartTimeAvailability()).append(",")
                    .append(volunteer.getEndTimeAvailability())
                    .append("\n");
        }

        ObservableList<Event> events = model.getFilteredEventList();

        StringBuilder eventCsvOutput = new StringBuilder();

        // CSV headers
        eventCsvOutput.append("Name,Date,Start Time,End Time,Location,Description\n");

        // Build the CSV content
        for (Event event : events) {
            // Enclose fields to prevent commas from messing with CSV
            eventCsvOutput.append("\"").append(event.getName()).append("\",")
                    .append(event.getDate()).append(",")
                    .append(event.getStartTime()).append(",")
                    .append(event.getEndTime()).append(",")
                    .append(event.getLocation()).append(",")
                    .append(event.getDescription())
                    .append("\n");
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
        try (FileWriter writer = new FileWriter(VOLUNTEERS_CSV_FILE_PATH)) {
            writer.write(volunteerCsvOutput.toString());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_CSV_WRITING);
        }

        try (FileWriter writer = new FileWriter(EVENTS_CSV_FILE_PATH)) {
            writer.write(eventCsvOutput.toString());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_CSV_WRITING);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
