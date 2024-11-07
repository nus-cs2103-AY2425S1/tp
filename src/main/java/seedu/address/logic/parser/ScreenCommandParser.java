package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ScreenCommand;
import seedu.address.logic.commands.ScreenJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new subclass of ScreenCommand object
 */
public class ScreenCommandParser implements Parser<ScreenCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ScreenCommand
     * and returns a ScreenCommand subclass object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScreenCommand parse(String args) throws ParseException {

        String[] splitArgs = ParserUtil.parseRequiredNumberOfArguments(args, 2, ScreenCommand.MESSAGE_USAGE);

        String entityString = splitArgs[0];
        String indexString = splitArgs[1];

        String entity = ParserUtil.parseEntity(entityString);
        Index index = ParserUtil.parseIndex(indexString);

        switch (entity) {
        case ScreenJobCommand.ENTITY_WORD:
            return new ScreenJobCommand(index);
        default:
            String exceptionMessage = String.format(Messages.MESSAGE_OPERATION_NOT_ALLOWED,
                    AddCommand.COMMAND_WORD, entity);
            throw new ParseException(exceptionMessage);
        }
    }
}
