package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_CELEBRITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_CONTACTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the user input to create a new instance of an EditEventCommand
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_TIME,
                        PREFIX_EVENT_VENUE, PREFIX_EVENT_CELEBRITY, PREFIX_EVENT_CONTACTS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME, PREFIX_EVENT_TIME,
                PREFIX_EVENT_VENUE, PREFIX_EVENT_CELEBRITY, PREFIX_EVENT_CONTACTS);

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            editEventDescriptor.setEventName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_TIME).isPresent()) {
            editEventDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_EVENT_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_VENUE).isPresent()) {
            editEventDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_EVENT_VENUE).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_CELEBRITY).isPresent()) {
            editEventDescriptor.setCelebrityName(argMultimap.getValue(PREFIX_EVENT_CELEBRITY).get());
        }
        if (argMultimap.getValue(PREFIX_EVENT_CONTACTS).isPresent()) {
            editEventDescriptor.setContactsNames(argMultimap.getValue(PREFIX_EVENT_CONTACTS).get());
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }

}
