package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnenrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input String and creates a new UnenrollCommand object
 */
public class UnenrollCommandParser implements Parser<UnenrollCommand> {
    private static final Logger logger = LogsCenter.getLogger(UnenrollCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the UnenrollCommand
     * and returns an UnenrollCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnenrollCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments for UnenrollCommand cannot be null";

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL);
        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL)) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UnenrollCommandParser.class));
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrollCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrollCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL);
        String subject = argMultimap.getValue(PREFIX_TUTORIAL).get();

        return new UnenrollCommand(index, subject);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
