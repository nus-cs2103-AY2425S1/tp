package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object.
 * This parser checks if the input path is valid, ends with ".csv",
 * and contains no extra arguments or duplicate prefixes.
 */
public class ImportCommandParser implements Parser<ImportCommand> {


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
    public ImportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize the input arguments.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_PATH);

        // Check if the preamble is empty (no extra text before the first prefix).
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        // Check that the required prefix is present.
        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        // Check for duplicate prefixes.
        if (hasDuplicatePrefixes(argMultimap, CliSyntax.PREFIX_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        // Get the CSV file path and ensure it is not empty.
        String csvFilePath = argMultimap.getValue(CliSyntax.PREFIX_PATH).orElse("").trim();
        if (csvFilePath.isEmpty()) {
            throw new ParseException("CSV file path cannot be empty!");
        }

        // Check that the file ends with .csv extension.
        if (!csvFilePath.toLowerCase().endsWith(".csv")) {
            throw new ParseException("Invalid file extension. Please provide a .csv file!");
        }

        return new ImportCommand(csvFilePath);
    }
}


