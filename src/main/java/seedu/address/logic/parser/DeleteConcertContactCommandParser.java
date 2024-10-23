package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteConcertContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteConcertContactCommand object
 */
public class DeleteConcertContactCommandParser implements Parser<DeleteConcertContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteConcertContactCommand
     * and returns an DeleteConcertContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteConcertContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_CONCERT);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CONCERT);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON);

        Index indexP;
        Index indexC;
        try {
            if (argMultimap.getValue(PREFIX_PERSON).isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            indexP = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());

            if (argMultimap.getValue(PREFIX_CONCERT).isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            indexC = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CONCERT).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteConcertContactCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteConcertContactCommand(indexP, indexC);
    }

}
