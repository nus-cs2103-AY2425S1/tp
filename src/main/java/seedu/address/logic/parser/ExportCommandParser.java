package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.util.stream.Stream;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 * This parser ensures the user-provided path is valid, ends with ".csv",
 * and contains no extra arguments.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Checks if all specified prefixes have values in the given ArgumentMultimap.
     *
     * @param argumentMultimap ArgumentMultimap containing parsed prefixes and their values.
     * @param prefixes Prefixes to check for presence.
     * @return true if all specified prefixes have non-empty values, otherwise false.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


    /**
     * Checks if any of the specified prefixes appears more than once in the ArgumentMultimap.
     *
     * @param argumentMultimap ArgumentMultimap containing parsed prefixes and their values.
     * @param prefixes Prefixes to check for duplicates.
     * @return true if any prefix appears more than once, otherwise false.
     */
    private static boolean hasDuplicatePrefixes(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }

    @Override
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize the input arguments.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_PATH);

        // Check that the required prefix is present.
        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        // Get the file path and ensure it is not empty.
        String filePath = argMultimap.getValue(CliSyntax.PREFIX_PATH).orElse("").trim();
        if (filePath.isEmpty()) {
            throw new ParseException("File path cannot be empty.");
        }


        // Check for duplicate prefixes.
        if (hasDuplicatePrefixes(argMultimap, CliSyntax.PREFIX_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }


        // Ensure preamble is empty.
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        // Ensures that file has .csv extension
        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new ParseException("Invalid file extension. Please provide a file path ending with .csv!");
        }

        // Validate filename portion of path
        File inputFile = new File(filePath);
        String fileName = inputFile.getName(); // Get the name of the file
        if (fileName.isEmpty() || fileName.equals(".csv")) {
            throw new ParseException("Invalid file name. Please provide a valid file name before the .csv extension!");
        }

        return new ExportCommand(filePath);
    }
}
