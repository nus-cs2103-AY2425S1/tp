package seedu.address.logic.parser.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.wedding.AssignWeddingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Parses input arguments and creates a new AssignWeddingCommand object.
 */
public class AssignWeddingCommandParser implements Parser<AssignWeddingCommand> {
    /**
     * Parses the given String of arguments in the context of the AssignWeddingCommand
     * and returns a AssignWeddingCommand object for execution.
     *
     * @param args the user input string containing the index and weddings to be added
     * @return a new {@code AssignWeddingCommand} object that contains the parsed index and list of weddings
     * @throws ParseException if the input does not conform to the expected format (i.e., invalid index
     *      or missing weddings)
     */
    public AssignWeddingCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_WEDDING, PREFIX_FORCE);
        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignWeddingCommand.MESSAGE_USAGE));
        }

        try {
            // Parse the index from the preamble
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignWeddingCommand.MESSAGE_USAGE),
                    ive);
        }

        List<String> weddingValues = argMultimap.getAllValues(PREFIX_WEDDING);
        if (weddingValues.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignWeddingCommand.MESSAGE_USAGE));
        }

        // Checks that all weddings have valid names
        if (weddingValues.stream().anyMatch(wedding -> !Wedding.isValidWeddingName(wedding))) {
            throw new ParseException(WeddingName.MESSAGE_CONSTRAINTS);
        }

        Map<Wedding, String> weddings = weddingValues.stream()
                .collect(Collectors.toMap(
                        value -> new Wedding(new WeddingName(value.split(" p[12]/")[0])),
                        // Key: Wedding object with the name before "/p1" or "/p2"
                        value -> {
                            // Determine the type ("p1", "p2", or "g")
                            if (value.contains(" p1/")) {
                                return "p1";
                            } else if (value.contains(" p2/")) {
                                return "p2";
                            } else {
                                return "g";
                            }
                        }
                ));

        return new AssignWeddingCommand(index, weddings, arePrefixesPresent(argMultimap, PREFIX_FORCE));


    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
