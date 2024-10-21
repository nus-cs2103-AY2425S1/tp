package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports contact list to a csv format.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the whole contact list"
            + "to a specfied file type.\n"
            + "Parameters: " + "FILETYPE\n"
            + "Example: " + COMMAND_WORD + " f/csv";

    public static final String MESSAGE_SUCCESS = "Contact list successfully exported to a %1$s file";
    public static final String MESSAGE_FAILURE = "Unable to export contact list to a %1$s file";

    private final String csvHeaders = "Name,Phone No,Email,Address,Tags,Notes";

    private final Path exportCsvPath = Paths.get("exports" , "bizbook.csv");

    private final String fileType;

    /**
     * Creates an ExportCsvCommand.
     *
     * @param fileType The file type to export the data into.
     */
    public ExportCommand(String fileType) {
        requireNonNull(fileType);
        this.fileType = fileType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            if (!Files.exists(exportCsvPath.getParent())) {
                Files.createDirectories(exportCsvPath.getName(0));
            }
            // Create a csv file to save the tasks
            File dataFile = new File(exportCsvPath.toString());
            FileWriter fw = new FileWriter(dataFile);

            fw.write(this.csvHeaders);

            ObservableList<Person> personList = model.getFilteredPersonList();
            for (Person person : personList) {
                String personData = person.toCsvString();
                fw.write(personData + "\n");
            }
            fw.close();
        } catch (IOException io) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileType));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand otherExportCommand = (ExportCommand) other;
        return fileType.equals(otherExportCommand.fileType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("fileType", fileType)
                .toString();
    }
}
