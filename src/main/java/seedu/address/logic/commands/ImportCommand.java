package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.CsvToJsonConverter;
import seedu.address.storage.JsonImporter;
import seedu.address.storage.exceptions.ConverterException;
import seedu.address.storage.exceptions.ImporterException;

/**
 * Imports all .csv files in a directory to the address book
 */
public class ImportCommand extends Command{
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from a .csv file to the address book."
            + "Parameters: ";

    public static final String MESSAGE_SUCCESS = "Imported all contacts from: %1$s";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "This command has not been implemented yet.";

    private final File toImport;

    /**
     * Creates an ImportCommand to import a specified {@Code File}
     */
    public ImportCommand(File file) {
        requireNonNull(file);
        toImport = file;
    }

    public ImportCommand(){
        toImport = new File("Import");
        assert toImport.exists();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<File> jsonFiles;

        CsvToJsonConverter converter = new CsvToJsonConverter(toImport);
        try {
            jsonFiles = converter.convertAllCsvFiles();
        } catch (ConverterException ce) {
            throw new CommandException(ce);
        }

        JsonImporter importer = new JsonImporter(jsonFiles);
        try {
            importer.importAllJsonFiles(model);
        } catch (ImporterException e) {
            throw new CommandException(e);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toImport.getName()));
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
        return toImport.equals(otherImportCommand.toImport);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toImport", toImport.getName())
                .toString();
    }
}
