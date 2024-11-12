package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddConcertContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddConcertContactCommand object
 */
public class AddConcertContactCommandParser implements Parser<AddConcertContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddConcertContactCommand
     * and returns an AddConcertContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddConcertContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON,
                PREFIX_CONCERT);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CONCERT);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON);

        try {
            if (argMultimap.getValue(PREFIX_PERSON).isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }

            if (argMultimap.getValue(PREFIX_CONCERT).isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddConcertContactCommand.MESSAGE_USAGE), pe);
        }

        Index indexP = ParserUtil.parseIndexWithMessage(argMultimap.getValue(PREFIX_PERSON).get(),
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        Index indexC = ParserUtil.parseIndexWithMessage(argMultimap.getValue(PREFIX_CONCERT).get(),
                MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);

        assert indexP != null : "Person index cannot be null";
        assert indexC != null : "Concert index cannot be null";

        return new AddConcertContactCommand(indexP, indexC);
    }

}
