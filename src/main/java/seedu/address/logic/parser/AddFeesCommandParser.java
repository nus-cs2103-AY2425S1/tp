package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddFeesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Fees;

/**
 * Parses input arguments and creates a new AddFeesCommand object
 */
public class AddFeesCommandParser implements Parser<AddFeesCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddFeesCommandParser.class);


    /**
     * Parses the given {@code String} of arguments in the context of the AddFeesCommand
     * and returns a AddFeesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFeesCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PAYMENT);
        if (!arePrefixesPresent(argMultimap, PREFIX_PAYMENT)) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, AddFeesCommandParser.class));
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFeesCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFeesCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PAYMENT);
        Fees fees = ParserUtil.parseFees(argMultimap.getValue(PREFIX_PAYMENT).get());

        return new AddFeesCommand(index, fees);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

