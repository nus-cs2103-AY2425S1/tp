package seedu.address.logic.parser.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.wedding.UnassignWeddingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Parses input arguments and create a new UnassignWeddingCommand object.
 */
public class UnassignWeddingCommandParser implements Parser<UnassignWeddingCommand> {

    /**
     * Parses the given String of arguments in the context of the UnassignWeddingCommand
     * and returns a UnassignWeddingCommand object for execution.
     *
     * @param args the user input string containing the index and weddings to be removed
     * @return a new UntagCommand object that contains the parsed index and list of weddings
     * @throws ParseException if the input does not conform to the expected format (i.e., invalid index
     *      or missing weddings)
     */
    public UnassignWeddingCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WEDDING);
        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignWeddingCommand.MESSAGE_USAGE));
        }

        try {
            // Parse the index from the preamble
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignWeddingCommand.MESSAGE_USAGE), ive);
        }

        List<String> weddingValues = argMultimap.getAllValues(PREFIX_WEDDING);
        if (weddingValues.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignWeddingCommand.MESSAGE_USAGE));
        }

        // Checks that all weddings have valid names
        if (weddingValues.stream().anyMatch(wedding -> !Wedding.isValidWeddingName(wedding))) {
            throw new ParseException(WeddingName.MESSAGE_CONSTRAINTS);
        }

        // Convert wedding values to Wedding objects
        List<Wedding> weddings = weddingValues.stream()
                .map(WeddingName::new)
                .map(Wedding::new)
                .collect(Collectors.toList());

        return new UnassignWeddingCommand(index, new HashSet<>(weddings));
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
