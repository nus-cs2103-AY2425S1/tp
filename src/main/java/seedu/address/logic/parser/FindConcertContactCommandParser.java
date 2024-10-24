package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindConcertContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindConcertContactCommand object
 */
public class FindConcertContactCommandParser implements Parser<FindConcertContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindConcertContactCommand
     * and returns a FindConcertContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindConcertContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertContactCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_CONCERT);

        if (argMultimap.getValue(PREFIX_PERSON).isEmpty() && argMultimap.getValue(PREFIX_CONCERT).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindConcertContactCommand.MESSAGE_USAGE));
        }
        assert argMultimap.getValue(PREFIX_PERSON).isPresent() || argMultimap.getValue(PREFIX_CONCERT).isPresent()
                : "Both person and concert prefixes cannot be missing at the same time";

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON, PREFIX_CONCERT);

        Index indexP = null;
        if (argMultimap.getValue(PREFIX_PERSON).isPresent()) {
            indexP = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
        }

        Index indexC = null;
        if (argMultimap.getValue(PREFIX_CONCERT).isPresent()) {
            indexC = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CONCERT).get());
        }

        return new FindConcertContactCommand(indexP, indexC);
    }
}
