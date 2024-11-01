package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EnrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input String and creates a new EnrollCommand object
 */
public class EnrollCommandParser implements Parser<EnrollCommand> {
    private static final Logger logger = LogsCenter.getLogger(EnrollCommandParser.class);


    /**
     * Parses the given {@code String} of arguments in the context of the EnrollCommand
     * and returns an EnrollCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EnrollCommand parse(String args) throws ParseException {
        assert args != null : "Input arguments for EnrollCommand cannot be null";

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL);
        if (!arePrefixesPresent(argMultimap, PREFIX_TUTORIAL)) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, EnrollCommandParser.class));
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnrollCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnrollCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TUTORIAL);
        String subject = argMultimap.getValue(PREFIX_TUTORIAL).get();

        return new EnrollCommand(index, subject);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
