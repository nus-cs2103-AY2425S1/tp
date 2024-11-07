package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.io.FileNotFoundException;
import java.util.Map;

import seedu.address.commons.exceptions.ImproperFormatException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.CsvImport;

/**
 * Adds a list of persons to VolunTier provided by an import file.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a list of persons provided in"
            + " the provided file. \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + " ~/Desktop/tp/data/import_file.csv";

    public static final String MESSAGE_SUCCESS = "%d persons added. ";
    public static final String MESSAGE_DUPLICATES = "Rows with duplicates: %s. ";

    public static final String MESSAGE_FAILED = "The following rows had data which failed some constraints: %s";

    private final String importFilePath;
    public ImportCommand(String importFilePath) {
        this.importFilePath = importFilePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        CsvImport importer = new CsvImport(importFilePath);
        int personsAdded;
        try {
            personsAdded = importer.readCsv(model);
        } catch (ImproperFormatException | FileNotFoundException e) {
            throw new CommandException(e.getMessage());
        } catch (NullPointerException e) {
            throw new CommandException("The file is empty.");
        }

        if (personsAdded != 0) {
            model.commitAddressBook();
        }
        return new CommandResult(returnMessage(personsAdded, importer));
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
        return importFilePath.equals(otherImportCommand.importFilePath);
    }

    /**
     * Formats the message to be returned when the ImportCommand is executed.
     */
    public String returnMessage(int successes, CsvImport importer) {
        StringBuilder message = new StringBuilder();
        message.append(String.format(MESSAGE_SUCCESS, successes));
        if (importer.hasDuplicates()) {
            message.append(String.format(MESSAGE_DUPLICATES, importer.getDuplicates()));
        }
        if (importer.hasFailed()) {
            Map<Integer, String> failed = importer.getFailed();
            StringBuilder failedRowsAndConstraints = new StringBuilder();
            for (Map.Entry<Integer, String> entry : failed.entrySet()) {
                failedRowsAndConstraints.append("Row " + entry.getKey());
                failedRowsAndConstraints.append(": " + entry.getValue() + ". ");
            }
            message.append(String.format(MESSAGE_FAILED, failedRowsAndConstraints));
        }
        return message.toString();
    }
}
