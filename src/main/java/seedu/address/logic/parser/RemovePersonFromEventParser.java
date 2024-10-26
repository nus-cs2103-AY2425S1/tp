package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.RemovePersonFromEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.NoSuchElementException;


/**
 * Parses input arguments and creates a new RemovePersonFromEventCommand object
 */
public class RemovePersonFromEventParser implements Parser<RemovePersonFromEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePersonFromEventCommand
     * and returns a RemovePersonFromEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePersonFromEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_EVENT_INDEX, CliSyntax.PREFIX_PERSON_INDEX);

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_EVENT_INDEX, CliSyntax.PREFIX_PERSON_INDEX);

        Index personIndex;
        Index eventIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_PERSON_INDEX).get());
            eventIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_EVENT_INDEX).get());

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePersonFromEventCommand.MESSAGE_USAGE), pe);
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePersonFromEventCommand.MESSAGE_USAGE));
        }


        return new RemovePersonFromEventCommand(eventIndex, personIndex);

    }






}
