package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OweCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new OweCommand object
 */
public class OweCommandParser implements Parser<OweCommand> {
    private static final Logger logger = Logger.getLogger(OweCommandParser.class.getName());
    
    /**
     * Parses the given {@code String} of arguments in the context of the OweCommand
     * and returns an OweCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OweCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.log(Level.INFO, "Parsing OweCommand with arguments: " + args);
        
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HOUR);
        Index index;
        double hour;
        
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Failed to parse index. Invalid command format.", pe);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OweCommand.MESSAGE_USAGE), pe);
        }
        
        if (!arePrefixHourPresent(argMultimap)) {
            logger.log(Level.WARNING, "Failed to parse hours owed. Invalid parameters");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OweCommand.MESSAGE_USAGE));
        }
        
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HOUR);
        
        hour = ParserUtil.parseHour(argMultimap.getValue(PREFIX_HOUR).get());
       
        logger.log(Level.INFO, "Successfully parsed OweCommand.");
        return new OweCommand(index, hour);
    }
    
    private static boolean arePrefixHourPresent (ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_HOUR).isPresent();
    }
}
