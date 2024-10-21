package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CreateWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;

/**
 * Parses input arguments and creates a new CreateWeddingCommand object
 */
public class CreateWeddingCommandParser implements Parser<CreateWeddingCommand> {
    /**
     * Parses a {@code String} of arguments in the context of the CreateWeddingCommand
     * and returns an CreateWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CreateWeddingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING);
        Index[] partnerIndexes;
        try {
            partnerIndexes = ParserUtil.parseTwoIndexes(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateWeddingCommand.MESSAGE_USAGE),
                    pe);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING)
                || partnerIndexes.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateWeddingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEDDING);
        Wedding wedding = ParserUtil.parseWedding(argMultimap.getValue(PREFIX_WEDDING).get());
        return new CreateWeddingCommand(wedding);
    }

    /**
     * Returns true if none of the prefixes contain empty {@code Optional} values in the given {@code ArgumentMultimap}
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
