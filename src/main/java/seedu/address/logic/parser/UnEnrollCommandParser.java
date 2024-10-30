package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnEnrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EnrollCommand object
 */
public class UnEnrollCommandParser implements Parser<UnEnrollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EnrollCommand
     * and returns an EnrollCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnEnrollCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL);
        Index index;

        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnEnrollCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnEnrollCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL);
        String subject = argMultimap.getValue(PREFIX_TUTORIAL).get();
        return new UnEnrollCommand(index, subject);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
