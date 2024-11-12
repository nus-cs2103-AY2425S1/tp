package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.CsvToJsonConverter;
import seedu.address.storage.JsonImporter;
import seedu.address.storage.exceptions.ConverterException;
import seedu.address.storage.exceptions.ImporterException;

/**
 * Imports all .csv files in a directory to the address book
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from a .csv file to the address book."
            + "Parameters: ";

    public static final String MESSAGE_SUCCESS = "Imported all contacts successfully";

    private final File toImport;

    /**
     * Creates an ImportCommand to import .csv files from the "Import" folder
     */
    public ImportCommand() {
        toImport = new File("Import");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<File> jsonFiles;
        CsvToJsonConverter converter;
        try {
            converter = new CsvToJsonConverter(toImport);
        } catch (IllegalArgumentException iae) {
            throw new CommandException(iae.getMessage());
        }
        try {
            jsonFiles = converter.convertAllCsvFiles();
        } catch (ConverterException ce) {
            throw new CommandException(ce.getMessage());
        }

        JsonImporter importer = new JsonImporter(jsonFiles);
        try {
            importer.importAllJsonFiles(model);
        } catch (ImporterException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return toImport.equals(otherImportCommand.toImport);
    }

    @Override
    public String toString() {
        return toImport.getName();
    }

    public File getToImport() {
        return toImport;
    }
}
