package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.util.ArrayList;

import seedu.address.commons.exceptions.ImproperFormatException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.CsvImport;

/**
 * Adds a  list of persons to the address book provided by an import file.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a list of persons provided in"
            + " the provided file. \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "~/data/test.txt";

    public static final String MESSAGE_SUCCESS = "%d persons added.";
    public static final String MESSAGE_FAILED = "Rows with duplicates: %s.";

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
        } catch (ImproperFormatException e) {
            throw new CommandException(e.getMessage());
        }

        ArrayList<Integer> personsFailed = importer.getDuplicates();
        if (!importer.hasFailures()) {
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, personsAdded));
        } else if (personsAdded == 0) {
            return new CommandResult(String.format(MESSAGE_FAILED, personsFailed));
        } else {
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, personsAdded)
                    + " " + String.format(MESSAGE_FAILED, personsFailed));
        }
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toImport", importFilePath)
                .toString();
    }
}
