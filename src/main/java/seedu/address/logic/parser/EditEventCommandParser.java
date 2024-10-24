package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;

import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;

/**
 * Parses input arguments and creates a new EditEventCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_DESCRIPTION,
                        PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE);

        if (argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }

        int eventId;
        try {
            eventId = Integer.parseInt(argMultiMap.getPreamble().trim());
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        if (argMultiMap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            EventName eventName = ParserUtil.parseEventName(argMultiMap.getValue(PREFIX_EVENT_NAME).get());
            editEventDescriptor.setName(eventName);
        }
        if (argMultiMap.getValue(PREFIX_EVENT_DESCRIPTION).isPresent()) {
            EventDescription eventDescription = ParserUtil
                    .parseEventDescription(argMultiMap.getValue(PREFIX_EVENT_DESCRIPTION).get());
            editEventDescriptor.setDescription(eventDescription);
        }
        if (argMultiMap.getValue(PREFIX_EVENT_START_DATE)
                        .isPresent() && argMultiMap.getValue(PREFIX_EVENT_END_DATE).isPresent()) {
            EventDuration eventDuration = ParserUtil.parseEventDuration(
                    argMultiMap.getValue(PREFIX_EVENT_START_DATE).get(),
                    argMultiMap.getValue(PREFIX_EVENT_END_DATE).get());
            editEventDescriptor.setDuration(eventDuration);
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NO_CHANGES);
        }

        return new EditEventCommand(eventId, editEventDescriptor);
    }
}
