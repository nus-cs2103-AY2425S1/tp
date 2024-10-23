package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports the emails of current listed participants
 * into a file whose path is specified by the user as a string.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the emails of current listed participants into a specified file.\n"
            + "Parameters: [FILENAME]\n"
            + "Example: " + COMMAND_WORD + "MyEmailsList.txt";
    public static final String MESSAGE_SUCCESS = "Exported participants' emails to \n";
    public static final String MESSAGE_EMPTY = "No participants to export";
    public static final String FILE_WRITE_ERROR = "Unable to export participants' emails due to unexpected I/O error";
    public static final String FILE_WRITE_PERMISSION_ERROR =
            "Unable to export participants' emails due to denied export file access";

    private final File exportFile;

    public ExportCommand(File exportFile) {
        this.exportFile = exportFile;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(exportFile);
        ObservableList<Person> contactsList = model.getFilteredPersonList();
        if (contactsList.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        try {
            FileWriter fileWriter = new FileWriter(exportFile);
            for (Person person : contactsList) {
                String email = person.getEmail().toString();
                fileWriter.write(email + System.lineSeparator());
            }
            fileWriter.close();
        } catch (IOException ioe) {
            if (exportFile.exists() && !exportFile.canWrite()) {
                throw new CommandException(FILE_WRITE_PERMISSION_ERROR);
            } else {
                throw new CommandException(FILE_WRITE_ERROR);
            }
        }
        return new CommandResult(MESSAGE_SUCCESS + exportFile.getAbsolutePath());
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
        return exportFile.equals(otherExportCommand.exportFile);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("exportFile", exportFile)
                .toString();
    }
}
