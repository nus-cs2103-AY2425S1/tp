package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.OweCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.OwedAmount;

/**
 * Parses input arguments and creates a new OweCommand object
 */
public class OweCommandParser {
    
    /**
     * Parses the given {@code String} of arguments in the context of the OweCommand
     * and returns an OweCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OweCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HOUR);
        Index index;
        
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OweCommand.MESSAGE_USAGE), pe);
        }
        
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HOUR);
        
        if (argMultimap.getValue(PREFIX_HOUR).isPresent()) {}
        
        return new OweCommand(Index.fromOneBased(1),"200");
    }
}
