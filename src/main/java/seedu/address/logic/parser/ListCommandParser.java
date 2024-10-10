package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments for ListCommand
     *
     * @param args user input
     * @return ListCommand object for execution
     * @throws ParseException if the user input does not follow the expected format
     */
    public ListCommand parse(String args) throws ParseException {

        String entityType = args.trim();

        switch (entityType) {
        case PERSON_ENTITY_STRING:
            return new ListPersonCommand();
        case APPOINTMENT_ENTITY_STRING:
            //TODO: Instantiate and return ListAppointmentCommand
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE));
        }
    }
}
